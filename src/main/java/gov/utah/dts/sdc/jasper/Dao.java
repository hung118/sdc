package gov.utah.dts.sdc.jasper;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import gov.utah.dts.sdc.model.School;

import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles database methods.
 * 
 * @author HNGUYEN
 *
 */
@SuppressWarnings({"unused", "rawtypes"})
public class Dao {

	private static final String DATE_PATTERN = "MM/dd/yyyy";
	private static final String DATE_PATTERN2 = "yyyy-MM-dd";
	private static final String TIME_PATTERN = "hh:mm a";
	// multi training types
	private static final int BTW_CR = 1;
	private static final int OBS_CR = 2;
	private static final int BTW_OBS = 3;
	private static final int RD_BTW = 4;
	private static final int RD_OBS = 5;
	private static final int RD_CR = 6;
	private static final int BTW = 1;
	private static final int CR = 2;
	private static final int RD = 3;
	
    /** Creates a new instance of Dao */
    public Dao() {
    }
       
	public static List getSchoolList(Connection conn) throws Exception {

        String qry = "select school_pk as value, " +
                "upper(name) as name from school where deleted = 0 " +
                "order by name";
    	PreparedStatement statement = conn.prepareStatement(qry);
    	ResultSet rs = statement.executeQuery();
    	
    	ArrayList<NameValueBean> list = new ArrayList<NameValueBean>();
    	while (rs.next()) {
    		String name = rs.getString("name");
    		String value = String.valueOf(rs.getInt("value"));
    		NameValueBean bean = new NameValueBean(name, value);
    		
    		list.add(bean);    		
    	}
    	
    	return list;
    }
    
    public static List getTesterList(Connection conn) throws Exception {
    	
    	String qry = "select distinct(r.roadinstructor_fk) as roadinstructor_fk, " +
    			"upper(p.firstname) as firstname, upper(p.middlename) as middlename, upper(p.lastname) as lastname " +
    			"from completion_road r, person p " +
    			"where p.person_pk = r.roadinstructor_fk " +
    			"order by firstname, lastname";
    	PreparedStatement statement = conn.prepareStatement(qry);
    	ResultSet rs = statement.executeQuery();
    	
    	ArrayList<NameValueBean> list = new ArrayList<NameValueBean>();
    	while (rs.next()) {
    		String name = rs.getString("firstname") + 
    			(rs.getString("middlename") == null ? " " : " " + rs.getString("middlename")) +
    			" " + rs.getString("lastname");
    		String value = String.valueOf(rs.getInt("roadinstructor_fk"));
    		NameValueBean bean = new NameValueBean(name, value);
    		
    		list.add(bean);    		
    	}
    	
    	return list;    	
    }
    
    public static ArrayList<RoadTesterBean> getBeanCollection_RoadTester(Connection conn, String schoolType, List<String> schools, List<String> testers, String startDate, String endDate) throws Exception {
        StringBuffer sql = new StringBuffer();
    	
        sql.append("select upper(p.firstname) as tester_firstname, upper(p.middlename) as tester_middlename, ");
        sql.append("upper(p.lastname) as tester_lastname, COALESCE(p.instructornum, 0) as tester_number, upper(s.name) as school_name, ");
    	// Select a count of the number of passed tests
        sql.append("(select count(*) from completion_road r, student s where r.start_time is not null and r.end_time is not null and r.road_score <= 20 and r.roadinstructor_fk = a.person_pk and r.school_number = a.number and s.student_pk = r.student_fk");
        if (startDate != null && !"".equals(startDate)) {
        	sql.append(" and completion_date >= convert(datetime, '" + startDate + "', 101)");
    	}
        if (endDate != null && !"".equals(endDate)) {
        	sql.append(" and completion_date <= convert(datetime, '" + endDate + "', 101)");
        }
        //Closing parenthesis whether start and end dates exist or not
        sql.append(") as passed, ");
        // Select a count of the number of failed tests
    	sql.append("(select count(*) from completion_road r, student s where r.start_time is not null and r.end_time is not null and r.road_score > 20 and r.roadinstructor_fk = a.person_pk and r.school_number = a.number and s.student_pk = r.student_fk");
        if (startDate != null && !"".equals(startDate)) {
        	sql.append(" and completion_date >= convert(datetime, '" + startDate + "', 101)");
    	}
        if (endDate != null && !"".equals(endDate)) {
        	sql.append(" and completion_date <= convert(datetime, '" + endDate + "', 101)");
        }
        //Closing parenthesis whether start and end dates exist or not
    	sql.append(") as failed ");
    		
        sql.append("from ");

        // The following if block does processing that will put a list of selected instructors and schools into subquery 'a' 
        sql.append("(select z.* from ");
        sql.append("(select r.roadinstructor_fk AS person_pk, s.school_pk, s.number from school s, completion_road r, ");
        // We need to do instructor orphan checking if we don't have a selected list of instructors to choose from
        // by joining to person by the instructor key.
        if (testers.size() == 0) {
        	sql.append("person p, ");
    		}
        sql.append("student st ");
        sql.append("where ");
        if (schoolType != null && !"".equals(schoolType)) {
        	sql.append("s.school_type = "+schoolType+" and ");
        }
        if (schools.size() > 0) {
        	if (schools.size() == 1) {
        		sql.append("s.school_pk = "+schools.get(0)+" and ");
        	}else{
        		sql.append("s.school_pk in (");
        		for (int i=0;i<schools.size();i++) {
        			if (i > 0) {
        				sql.append(",");
        			}
        			sql.append(schools.get(i));
        		}
        		sql.append(") and ");
        	}
        }
        sql.append("r.school_number = s.number and ");
        if (testers.size() > 0) {
        	if (testers.size() == 1) {
        		sql.append("r.roadinstructor_fk = "+testers.get(0)+" and ");
        	}else{
        		sql.append("r.roadinstructor_fk in (");
        		for (int i=0;i<testers.size();i++) {
        			if (i > 0) {
        				sql.append(",");
        			}
        			sql.append(testers.get(i));
        		}
        		sql.append(") AND ");
        	}
        }else{
        	sql.append("p.person_pk = r.roadinstructor_fk and ");
        }
        if (testers.size() == 0) {
        	sql.append("p.person_pk = r.roadinstructor_fk and ");
        }
        sql.append("st.student_pk = r.student_fk ");
        sql.append(" UNION ");
        sql.append("select ps.person_fk AS person_pk, s.school_pk, s.number from school s, person_school ps");
        // If we do not have a list of testers to select for then we need to make sure that all testers found are 
        // not orphaned by joining to person.
        if (testers.size() == 0) {
        	sql.append(", person p ");
        }
        sql.append(" where ");
        if (schools.size() > 0) {
        	if (schools.size() == 1) {
        		sql.append("s.school_pk = "+schools.get(0)+" and ");
        	}else{
        		sql.append("s.school_pk IN (");
        		for (int i=0;i<schools.size();i++) {
        			if (i > 0) {
        				sql.append(",");
        			}
        			sql.append(schools.get(i));
        		}
        		sql.append(") and ");
        	}
        }
        if (schoolType != null && !"".equals(schoolType)) {
        	sql.append("s.school_type = "+schoolType+" and ");
        }
        sql.append("ps.school_fk = s.school_pk");
        if (testers.size() > 0) {
        	if (testers.size() == 1) {
        		sql.append(" and ps.person_fk = "+testers.get(0));
        	}else{
        		sql.append(" and ps.person_fk IN (");
        		for (int i=0;i<testers.size();i++) {
        			if (i > 0) {
        				sql.append(",");
        			}
        			sql.append(testers.get(i));
        		}
    		sql.append(") ");
    	} 
        }else{
            sql.append(" and p.person_pk = ps.person_fk");
    	 }
        sql.append(") z ");
        sql.append(") a, ");
        sql.append("person p, school s ");
        sql.append("where ");
        sql.append("p.person_pk = a.person_pk and ");
        sql.append("s.school_pk = a.school_pk ");
        sql.append("order by s.name, p.firstname, p.middlename, p.lastname");
        //System.out.println("SQL > "+sql.toString());
        
        PreparedStatement statement = conn.prepareStatement(sql.toString());
    	ResultSet rs = statement.executeQuery();
        
        ArrayList<RoadTesterBean> collection = new ArrayList<RoadTesterBean>();
        int passed = 0;
        int failed = 0;
        int total = 0;
    	while (rs.next()) {
    		RoadTesterBean bean = new RoadTesterBean();
    		bean.setSchoolName(rs.getString("school_name"));
    		String testerNameNumber = rs.getString("tester_firstname") +
    			(rs.getString("tester_middlename") == null ? " " : " " + rs.getString("tester_middlename")) +
    			" " + rs.getString("tester_lastname") +
    			", " + (rs.getInt("tester_number") == 0 ? " " : " " + rs.getInt("tester_number"));
    		bean.setTesterNameNumber(testerNameNumber);
    		passed = rs.getInt("passed");
    		failed = rs.getInt("failed");
    		total = passed + failed;
            bean.setPassedTests(Double.valueOf(String.valueOf(passed)));
    		bean.setFailedTests(Double.valueOf(String.valueOf(failed)));
    		bean.setTotalTests(Double.valueOf(String.valueOf(total)));
    		collection.add(bean);
    	}
    	
    	return collection;
    }
    
    private static String getVarStr(HttpServletRequest request, boolean pass) {
    	
    	StringBuffer varStr = new StringBuffer("");
    	
    	// passed or failed tests
    	if (pass) {		// passed tests
    		varStr.append("(r.road_score <= 20 or r.road_score is null) ");
    	} else {		// failed tests
    		varStr.append("r.road_score > 20 ");
    	}
    	
    	// all or some testers
    	String[] roadinstructor_fk = request.getParameterValues("roadinstructor_fk");
    	if (!"0".equals(roadinstructor_fk[0])) {		// not all testers
    		varStr.append("and r.roadinstructor_fk in (" + roadinstructor_fk[0]);
    		
    		for (int i = 1; i < roadinstructor_fk.length; i++) {
    			varStr.append(", " + roadinstructor_fk[i]);
    		}
    		
    		varStr.append(") ");
    	}
    	
    	// date range
    	String startDate = request.getParameter("reportStartDate");
    	String endDate = request.getParameter("reportEndDate");
    	if (!"".equals(startDate) && !"".equals(endDate)) {
    		varStr.append("and r.completion_date <= convert(datetime, '" + endDate + "', 101) ")
    		.append("and r.completion_date >= convert(datetime, '" + startDate + "', 101) ");
    	}
    	
    	// return result
    	return varStr.toString();
    }
    
    /**
     * Calculates number of students doing more than one kind of training at one time 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Student1(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer unionGroupBy = new StringBuffer("");
    	unionGroupBy.append("(select z.school_pk, z.student_fk, z.start_date from ")
    	.append("(select s.school_pk, a.student_fk, left(convert(varchar, a.start_time, 120), 10) AS start_date, 'BTW' as training_type ")
    	.append("from student_btw a, classroom c, school s ")
    	.append("where a.classroom_fk = c.classroom_pk and c.school_fk = s.school_pk and s.school_pk = schoolIndex and ")
    	.append("a.start_time >= convert(datetime, '" + startDate + "', 101) and ")
    	.append("a.end_time <= convert(datetime, '" + endDate + "', 101) ")
    	.append("group by s.school_pk, a.student_fk, left(convert(varchar, a.start_time, 120), 10) ")
    	.append("UNION ")
    	.append("select s.school_pk, a.student_fk, left(convert(varchar, a.start_time, 120), 10) AS start_date, 'Classroom' as training_type ")
    	.append("from student_training a, classroom c, school s ")
    	.append("where a.classroom_fk = c.classroom_pk and c.school_fk = s.school_pk and s.school_pk = schoolIndex and ")
    	.append("a.start_time >= convert(datetime, '" + startDate + "', 101) and ")
    	.append("a.end_time <= convert(datetime, '" + endDate + "', 101) ")
    	.append("group by s.school_pk, a.student_fk, left(convert(varchar, a.start_time, 120), 10) ")
    	.append("UNION ")
    	.append("select s.school_pk, a.student_fk, left(convert(varchar, a.start_time, 120), 10) AS start_date, 'Observation' as training_type ")
    	.append("from student_observation a, classroom c, school s ")
    	.append("where a.classroom_fk = c.classroom_pk and c.school_fk = s.school_pk and s.school_pk = schoolIndex and ")
    	.append("a.start_time >= convert(datetime, '" + startDate + "', 101) and ")
    	.append("a.end_time <= convert(datetime, '" + endDate + "', 101) ")
    	.append("group by s.school_pk, a.student_fk, left(convert(varchar, a.start_time, 120), 10)) z ")
    	.append("group by z.school_pk, z.student_fk, z.start_date having count(*) > 1) ");
    	
    	StringBuffer qry = new StringBuffer();
    	qry.append("select x.* from ")
    	.append("(select sc.name as school_name, s.firstname as studentfirstname, s.lastname as studentlastname, ")
    	.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber, y.start_date, 'BTW' as training_type ")
    	.append("from ")
    	.append(unionGroupBy.toString())
    	.append("y, student_btw b, classroom cl, school sc, student s ")
    	.append("where b.classroom_fk = cl.classroom_pk and cl.school_fk = sc.school_pk and sc.school_pk = y.school_pk and ")
    	.append("b.student_fk = y.student_fk and y.student_fk = s.student_pk and left(convert(varchar, b.start_time, 120), 10) = y.start_date ")
    	.append("UNION ")
    	.append("select sc.name as school_name, s.firstname as studentfirstname, s.lastname as studentlastname, ")
    	.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber, y.start_date, 'Classroom' as training_type ")
    	.append("from ")
    	.append(unionGroupBy.toString())
    	.append("y, student_training b, classroom cl, school sc, student s ")
    	.append("where b.classroom_fk = cl.classroom_pk and cl.school_fk = sc.school_pk and sc.school_pk = y.school_pk and ")
    	.append("b.student_fk = y.student_fk and y.student_fk = s.student_pk and left(convert(varchar, b.start_time, 120), 10) = y.start_date ")
    	.append("UNION ")
    	.append("select sc.name as school_name, s.firstname as studentfirstname, s.lastname as studentlastname, ")
    	.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber, y.start_date, 'Observation' as training_type ")
    	.append("from ")
    	.append(unionGroupBy.toString())
    	.append("y, student_observation b, classroom cl, school sc, student s ")
    	.append("where b.classroom_fk = cl.classroom_pk and cl.school_fk = sc.school_pk and sc.school_pk = y.school_pk and ")
    	.append("b.student_fk = y.student_fk and y.student_fk = s.student_pk and left(convert(varchar, b.start_time, 120), 10) = y.start_date ")
    	.append(") x order by studentlastname, studentfirstname, start_date");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> resultList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "studentOver1");
        		
        		ArrayList<OverlapTimeBean> filteredList = filterStudent1(schoolList);
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setStudentName("No Results Found");
            		resultList.add(bean);        			
        		} else {
        			resultList.addAll(filteredList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setStudentName("No Records Found for Process");
        		resultList.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
  
    	return resultList;
    }

    /**
     * Calculates number of students doing 2 different kinds of training at one time. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Student2(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {    	
    	StringBuffer qry = new StringBuffer();
    	qry.append("select a.name as school_name, s.firstname, s.lastname, case when s.file_number is null then s.student_number else s.file_number end AS student_number, a.start_date, a.btw, a.observation, a.training ");
    	qry.append("from ");
    	qry.append("(select d.name, d.student_fk, d.start_date, ");
    	qry.append("case sum(d.btw_count) when 0 then '0' else '1' end AS btw, ");
    	qry.append("case sum(d.obs_count) when 0 then '0' else '1' end AS observation, ");
    	qry.append("case sum(d.train_count) when 0 then '0' else '1' end AS training ");
    	qry.append("from ");
    	// Get btw to training overlaps
    	qry.append("(select sc.name, b.student_fk, left(convert(varchar, b.start_time, 120), 10) AS start_date, 1 AS btw_count, 0 AS obs_count, 0 AS train_count ");
    	qry.append("from student_btw b, student_training c, classroom cl, school sc ");
    	qry.append("where sc.school_pk = schoolIndex and ");
	    qry.append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("b.start_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("c.student_fk = b.student_fk and ");
    	qry.append("((b.start_time >= c.start_time and b.start_time <= c.end_time) or (c.start_time >= b.start_time and c.start_time <= b.end_time)) and ");
    	qry.append("b.classroom_fk = cl.classroom_pk and ");
    	qry.append("cl.school_fk = sc.school_pk ");
    	qry.append(" UNION ");
    	// Get observation to training overlaps
    	qry.append("select sc.name, b.student_fk, left(convert(varchar, b.start_time, 120), 10) AS start_date, 0 AS btw_count, 1 AS obs_count, 0 AS train_count ");
    	qry.append("from student_observation b, student_training c, classroom cl, school sc ");
    	qry.append("where sc.school_pk = schoolIndex and ");
	    qry.append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("b.start_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("c.student_fk = b.student_fk and ");
    	qry.append("((b.start_time >= c.start_time and b.start_time <= c.end_time) or (c.start_time >= b.start_time and c.start_time <= b.end_time)) and ");
    	qry.append("b.classroom_fk = cl.classroom_pk and ");
    	qry.append("cl.school_fk = sc.school_pk ");
    	qry.append(" UNION ");
    	// Get btw to observation overlaps
    	qry.append("select sc.name, b.student_fk, left(convert(varchar, b.start_time, 120), 10) AS start_date, 0 AS btw_count, 0 AS obs_count, 1 AS train_count ");
    	qry.append("from student_btw b, student_observation c, classroom cl, school sc ");
    	qry.append("where sc.school_pk = schoolIndex and ");
	    qry.append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("b.start_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("c.student_fk = b.student_fk and ");
    	qry.append("((b.start_time >= c.start_time and b.start_time <= c.end_time) or (c.start_time >= b.start_time and c.start_time <= b.end_time)) and ");
    	qry.append("b.classroom_fk = cl.classroom_pk and ");
    	qry.append("cl.school_fk = sc.school_pk ");
    	qry.append(") d ");
    	qry.append("group by d.name, d.student_fk, d.start_date");
    	qry.append(") a, ");
    	qry.append("student s ");
    	qry.append("WHERE s.student_pk = a.student_fk ");
    	qry.append("ORDER BY a.name, s.lastname, s.firstname, a.start_date");

		// loop thru each school
    	ArrayList<OverlapTimeBean> resultList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		String schoolName = "";
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
            	while (rs.next()) {
            		if ("".equals(schoolName)) {
            			schoolName = rs.getString("school_name");
            		}
        			OverlapTimeBean bean = new OverlapTimeBean();
        			bean.setSchoolName(rs.getString("school_name"));
        			bean.setFirstName(rs.getString("firstname"));
        			bean.setLastName(rs.getString("lastname"));
        			bean.setStudentNumber(String.valueOf(rs.getLong("student_number")));
        			bean.setStartDateStr(rs.getString("start_date"));
        			bean.setBtw(rs.getString("btw"));
        			bean.setObservation(rs.getString("observation"));
        			bean.setTraining(rs.getString("training"));
            		schoolList.add(bean);
            	}
        		
        		if (schoolList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No Results");
            		bean.setLastName("Found");
            		bean.setBtw("0");
            		bean.setObservation("0");;
            		bean.setTraining("0");
            		resultList.add(bean);        			
        		} else {
        			resultList.addAll(schoolList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records Found");
        		bean.setLastName("for Process");
        		bean.setBtw("0");
        		bean.setObservation("0");;
        		bean.setTraining("0");
        		resultList.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
  
    	return resultList;
    }
   
    /**
     * Calculates number of instructors conducting behind the wheel training for more than 1 student
     * at one time.
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Instructor1(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {    	
    	StringBuffer qry = new StringBuffer();
    	
    	qry.append("select sc.name as school_name, a.firstname, a.lastname, a.start_time, a.end_time, a.studentfirstname, a.studentlastname, a.studentnumber ");
    	qry.append("from ");
    	qry.append("(select b.instructor_fk, p.firstname, p.lastname, ");
    	qry.append("b.start_time, b.end_time, ");
    	qry.append("b.classroom_fk, ");
    	qry.append("b.student_fk, s.firstname as studentfirstname, s.lastname as studentlastname, ");
    	qry.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qry.append("from ");
    	qry.append("student_btw b, person p, student s ");
    	qry.append("where ");
    	qry.append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("b.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("b.instructor_fk = p.person_pk and ");
    	qry.append("b.student_fk = s.student_pk) a ");
    	qry.append("left join classroom cl on a.classroom_fk = cl.classroom_pk left join school sc on cl.school_fk = sc.school_pk ");
    	qry.append("where ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> btw = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by a.lastname, a.firstname, a.start_time, a.studentlastname";
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "btw");
        		
        		ArrayList<OverlapTimeBean> filteredList = filterInstructor(schoolList, "btw");
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setBtw("0");
            		btw.add(bean);        			
        		} else {
        			btw.addAll(filteredList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setBtw("0");
        		btw.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
  
    	return btw;
    }
    
    /**
     * Calculates number of instructors doing 2 different kinds of training at one time. (Instructor can do 1 Behind-
     * The-Wheel and up to 3 observations at the same time, but they cannot do Classroom and Behind-The-Wheel at
     * the same time.) 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Instructor2(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();
    	qry.append("select sc.name as school_name, p.firstname, p.lastname, a.start_time, a.end_time, ");
    	qry.append("s.firstname as studentfirstname, ");
    	qry.append("s.lastname as studentlastname, ");
    	qry.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qry.append("from tableName a, person p, student s, classroom cl, school sc ");
    	qry.append("where ");
    	qry.append("a.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("a.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("a.instructor_fk = p.person_pk and ");
    	qry.append("a.student_fk = s.student_pk and ");
    	qry.append("a.classroom_fk = cl.classroom_pk and ");
    	qry.append("cl.school_fk = sc.school_pk and ");
    	
		// loop thru each school to get 3 list from 3 db tables and filter them to get the final results
    	ArrayList<OverlapTimeBean> finalList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		
    		ArrayList<OverlapTimeBean> btw = new ArrayList<OverlapTimeBean>();
        	PreparedStatement statement = conn.prepareStatement(query.replaceFirst("tableName", "student_btw"));
        	ResultSet rs = statement.executeQuery();  
        	setList(btw, rs, "BTW", null);
        	rs.close();
        	statement.close();
        	
        	query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		ArrayList<OverlapTimeBean> training = new ArrayList<OverlapTimeBean>();
        	statement = conn.prepareStatement(query.replaceFirst("tableName", "student_training"));
        	rs = statement.executeQuery();  
        	setList(training, rs, "Classroom", null);
        	rs.close();
        	statement.close();

        	query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		ArrayList<OverlapTimeBean> observation = new ArrayList<OverlapTimeBean>();
        	statement = conn.prepareStatement(query.replaceFirst("tableName", "student_observation"));
        	rs = statement.executeQuery();  
        	setList(observation, rs, "Observation", null);
        	
        	if (btw.isEmpty() && training.isEmpty() && observation.isEmpty()) {	// insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setStudentName("");
        		bean.setStudentNumber("");
        		bean.setBtw_cr("0");
        		bean.setObs_cr("0");
        		bean.setBtw_obs("0");
        		
        		finalList.add(bean);
        	} else {
        		ArrayList<OverlapTimeBean> filteredList = filterInstructor2(btw, training, observation);
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		rs.close();
            		statement.close();
            		query = "select name from school where school_pk = " + schools.get(i);
            		statement = conn.prepareStatement(query);
            		rs = statement.executeQuery();
            		rs.next();
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(rs.getString("name"));
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setBtw_cr("0");
            		bean.setObs_cr("0");
            		bean.setBtw_obs("0");
            		
            		finalList.add(bean);
        		} else {
        			finalList.addAll(filteredList);
        		}
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return finalList;
    }
    
    /**
     * Calculates number of instructors doing 2 or more different classroom sessions at one time. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList<OverlapTimeBean> getBeanCollection_Instructor3(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();
    	
    	qry.append("select sc.name as school_name, a.firstname, a.lastname, a.start_time, a.end_time, a.studentfirstname, a.studentlastname, a.studentnumber ");
    	qry.append("from ");
    	qry.append("(select b.instructor_fk, p.firstname, p.lastname, ");
    	qry.append("b.start_time, b.end_time, ");
    	qry.append("b.classroom_fk, ");
    	qry.append("b.student_fk, s.firstname as studentfirstname, s.lastname as studentlastname, ");
    	qry.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qry.append("from ");
    	qry.append("student_training b, person p, student s ");
    	qry.append("where ");
    	qry.append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("b.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("b.instructor_fk = p.person_pk and ");
    	qry.append("b.student_fk = s.student_pk) a ");
    	qry.append("left join classroom cl on a.classroom_fk = cl.classroom_pk left join school sc on cl.school_fk = sc.school_pk ");
    	qry.append("where ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> training = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by a.lastname, a.firstname, a.start_time, a.studentlastname";
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "training");
        		
        		ArrayList<OverlapTimeBean> filteredList = filterInstructor(schoolList, "training");
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setTraining("0");
            		training.add(bean);        			
        		} else {
        			training.addAll(filteredList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setTraining("0");
        		training.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
  
    	return training;
    }

    /**
     * Calculates number of instructor/tester teaching any training and road testing at the same time
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Instructor4(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qryRoad = new StringBuffer();
    	qryRoad.append("select sc.name as school_name, p.firstname, p.lastname, a.start_time, a.end_time, ");
    	qryRoad.append("s.firstname as studentfirstname, ");
    	qryRoad.append("s.lastname as studentlastname, ");
    	qryRoad.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qryRoad.append("from completion_road a, person p, student s, school sc ");
    	qryRoad.append("where ");
    	qryRoad.append("a.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qryRoad.append("a.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qryRoad.append("a.roadinstructor_fk = p.person_pk and ");
    	qryRoad.append("a.student_fk = s.student_pk and ");
    	qryRoad.append("a.school_number = sc.number and ");
    	
    	StringBuffer qry = new StringBuffer();
    	qry.append("select sc.name as school_name, p.firstname, p.lastname, a.start_time, a.end_time, ");
    	qry.append("s.firstname as studentfirstname, ");
    	qry.append("s.lastname as studentlastname, ");
    	qry.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qry.append("from tableName a, person p, student s, classroom cl, school sc ");
    	qry.append("where ");
    	qry.append("a.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("a.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("a.instructor_fk = p.person_pk and ");
    	qry.append("a.student_fk = s.student_pk and ");
    	qry.append("a.classroom_fk = cl.classroom_pk and ");
    	qry.append("cl.school_fk = sc.school_pk and ");
    	
		// loop thru each school to get 4 list from 4 db tables and filter them to get the final results
    	ArrayList<OverlapTimeBean> finalList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String queryRoad = qryRoad.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		String query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		
    		ArrayList<OverlapTimeBean> road = new ArrayList<OverlapTimeBean>();
        	PreparedStatement statement = conn.prepareStatement(queryRoad);
        	ResultSet rs = statement.executeQuery();  
        	setList(road, rs, "Road", null);
        	rs.close();
        	statement.close();
    		
    		ArrayList<OverlapTimeBean> btw = new ArrayList<OverlapTimeBean>();
        	statement = conn.prepareStatement(query.replaceFirst("tableName", "student_btw"));
        	rs = statement.executeQuery();  
        	setList(btw, rs, "BTW", null);
        	rs.close();
        	statement.close();
        	
        	query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		ArrayList<OverlapTimeBean> training = new ArrayList<OverlapTimeBean>();
        	statement = conn.prepareStatement(query.replaceFirst("tableName", "student_training"));
        	rs = statement.executeQuery();  
        	setList(training, rs, "Classroom", null);
        	rs.close();
        	statement.close();

        	query = qry.toString() + "sc.school_pk = " + schools.get(i) + " order by p.lastname, p.firstname, a.start_time, studentlastname";
    		ArrayList<OverlapTimeBean> observation = new ArrayList<OverlapTimeBean>();
        	statement = conn.prepareStatement(query.replaceFirst("tableName", "student_observation"));
        	rs = statement.executeQuery();  
        	setList(observation, rs, "Observation", null);
        	
        	if (road.isEmpty() && btw.isEmpty() && training.isEmpty() && observation.isEmpty()) {	// insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setRd_btw("0");
        		bean.setRd_obs("0");
        		bean.setRd_cr("0");
        		
        		finalList.add(bean);
        	} else {
        		ArrayList<OverlapTimeBean> filteredList = filterInstructor4(road, btw, observation, training);
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		rs.close();
            		statement.close();
            		query = "select name from school where school_pk = " + schools.get(i);
            		statement = conn.prepareStatement(query);
            		rs = statement.executeQuery();
            		rs.next();
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(rs.getString("name"));
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setRd_btw("0");
            		bean.setRd_obs("0");
            		bean.setRd_cr("0");
            		
            		finalList.add(bean);
        		} else {
        			finalList.addAll(filteredList);
        		}
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return finalList;
    }

    /**
     * Calculates number of instructors doing Behind-The-Wheel for more than 10 hours a day. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Instructor5(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();
    	
    	qry.append("select sc.name as school_name, p.firstname, p.middlename, p.lastname, btw.start_time, btw.end_time, ");
    	qry.append("s.firstname as studentfirstname,  s.lastname as studentlastname, ");
    	qry.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qry.append("from ");
    	qry.append("(select b.classroom_fk, b.instructor_fk, left(convert(varchar, b.start_time, 120), 10) AS start_date, ");
    	qry.append("sum(datediff(hour, b.start_time, b.end_time)) AS daily_hours  ");
    	qry.append("from ");
    	qry.append("student_btw b, person p, classroom cl, school sc ");
    	qry.append("where ");
	    qry.append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("b.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("p.person_pk = b.instructor_fk and ");
    	qry.append("b.classroom_fk = cl.classroom_pk and ");
    	qry.append("cl.school_fk = sc.school_pk and ");
    	qry.append("sc.school_pk = schoolIndex ");
    	qry.append("group by b.classroom_fk, b.instructor_fk, left(convert(varchar, b.start_time, 120), 10) ");
    	qry.append("having sum(datediff(hour, b.start_time, b.end_time)) > 10) a, ");
    	qry.append("student_btw btw, person p, student s, classroom cl, school sc ");
    	qry.append("where ");
    	qry.append("a.daily_hours > 10 and ");
    	
    	qry.append("btw.instructor_fk = a.instructor_fk and ");
    	qry.append("left(convert(varchar, btw.start_time, 120), 10) = a.start_date and ");
    	qry.append("p.person_pk = btw.instructor_fk and s.student_pk = btw.student_fk and ");
    	qry.append("a.classroom_fk = cl.classroom_pk and cl.school_fk = sc.school_pk ");
    	qry.append("order by p.lastname, p.firstname, btw.start_time, studentlastname ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> btw = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceFirst("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, null);
        		
        		if (schoolList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setBtw("0");
            		btw.add(bean);        			
        		} else {
        			btw.addAll(schoolList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setBtw("0");
        		btw.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return btw;
    }
    
    /**
     * Calculates number of instructors doing road testing for more than 10 hours a day. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Instructor6(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();
    	
    	qry.append("select sc.name as school_name, p.firstname, p.middlename, p.lastname, r.start_time, r.end_time, ");
    	qry.append("s.firstname as studentfirstname,  s.lastname as studentlastname, ");
    	qry.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ");
    	qry.append("from ");
    	qry.append("(select r.school_number, r.roadinstructor_fk, left(convert(varchar, r.start_time, 120), 10) AS start_date, ");
    	qry.append("sum(datediff(hour, r.start_time, r.end_time)) AS daily_hours  ");
    	qry.append("from ");
    	qry.append("completion_road r, person p, school sc ");
    	qry.append("where ");
	    qry.append("r.start_time >= convert(datetime, '" + startDate + "', 101) and ");
    	qry.append("r.end_time <= convert(datetime, '" + endDate + "', 101) and ");
    	qry.append("p.person_pk = r.roadinstructor_fk and ");
    	qry.append("r.school_number = sc.number and ");
    	qry.append("sc.school_pk = schoolIndex ");
    	qry.append("group by r.school_number, r.roadinstructor_fk, left(convert(varchar, r.start_time, 120), 10) ");
    	qry.append("having sum(datediff(hour, r.start_time, r.end_time)) > 10) a, ");
    	qry.append("completion_road r, person p, student s, school sc ");
    	qry.append("where ");
    	qry.append("a.daily_hours > 10 and ");
    	
    	qry.append("r.roadinstructor_fk = a.roadinstructor_fk and ");
    	qry.append("left(convert(varchar, r.start_time, 120), 10) = a.start_date and ");
    	qry.append("p.person_pk = r.roadinstructor_fk and s.student_pk = r.student_fk and ");
    	qry.append("r.school_number = sc.number ");
    	qry.append("order by p.lastname, p.firstname, r.start_time, studentlastname ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> r = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceFirst("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "road");
        		
        		if (schoolList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setRoad("0");
            		r.add(bean);        			
        		} else {
        			r.addAll(schoolList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setRoad("0");
        		r.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return r;
    }
    
    /**
     * Calculates number of instructors doing both Behind-The-Wheel and Road Testing for more than 10 hours a day. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Instructor7(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer unionGroupBy = new StringBuffer();
    	unionGroupBy.append("(select z.school_pk, z.instructor_fk, z.start_date, sum(daily_minutes) daily_minutes ")
    	.append("from (select sc.school_pk, b.instructor_fk, left(convert(varchar, b.start_time, 120), 10) AS start_date, ")
    	.append("b.start_time, b.end_time, datediff(minute, b.start_time, b.end_time) as daily_minutes ")
    	.append("from student_btw b, classroom cl, school sc ")
    	.append("where b.classroom_fk = cl.classroom_pk and cl.school_fk = sc.school_pk and ")
    	.append("sc.school_pk = schoolIndex and ")
	    .append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ")
    	.append("b.end_time <= convert(datetime, '" + endDate + "', 101) ")
    	.append("group by sc.school_pk, b.instructor_fk, b.start_time, b.end_time ")
    	.append("UNION ")
    	.append("select sc.school_pk, r.roadinstructor_fk as instructor_fk, left(convert(varchar, r.start_time, 120), 10) AS start_date, ")
    	.append("r.start_time, r.end_time, datediff(minute, r.start_time, r.end_time) as daily_minutes ")
    	.append("from completion_road r, school sc ")
    	.append("where r.school_number = sc.number and ")
    	.append("sc.school_pk = schoolIndex and ")
	    .append("r.start_time >= convert(datetime, '" + startDate + "', 101) and ")
    	.append("r.end_time <= convert(datetime, '" + endDate + "', 101) ")
    	.append("group by sc.school_pk, r.roadinstructor_fk, r.start_time, r.end_time ")
    	.append(") z group by z.school_pk, z.instructor_fk, z.start_date ")
    	.append("having sum(daily_minutes) > 10 * 60) ");
    	
    	StringBuffer qry = new StringBuffer();    	
    	qry.append("select y.* from ( ")
    	.append("select sc.name as school_name, p.firstname, p.middlename, p.lastname, ")
    	.append("sb.start_time, sb.end_time, 'BTW' AS training_type, s.firstname as studentfirstname, s.lastname as studentlastname, ")
    	.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ")
    	.append("from ")
    	.append(unionGroupBy.toString())
    	.append(" u, student_btw sb, student s, person p, classroom cl, school sc ")
    	.append("where sb.instructor_fk = u.instructor_fk and left(convert(varchar, sb.start_time, 120), 10) = u.start_date and ")
    	.append("sb.classroom_fk = cl.classroom_pk and ")
    	.append("cl.school_fk = sc.school_pk and sc.school_pk = u.school_pk and ")
    	.append("sb.instructor_fk = p.person_pk and sb.student_fk = s.student_pk ")
    	.append("UNION ")
    	.append("select sc.name as school_name, p.firstname, p.middlename, p.lastname, ")
    	.append("cr.start_time, cr.end_time, 'Road' AS training_type, s.firstname as studentfirstname, s.lastname as studentlastname, ")
    	.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ")
    	.append("from ")
    	.append(unionGroupBy.toString())
    	.append(" u, completion_road cr, student s, person p, school sc ")
    	.append("where cr.roadinstructor_fk = u.instructor_fk and left(convert(varchar, cr.start_time, 120), 10) = u.start_date and ")
    	.append("sc.school_pk = u.school_pk and cr.roadinstructor_fk = p.person_pk and cr.student_fk = s.student_pk ")
    	.append(") y order by y.lastname, y.firstname, y.start_time, y.studentlastname");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> r = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "btw_road");
        		
        		if (schoolList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setRoad("0");
            		r.add(bean);        			
        		} else {
        			r.addAll(schoolList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setRoad("0");
        		r.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return r;    	
    }
    
    /**
     * Calculates the same vehicle being used to conduct 2 or more btw sessions.
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Vehicle1(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();    	
    	qry.append("select s.name as school_name, v.plate, v.make, v.year, b.start_time, b.end_time, ")
    	.append("st.firstname as studentfirstname, st.lastname as studentlastname, case when st.file_number is null then st.student_number else st.file_number end AS studentnumber ")
    	.append("from student_btw b, classroom c, school s, vehicle v, student st ")
    	.append("where s.school_pk = schoolIndex and ")
	    .append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ")
	    .append("b.end_time <= convert(datetime, '" + endDate + "', 101) and ")
    	.append("b.classroom_fk = c.classroom_pk and c.school_fk = s.school_pk and ")
    	.append("b.vehicle_fk = v.vehicle_pk and b.student_fk = st.student_pk ")
    	.append("order by v.plate, v.make, v.year, b.start_time, studentlastname, studentfirstname ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> btw = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "btw_vehicle");
        		
        		ArrayList<OverlapTimeBean> filteredList = filterVehicle(schoolList);
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setVehicleInfo("No Results Found");
            		bean.setBtw("0");
            		btw.add(bean);        			
        		} else {
        			btw.addAll(filteredList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setVehicleInfo("No Records Found for Process");
        		bean.setBtw("0");
        		btw.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return btw;    	
    }
    
    /**
     * Calculates the same vehicle being used during a road test at one time. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Vehicle2(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();    	
    	qry.append("select s.name as school_name, v.plate, v.make, v.year, b.start_time, b.end_time, ")
    	.append("st.firstname as studentfirstname, st.lastname as studentlastname, case when st.file_number is null then st.student_number else st.file_number end AS studentnumber ")
    	.append("from completion_road b, school s, vehicle v, student st ")
    	.append("where b.school_number = s.number and s.school_pk = schoolIndex and ")
	    .append("b.start_time >= convert(datetime, '" + startDate + "', 101) and ")
	    .append("b.end_time <= convert(datetime, '" + endDate + "', 101) and ")
    	.append("b.vehicle_fk = v.vehicle_pk and b.student_fk = st.student_pk ")
    	.append("order by v.plate, v.make, v.year, b.start_time, studentlastname, studentfirstname ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> road = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "btw_vehicle2");
        		
        		ArrayList<OverlapTimeBean> filteredList = filterVehicle(schoolList);
        		if (filteredList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setVehicleInfo("No Results Found");
            		bean.setRoad("0");
            		road.add(bean);        			
        		} else {
        			road.addAll(filteredList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setVehicleInfo("No Records Found for Process");
        		bean.setRoad("0");
        		road.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return road;    	
    }
    
    /**
     * Calculates the number of road testers doing testing for more than 1 student at a time. 
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList getBeanCollection_Tester1(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();    	
    	qry.append("select sc.name as school_name, p.firstname, p.middlename, p.lastname, ")
    	.append("r.start_time, r.end_time, s.firstname as studentfirstname, s.lastname as studentlastname, ")
    	.append("case when s.file_number is null then s.student_number else s.file_number end AS studentnumber ")
    	.append("from (select sc.school_pk, r.roadinstructor_fk, r.start_time from completion_road r, school sc ")
    	.append("where r.start_time is not null and sc.school_pk = schoolIndex and r.school_number = sc.number and ")
	    .append("r.start_time >= convert(datetime, '" + startDate + "', 101) and ")
	    .append("r.end_time <= convert(datetime, '" + endDate + "', 101) ")
    	.append("group by sc.school_pk, r.roadinstructor_fk, r.start_time having count(*) > 1 ")
    	.append(") z, completion_road r, school sc, person p, student s ")
    	.append("where r.school_number = sc.number and sc.school_pk = z.school_pk and r.roadinstructor_fk = z.roadinstructor_fk and ")
    	.append("r.start_time = z.start_time and r.student_fk = s.student_pk and r.roadinstructor_fk = p.person_pk ")
    	.append("order by p.lastname, p.firstname, r.start_time, studentlastname ");
    	
		// loop thru each school
    	ArrayList<OverlapTimeBean> r = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
        		String schoolName = setList(schoolList, rs, null, "road");
        		
        		if (schoolList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No");
            		bean.setLastName("Results Found");
            		bean.setRoad("0");
            		r.add(bean);        			
        		} else {
        			r.addAll(schoolList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records");
        		bean.setLastName("Found for Process");
        		bean.setRoad("0");
        		r.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return r;    	
    }

    /**
     * Calculates the number of students that have completed each part of their training (classroom, btw, and observation). 
     * See Redmine 30817 enhancements for details.
     * @param conn
     * @return
     * @throws Exception
     */
    public static ArrayList<OverlapTimeBean> getBeanCollection_SchoolCompletion(Connection conn, List<String> schools, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();
    	qry.append("select sc.name school_name, s.firstname, s.middlename, s.lastname,  case when s.file_number is null then s.student_number else s.file_number end AS student_number, ") 
		.append("1 as completiontype_fk ")
		.append("from student s left join school sc on s.btw_completion_school = sc.number ")
		.append("where s.btw_completion_date >= convert(datetime, '" + startDate + "', 101) and s.btw_completion_date <= convert(datetime, '" + endDate + "', 101) and ")
		.append("sc.school_pk = schoolIndex ")
		.append("UNION ");
    	qry.append("select sc.name school_name, s.firstname, s.middlename, s.lastname,  case when s.file_number is null then s.student_number else s.file_number end AS student_number, ") 
		.append("2 as completiontype_fk ")
		.append("from student s left join school sc on s.observation_completion_school = sc.number ")
		.append("where s.observation_completion_date >= convert(datetime, '" + startDate + "', 101) and s.observation_completion_date <= convert(datetime, '" + endDate + "', 101) and ")
		.append("sc.school_pk = schoolIndex ")
		.append("UNION ");
    	qry.append("select sc.name school_name, s.firstname, s.middlename, s.lastname,  case when s.file_number is null then s.student_number else s.file_number end AS student_number, ") 
		.append("3 as completiontype_fk ")
		.append("from student s left join school sc on s.classroom_completion_school = sc.number ")
		.append("where s.classroom_completion_date >= convert(datetime, '" + startDate + "', 101) and s.classroom_completion_date <= convert(datetime, '" + endDate + "', 101) and ")
		.append("sc.school_pk = schoolIndex ");    		    		
    	
		// loop thru each school    	
    	ArrayList<OverlapTimeBean> results = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < schools.size(); i++) {
    		String query = qry.toString().replaceAll("schoolIndex", schools.get(i));
    		
        	PreparedStatement statement = conn.prepareStatement(query);
        	ResultSet rs = statement.executeQuery();  
        	if (rs.isBeforeFirst()) {
        		String schoolName = "";
        		ArrayList<OverlapTimeBean> schoolList = new ArrayList<OverlapTimeBean>();
            	while (rs.next()) {
            		if ("".equals(schoolName)) {
            			schoolName = rs.getString("school_name");
            		}
        			OverlapTimeBean bean = new OverlapTimeBean();
        			bean.setSchoolName(rs.getString("school_name"));
        			bean.setFirstName(rs.getString("firstname"));
        			bean.setLastName(rs.getString("lastname"));
        			bean.setMiddleName(rs.getString("middlename"));
        			bean.setStudentNumber(rs.getString("student_number"));
        			bean.setByType(rs.getString("completiontype_fk"));

        			schoolList.add(bean);
            	}
        		
        		if (schoolList.isEmpty()) {	// add a "No Results Found" message
            		OverlapTimeBean bean = new OverlapTimeBean();
            		bean.setSchoolName(schoolName);
            		bean.setFirstName("No Results");
            		bean.setLastName("Found");
            		bean.setBtw("0");
            		bean.setObservation("0");;
            		bean.setTraining("0");
            		results.add(bean);        			
        		} else {
        			results.addAll(schoolList);
        		}
        	} else {	// empty result set, insert "No Records Found to Be Processed" record
        		rs.close();
        		statement.close();
        		query = "select name from school where school_pk = " + schools.get(i);
        		statement = conn.prepareStatement(query);
        		rs = statement.executeQuery();
        		rs.next();
        		OverlapTimeBean bean = new OverlapTimeBean();
        		bean.setSchoolName(rs.getString("name"));
        		bean.setFirstName("No Records Found");
        		bean.setLastName("for Process");
        		bean.setBtw("0");
        		bean.setObservation("0");;
        		bean.setTraining("0");
        		results.add(bean);
        	}
        	
        	rs.close();
        	statement.close();
    	}
    	
    	return filterUnique(results);
    }
    
    /**
     * Retrieves a list of instructors with license expiration dates that fall between startDate and endDate. 
     * @param conn
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static ArrayList<InstructorBean> getBeanCollection_InstructorLicenseExpiration(Connection conn, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();

    	qry.append("select upper(p.firstname) as firstname, upper(p.middlename) as middlename, upper(p.lastname) as lastname, ");
    	qry.append("upper(sc.name) AS school_name, p.businessphone, p.license_expire_date ");
    	qry.append("from person_school ps, person p, school sc ");
    	qry.append("where ");
    	qry.append("p.person_pk = ps.person_fk and ");
    	qry.append("sc.school_pk = ps.school_fk and ");
    	qry.append("sc.deleted = 0 ");
    	if (startDate != null && !"".equals(startDate)) {
    		qry.append("and p.license_expire_date >= convert(datetime, '" + startDate + "', 101) ");
    	}
    	if (endDate != null && !"".equals(endDate)) {
    		qry.append("and p.license_expire_date <= convert(datetime, '" + endDate + "', 101) ");
    	}
    	qry.append("order by p.license_expire_date, upper(p.firstname), upper(p.middlename), upper(p.lastname)");
    	//System.out.println("SQL> "+qry.toString());
    	
    	ArrayList<InstructorBean> results = new ArrayList<InstructorBean>();
    	
    	// get data from db
    	PreparedStatement statement = conn.prepareStatement(qry.toString());
    	ResultSet rs = statement.executeQuery();
    	while (rs.next()) {
			InstructorBean bean = new InstructorBean();
			bean.setFirstName(rs.getString("firstname"));
			bean.setMiddleName(rs.getString("middlename"));
			bean.setLastName(rs.getString("lastname"));
			bean.setSchoolName(rs.getString("school_name"));
			bean.setBusinessPhone(rs.getString("businessphone"));
			if (rs.getDate("license_expire_date") != null) {
				bean.setLicenseExpireDate(rs.getDate("license_expire_date"));
				bean.setLicenseExpireDateStr(formatDate(rs.getDate("license_expire_date")));
			}
			results.add(bean);
    	}

    	return results;
    }

    /**
     * Retrieves a list of schools with expiration dates that fall between startDate and endDate. 
     * @param conn
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static ArrayList<School> getBeanCollection_SchoolExpiration(Connection conn, String startDate, String endDate) throws Exception {
    	StringBuffer qry = new StringBuffer();

    	qry.append("select upper(sc.name) as school_name, sc.number, sc.address1, sc.address2, sc.city, sc.state, sc.zip, sc.businessphone, sc.expire_date ");
    	qry.append("from school sc ");
    	qry.append("where sc.deleted = 0");
    	if (startDate != null && !"".equals(startDate)) {
    		qry.append(" and sc.expire_date >= convert(datetime, '" + startDate + "', 101)");
    	}
    	if (endDate != null && !"".equals(endDate)) {
    		qry.append(" and sc.expire_date <= convert(datetime, '" + endDate + "', 101)");
    	}
    	qry.append(" order by upper(sc.name), sc.expire_date desc");
    	//System.out.println("SQL> "+qry.toString());
    	
    	ArrayList<School> results = new ArrayList<School>();
    	
    	// get data from db
    	PreparedStatement statement = conn.prepareStatement(qry.toString());
    	ResultSet rs = statement.executeQuery();
    	while (rs.next()) {
			School bean = new School();
			bean.setSchoolName(rs.getString("school_name"));
			bean.setSchoolNumber(rs.getString("number"));
			bean.setAddress1(rs.getString("address1"));
			bean.setAddress2(rs.getString("address2"));
			bean.setCity(rs.getString("city"));
			bean.setState(rs.getString("state"));
			bean.setZip(rs.getString("zip"));
			bean.setBusinessPhone(rs.getString("businessphone"));
			if (rs.getDate("expire_date") != null) {
				bean.setExpireDate(rs.getDate("expire_date"));
				bean.setExpireDateStr(formatDate(rs.getDate("expire_date")));
			}
			results.add(bean);
    	}

    	return results;
    }

    private static String formatDate(Date date) {
    	
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
		return df.format(date);
    }
    
    private static String formatDate2(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN2);
		return df.format(date);
    }    

    private static String formatTime(Date time) {
    	
		SimpleDateFormat df = new SimpleDateFormat(TIME_PATTERN);
		return df.format(time);
    }
    
    private static String concatString(String str1, String str2, String str3) {
    	
    	StringBuffer resultStr = new StringBuffer();
    	resultStr.append(str1 == null ? " " : str1 + " ")
    	.append(str2 == null ? " " : str2 + " ")
    	.append(str3 == null ? " " : str3);
    	
    	return resultStr.toString();
    }
    
    private static ArrayList<OverlapTimeBean> removeDuplicates(ArrayList list) {

        ArrayList<OverlapTimeBean> resultList = new ArrayList<OverlapTimeBean>();

        if (!list.isEmpty()) {
            resultList.add((OverlapTimeBean) list.get(0)); // add the first one
            for (int i = 1; i < list.size(); i++) {
                OverlapTimeBean bean = (OverlapTimeBean) list.get(i);
                int j = 0;
                while (j < resultList.size()) {
                    OverlapTimeBean rbean = (OverlapTimeBean) resultList.get(j);

                    if (bean.getInstructor_fk() == rbean.getInstructor_fk() && bean.getStartTime() == rbean.getStartTime() && bean.getEndTime() == rbean.getEndTime() && bean.getStudentName() == rbean.getStudentName() && bean.getByType() == rbean.getByType()) {
                        break;
                    }
                    j = j + 1;
                }

                if (j == resultList.size()) {
                    resultList.add(bean);
                }
            }
        }

        return resultList;
    }
    
    private static String getLimitedTesterStatement(StringBuffer sb) {
        StringBuffer s = new StringBuffer();
        s.append("select distinct(r.roadinstructor_fk) as roadinstructor_fk, " +
                "upper(p.firstname) as firstname, upper(p.middlename) as middlename, upper(p.lastname) as lastname " +
                "from completion_road r, person p " +
                "where r.roadinstructor_fk = p.person_pk ");
        if (sb.length() > 0){
            s.append(" and ");
            s.append(sb);
        }
        sb.append("order by firstname, lastname");
        return s.toString();
    }

    public static List getLimitedSchoolList(Connection conn, Integer personFk) throws Exception {

        String qry = "select distinct s.school_pk as value, upper(s.name) as name " +
        		"from school s, person_school ps where s.deleted = 0  AND s.school_pk = ps.school_fk AND " +
        		"ps.person_fk = " + personFk + " order by s.name";
        PreparedStatement statement = conn.prepareStatement(qry);
    	ResultSet rs = statement.executeQuery();
        //<isNotEmpty property="schoolType"  prepend=" AND ">s.school_type = #schoolType#</isNotEmpty>
        ArrayList<NameValueBean> list = new ArrayList<NameValueBean>();
        while (rs.next()) {
            String name = rs.getString("name");
            String value = String.valueOf(rs.getInt("value"));
            NameValueBean bean = new NameValueBean(name, value);
            list.add(bean);
        }

        return list;
    }

    public static List getLimitedTesterList(Connection conn, List schoolList) throws Exception {

        StringBuffer sb = new StringBuffer();
        String preStatement = "(r.school_number = (select number from school where school_pk = ";
        String postStatement = ")";
        String appendStatement = " or ";
        boolean first = true;

        if (!schoolList.isEmpty()) {
            for (int x = 0; x < schoolList.size(); x++) {
                NameValueBean bean = (NameValueBean) schoolList.get(x);
                if(!first){
                    sb.append(appendStatement);
                }
                sb.append(preStatement);
                sb.append(bean.getValue());
                sb.append(postStatement);
                if(first) {
                    first = false;
                }
            }
            sb.append(postStatement);
        }
        
        PreparedStatement statement = conn.prepareStatement(getLimitedTesterStatement(sb));
    	ResultSet rs = statement.executeQuery();

        ArrayList<NameValueBean> list = new ArrayList<NameValueBean>();
        while (rs.next()) {
            String name = rs.getString("firstname") + (rs.getString("middlename") == null ? " " : " " + rs.getString("middlename")) + " " + rs.getString("lastname");
            String value = String.valueOf(rs.getInt("roadinstructor_fk"));
            NameValueBean bean = new NameValueBean(name, value);

            list.add(bean);
        }

        return list;
    }
    
    /**
     * Filter result list so that there are unique student name with different training type. 
     * @param oldList
     * @return
     */
    private static ArrayList<OverlapTimeBean> filterUnique(ArrayList<OverlapTimeBean> oldList) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < oldList.size(); i++) {
    		OverlapTimeBean oldBean = oldList.get(i);
    		if (oldBean.getStudentNumber() == null) {
    			newList.add(oldBean);
    			continue;
    		}
    		
			boolean found = false;
    		for (int j = 0; j < newList.size(); j++) {
    			OverlapTimeBean newBean = newList.get(j);
    			if (oldBean.getSchoolName() != null && oldBean.getStudentNumber() != null) {
        			if (oldBean.getSchoolName().equals(newBean.getSchoolName()) && oldBean.getStudentNumber().equals(newBean.getStudentNumber())) {
        				found = true;
        		    	switch (oldBean.getByType()) {
        		    	case "1":
        		    		newBean.setBtw("YES");
        		    		break;
        		    	case "2":
        		    		newBean.setObservation("YES");
        		    		break;
        		    	case "3":
        		    		newBean.setTraining("YES");
        		    	}
        			} 
    			}
    		}
    		if (!found && oldBean.getSchoolName() != null && oldBean.getStudentNumber() != null) {
		    	switch (oldBean.getByType()) {
		    	case "1":
		    		oldBean.setBtw("YES");
		    		break;
		    	case "2":
		    		oldBean.setObservation("YES");
		    		break;
		    	case "3":
		    		oldBean.setTraining("YES");
		    	}
    			newList.add(oldBean);
    		}
    	}
    	
    	// sort it
		Collections.sort(newList, new Comparator<OverlapTimeBean>() {
			public int compare(OverlapTimeBean o1, OverlapTimeBean o2) {  
				return (o1.getLastName() + o1.getFirstName()).compareToIgnoreCase(o2.getLastName() + o2.getFirstName());
			}
		});
    	
    	return newList;
    }
    
    /**
     * Filter list so that there are unique student number, start time, end time, and by type; then sort result list by start time and student last name. 
     * @param oldList
     * @return
     */
    private static ArrayList<OverlapTimeBean> filterUnique2(ArrayList<OverlapTimeBean> oldList) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();
    	newList.add(oldList.get(0));
    	for (int i = 1; i < oldList.size(); i++) {
    		OverlapTimeBean o = oldList.get(i);
    		boolean found = false;
    		for (int j = 0; j < newList.size(); j++) {
    			OverlapTimeBean n = newList.get(j);
    			if (o.getStudentNumber().equals(n.getStudentNumber()) && o.getStartTime() == n.getStartTime() && o.getEndTime() == n.getEndTime() && 
    					o.getByType().equals(n.getByType())) {
    				found = true;
    			}
    		}
    		if (!found) {
    			newList.add(o);
    		}
    	}
    	
    	// sort the list by start_time
		Collections.sort(newList, new Comparator<OverlapTimeBean>() {
			public int compare(OverlapTimeBean o1, OverlapTimeBean o2) {
				return 	(new Long(o1.getStartTime())).compareTo(new Long(o2.getStartTime()));
			}
		});
    	
    	return newList;
    }

    private static String setList(ArrayList<OverlapTimeBean> list, ResultSet rs, String dtype, String ptype) throws Exception {
    	String schoolName = "";
		while (rs.next()) {
			if ("".equals(schoolName)) {
				schoolName = rs.getString("school_name");
			}
			
			OverlapTimeBean bean = new OverlapTimeBean();
			bean.setSchoolName(rs.getString("school_name"));
			if (!"btw_vehicle".equals(ptype) && !"btw_vehicle2".equals(ptype) && !"studentOver1".equals(ptype)) {
				bean.setFirstName(rs.getString("firstname"));
				bean.setLastName(rs.getString("lastname"));				
			}
			bean.setStudentName(rs.getString("studentfirstname") + " " + rs.getString("studentlastname"));
			bean.setStudentNumber(String.valueOf(rs.getLong("studentnumber")));
			
			if ("studentOver1".equals(ptype)) {
				bean.setStartDateStr(rs.getString("start_date"));
				bean.setByType(rs.getString("training_type"));
			} else {
				if (rs.getTimestamp("start_time") != null) {
					bean.setStartTime(rs.getTimestamp("start_time").getTime());
					bean.setStartTimeStr(formatDate2(rs.getTimestamp("start_time")) + " " + formatTime(rs.getTime("start_time")));
				}
				if (rs.getTimestamp("end_time") != null) {
					bean.setEndTime(rs.getTimestamp("end_time").getTime());
					bean.setEndTimeStr(formatDate2(rs.getTimestamp("end_time")) + " " + formatTime(rs.getTime("end_time")));
				}				
			}
			
			if (dtype != null) {
				bean.setByType(dtype);
			}
			
			if (ptype != null) {
				switch (ptype) {
				case "btw":
					bean.setBtw("0");
					break;
				case "training":
					bean.setTraining("0");
					break;
				case "observation":
					bean.setObservation("0");
					break;
				case "road":
					bean.setRoad("1");	// for instructor over6
					break;
				case "btw_road":		// for instructor over7
					bean.setBtw_road("1");
					bean.setByType(rs.getString("training_type"));
					break;
				case "btw_vehicle":		// for vehicle over1
					bean.setBtw("1");
					bean.setVehicleInfo(concatString(rs.getString("plate"), rs.getString("make"), rs.getString("year")));
					break;
				case "btw_vehicle2":		// for vehicle over2
					bean.setRoad("1");
					bean.setVehicleInfo(concatString(rs.getString("plate"), rs.getString("make"), rs.getString("year")));
					break;
				}
			}
			
			if (dtype == null && ptype == null) {
				bean.setBtw("1");	// for instructor over 5
			}
			
			list.add(bean);            		
		}
		
		return schoolName;
    }
    
    private static ArrayList<OverlapTimeBean> filterInstructor(ArrayList<OverlapTimeBean> oldList, String ptype) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();
    	if (oldList.size() == 1) return newList;	// empty list
    	
    	ArrayList<OverlapTimeBean> oldList2 = oldList;
    	
		for (int i = 0; i < oldList.size(); i++) {
			OverlapTimeBean oldBean = oldList.get(i);
			for (int j = 0; j < oldList2.size(); j++) {
				if (i != j) {
					OverlapTimeBean newBean = oldList2.get(j);
					if (oldBean.getFirstName().equals(newBean.getFirstName()) && oldBean.getLastName().equals(newBean.getLastName())) {	// don't need school name since this list is one school
						if ( (oldBean.getStartTime() < newBean.getEndTime() && oldBean.getStartTime() > newBean.getStartTime()) ||
								(newBean.getStartTime() < oldBean.getEndTime() && newBean.getStartTime() > oldBean.getStartTime()) ) {
							switch (ptype) {
							case "btw":
		    					oldBean.setBtw("1");
								break;
							case "training":
		    					oldBean.setTraining("1");
								break;
							}
							newList.add(oldBean);
							break;
						}
					} else {	// skip the rest since instructor names are sorted.
						break;
					}
				}
			}
		}
    	
    	if (newList.size() == 1) {
    		newList.remove(0);
    	}
    	
    	return newList;
    }
    
    private static ArrayList<OverlapTimeBean> filterVehicle(ArrayList<OverlapTimeBean> oldList) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();
    	if (oldList.size() == 1) return newList;	// empty list
    	
    	ArrayList<OverlapTimeBean> oldList2 = oldList;
		for (int i = 0; i < oldList.size(); i++) {
			OverlapTimeBean oldBean = oldList.get(i);
			for (int j = 0; j < oldList2.size(); j++) {
				if (i != j) {
					OverlapTimeBean newBean = oldList2.get(j);
					if (oldBean.getVehicleInfo().equalsIgnoreCase(newBean.getVehicleInfo())) {
						if ( (oldBean.getStartTime() < newBean.getEndTime() && oldBean.getStartTime() > newBean.getStartTime()) ||
								(newBean.getStartTime() < oldBean.getEndTime() && newBean.getStartTime() > oldBean.getStartTime()) ) {
							newList.add(oldBean);
							break;
						}						
					}
				}
			}
		}
    	
    	if (newList.size() == 1) {
    		newList.remove(0);
    	}
    	
    	return newList;
    }
        
    private static ArrayList<OverlapTimeBean> filterInstructor2(ArrayList<OverlapTimeBean> btw, ArrayList<OverlapTimeBean> training, ArrayList<OverlapTimeBean> observation) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < btw.size(); i++) {
    		OverlapTimeBean b = btw.get(i);
    		boolean flag = false;
    		for (int j = 0; j < training.size(); j++) {
    			OverlapTimeBean t = training.get(j);
    			if (b.getFirstName().equals(t.getFirstName()) && b.getLastName().equals(t.getLastName())) {
    				if ( (b.getStartTime() < t.getEndTime() && b.getStartTime() > t.getStartTime()) ||
    						(t.getStartTime() < b.getEndTime() && t.getStartTime() > b.getStartTime()) ) {
    					if (flag) {
        					b.setBtw_cr("1");
        					t.setBtw_cr("1");
        					newList.add(t);
    					} else {
        					b.setBtw_cr("1");
        					t.setBtw_cr("1");
        					newList.add(b);
        					newList.add(t);
        					flag = true;	
    					}    					
    				}
    			}
    		}
    		
    		for (int k = 0; k < observation.size(); k++) {
    			OverlapTimeBean o = observation.get(k);
    			if (b.getFirstName().equals(o.getFirstName()) && b.getLastName().equals(o.getLastName())) {
    				if ( (b.getStartTime() < o.getEndTime() && b.getStartTime() > o.getStartTime()) ||
    						(o.getStartTime() < b.getEndTime() && o.getStartTime() > b.getStartTime()) ) {
    					if (flag) {
    						b.setBtw_obs("1");
        					o.setBtw_obs("1");
        					newList.add(o);
    					} else {
        					b.setBtw_obs("1");
        					o.setBtw_obs("1");
        					newList.add(b);
        					newList.add(o);    						
    					}
    				}
    			}    			
    		}
    	}
    	
    	for (int l = 0; l < observation.size(); l++) {
    		OverlapTimeBean o = observation.get(l);
    		for (int m = 0; m < training.size(); m++) {
    			OverlapTimeBean t = training.get(m);
    			if (o.getFirstName().equals(t.getFirstName()) && o.getLastName().equals(t.getLastName())) {
    				if ( (o.getStartTime() < t.getEndTime() && o.getStartTime() > t.getStartTime()) ||
    						(t.getStartTime() < o.getEndTime() && t.getStartTime() > o.getStartTime()) ) {
    					int idxAddedBean = getAddedBean(newList, o);
    					if (idxAddedBean == -1) {	// not added yet
    						o.setObs_cr("1");
    						newList.add(o);
    					} else {
    						OverlapTimeBean addedBean =  newList.get(idxAddedBean);
    						addedBean.setObs_cr("1");
    					}
    					idxAddedBean = getAddedBean(newList, t);
    					if (idxAddedBean == -1) {	// not added yet
    						t.setObs_cr("1");
    						newList.add(t);
    					} else {
    						OverlapTimeBean addedBean =  newList.get(idxAddedBean);
    						addedBean.setObs_cr("1");
    					}
    				}
    			}
    		}
    	}
    	
    	if (newList.size() > 1) {	// make it unique and sort it
    		return filterUnique2(newList);
    	} else {
        	return newList;    		
    	}
    }
    
    private static ArrayList<OverlapTimeBean> filterInstructor4(ArrayList<OverlapTimeBean> road, ArrayList<OverlapTimeBean> btw, ArrayList<OverlapTimeBean> observation, ArrayList<OverlapTimeBean> training) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();
    	for (int i = 0; i < road.size(); i++) {
    		OverlapTimeBean r = road.get(i);
    		boolean flag = false;
    		for (int j = 0; j < btw.size(); j++) {
    			OverlapTimeBean b = btw.get(j);
    			if (r.getFirstName().equals(b.getFirstName()) && r.getLastName().equals(b.getLastName())) {
    				if ( (r.getStartTime() < b.getEndTime() && r.getStartTime() > b.getStartTime()) ||
    						(b.getStartTime() < r.getEndTime() && b.getStartTime() > r.getStartTime()) ) {
    					r.setRd_btw("1");
    					b.setRd_btw("1");
    					newList.add(r);
    					newList.add(b);
    					flag = true;
    				}
    			}
    		}
    		for (int k = 0; k < observation.size(); k++) {
    			OverlapTimeBean o = observation.get(k);
    			if (r.getFirstName().equals(o.getFirstName()) && r.getLastName().equals(o.getLastName())) {
    				if ( (r.getStartTime() < o.getEndTime() && r.getStartTime() > o.getStartTime()) ||
    						(o.getStartTime() < r.getEndTime() && o.getStartTime() > r.getStartTime()) ) {
    					if (flag) {
    						r.setRd_obs("1");
    						o.setRd_obs("1");
    						newList.add(o);
    					} else {
    						r.setRd_obs("1");
    						o.setRd_obs("1");
        					newList.add(r);
        					newList.add(o);
        					flag = true;
    					}
    				}
    			}
    		}
    		for (int l = 0; l < training.size(); l++) {
    			OverlapTimeBean t = training.get(l);
    			if (r.getFirstName().equals(t.getFirstName()) && r.getLastName().equals(t.getLastName())) {
    				if ( (r.getStartTime() < t.getEndTime() && r.getStartTime() > t.getStartTime()) ||
    						(t.getStartTime() < r.getEndTime() && t.getStartTime() > r.getStartTime()) ) {
    					if (flag) {
    						r.setRd_cr("1");
    						t.setRd_cr("1");
    						newList.add(t);
    					} else {
    						r.setRd_cr("1");
    						t.setRd_cr("1");
        					newList.add(r);
        					newList.add(t);
    					}
    				}
    			} 
    		}
    	}
    	
    	return newList;
    }
    
    private static int getAddedBean(ArrayList<OverlapTimeBean> list, OverlapTimeBean bean) {
    	int index = -1;
    	for (int i = 0; i < list.size(); i++) {
    		OverlapTimeBean ab = list.get(i);
    		if (bean.getFirstName().equals(ab.getFirstName()) && bean.getLastName().equals(ab.getLastName()) && bean.getByType().equals(ab.getByType())) {
    			index = i;
    			break;
    		}
    	}
    	
    	return index;
    }
    
    /**
     * Filters records to have unique student names on same date with different training types. 
     * @param oldList
     * @return
     */
    private static ArrayList<OverlapTimeBean> filterStudent1(ArrayList<OverlapTimeBean> oldList) {
    	ArrayList<OverlapTimeBean> newList = new ArrayList<OverlapTimeBean>();

    	for (int i = 0; i < oldList.size(); i++) {
    		OverlapTimeBean o = oldList.get(i);
    		int indexFound = -1;
    		for (int j = newList.size() - 1; j >= 0; j--) {
    			OverlapTimeBean n = newList.get(j);
    			if (o.getStudentNumber().equals(n.getStudentNumber()) && o.getStartDateStr().equals(n.getStartDateStr())) {
    				indexFound = j;
    				break;
    			}
    		}
    		
    		if (indexFound == -1) {	// add new record
    			switch (o.getByType()) {
    			case "Classroom":
    				o.setTrainingInt(o.getTrainingInt() + 1);
    				break;
    			case "BTW":
    				o.setBtwInt(o.getBtwInt() + 1);
    				break;
    			case "Observation":
    				o.setObservationInt(o.getObservationInt() + 1);
    				break;
    			}
    			newList.add(o);
    		} else {	// update existing record
    			OverlapTimeBean n = newList.get(indexFound);
    			switch (o.getByType()) {
    			case "Classroom":
    				n.setTrainingInt(o.getTrainingInt() + 1);
    				break;
    			case "BTW":
    				n.setBtwInt(o.getBtwInt() + 1);
    				break;
    			case "Observation":
    				n.setObservationInt(o.getObservationInt() + 1);
    				break;
    			}
    		}
    	}
    	
    	return newList;
    }
   
}

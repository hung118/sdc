package gov.utah.dts.sdc.jasper;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.util.CollectionComparator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Servlet generates report in PDF format.
 * Syntax:  1) PDFReport?report=<name>
 * where name is a Jasper report name without the extension (.jrxml) which is stored in /jasper
 * root of this application.
 * Example: PDFReport?report=BANKTRANS
 *
 * @author HNGUYEN
 * @since: August 29, 2007
 */
@SuppressWarnings({"rawtypes", "unused"})
public class PDFReportGenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    static final String DATA_SOURCE = "java:comp/env/sdcDataSource";
    private Person loggedInPerson;
    private Integer umdPersonPk;
    private static HashMap<String, String> reportTitle = new HashMap<String, String>();
    static {
        /*
Student Overlapping Time 1 		Students Multi Training
Student Overlapping Time 2		Students Multi Training By Type
Instructor Overlapping Time 1		Instructor Multi BTW
Instructor Overlapping Time 2		Instructor Multi Training By Type
Instructor Overlapping Time 3		Instructor Multi Classroom	
Instructor Overlapping Time 4		Training and Testing Overlap
Instructor Overlapping Time 5		Training over 10 hours
Instructor Overlapping Time 6		Testing over 10 hours
Instructor Overlapping Time 7           Training and Testing over 10 hours
Vehicle Overlapping Time 1		Vehicle Training Overlap	
Vehicle Overlapping Time 2		Vehicle Testing Overlap
        */
        reportTitle.put("roadTester", "Road Tester Statistics");
        reportTitle.put("schoolCompletion", "School Completions");
        reportTitle.put("studentOver1", "Students Multi Training");
        reportTitle.put("studentOver2", "Students Multi Training By Type");
        reportTitle.put("instructorOver1", "Instructor Multi BTW");
        reportTitle.put("instructorOver2", "Instructor Multi Training By Type");
        reportTitle.put("instructorOver3", "Instructor Multi Classroom");
        reportTitle.put("instructorOver4", "Training and Testing Overlap");
        reportTitle.put("instructorOver5", "Training over 10 hours");
        reportTitle.put("instructorOver6", "Testing over 10 hours");
    	reportTitle.put("instructorOver7", "Training and Testing over 10 hours");
    	reportTitle.put("vehicleOver1", "Vehicle Training Overlap");
        reportTitle.put("vehicleOver2", "Vehicle Testing Overlap");
    	reportTitle.put("testerOver1", "Road Tester Overlap");
        reportTitle.put("bondList", "List of Surety Bond Companies");
        reportTitle.put("instructorList", "SDC User Report");
        reportTitle.put("schoolList", "Public List of Schools");
        reportTitle.put("testerList", "Public List of Testers");
        reportTitle.put("instructorExpiration", "Expiration of Instructor Licenses");
        reportTitle.put("schoolExpiration", "School Expirations");
    }
    Connection connection;
    // image name which should be in images directory at the root of application
    static final String STATE_SEAL = "state_seal_small.jpg";
    static final String PRE_REPORT_PARAMS = "/WEB-INF/jasper/";
    static final String PRE_JSP_PARAMS = "/WEB-INF/pages/reports";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {

        RequestDispatcher rd;
        try {
            // get db connection
            getConnection();

            if ("initSchoolCompletion".equals(request.getParameter("report"))) {
                if (request.isUserInRole(Constants.Role_Admin)) {
                    request.setAttribute("schoolList", Dao.getSchoolList(connection));
                } else {
                    request.setAttribute("schoolList", Dao.getLimitedSchoolList(connection, getUmdPersonPk(request)));
                }
                rd = this.getServletContext().getRequestDispatcher(PRE_JSP_PARAMS + "/schoolCompletion.jsp");
                rd.forward(request, response);
            } else if ("initRoadTester".equals(request.getParameter("report"))) {
                if (request.isUserInRole(Constants.Role_Admin)) {
                    request.setAttribute("testerList", Dao.getTesterList(connection));
                } else {
                	request.setAttribute("testerList", getRoadTester(getLoggedInPerson(request)));
                }
                rd = this.getServletContext().getRequestDispatcher(PRE_JSP_PARAMS + "/roadTester.jsp");
                rd.forward(request, response);
            } else if ("initInstructorExpiration".equals(request.getParameter("report"))) {
                rd = this.getServletContext().getRequestDispatcher(PRE_JSP_PARAMS + "/instructorExpiration.jsp");
                rd.forward(request, response);
            } else if ("initSchoolExpiration".equals(request.getParameter("report"))) {
                rd = this.getServletContext().getRequestDispatcher(PRE_JSP_PARAMS + "/schoolExpiration.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("showPage") != null) {
                rd = this.getServletContext().getRequestDispatcher(PRE_JSP_PARAMS + 
                		"/" + request.getParameter("page") + ".jsp");
                rd.forward(request, response);            	
            } else {
                createReport(request, response);
            }
        } catch (Exception e) {
            // show error page
            e.printStackTrace();
            request.setAttribute("errorMsg", e.getMessage());
            rd = this.getServletContext().getRequestDispatcher("/reportErrorPage.jsp");
            rd.forward(request, response);
        } finally {
            closeConnection();
        }
    }

    /** Creates pdf report using JasperReports.
     */
	private void createReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletOutputStream sos = null;

        try {
            //HttpSession session = request.getSession();
            // get Jasper report xml (*.jrxml) full path file name (c:/tomcat/webapps/JasperSample/reports/sample.jrxml)
            ServletContext context = this.getServletContext();
            String reportName = request.getParameter("report");
            String pathName = context.getRealPath(PRE_REPORT_PARAMS + reportName);

            // 1) Compile .jrxml report design to produce .jasper object
            JasperReport reportJasper = JasperCompileManager.compileReport(pathName + ".jrxml");
            JasperPrint reportPrint = null;

            // 2) Fill the compiled report design with data and produce the .jrprint object.
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("stateSealImage", context.getRealPath(PRE_REPORT_PARAMS + "images/" + STATE_SEAL));
            parameters.put("reportTitle", reportTitle.get(reportName));

            // Retrieve form parameters
        	String startDate = "";
        	String endDate = "";
        	if (request.getParameter("reportStartDate") != null) {
        		startDate = request.getParameter("reportStartDate");
        	}
        	if (request.getParameter("reportEndDate") != null) {
        		endDate = request.getParameter("reportEndDate");
        	}

            if (request.getParameter("overlap") != null ||
            	"roadTester".equals(reportName) ||
            	"schoolCompletion".equals(reportName) ||
            	"schoolExpiration".equals(reportName) ||
            	"instructorExpiration".equals(reportName))
            {
                parameters.put("startDate", startDate);
                parameters.put("endDate", endDate);            	
            }

            if ("roadTester".equals(reportName)) {
            	String schoolType = "";
            	String[] schoolArray = {};
            	String[] testerArray = {};
            	java.util.List<String> schools = new ArrayList<String>();
            	java.util.List<String> testers = new ArrayList<String>();
            	if (request.getParameter("schoolType") != null) {
            		schoolType = request.getParameter("schoolType");
            	}
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		schools = Arrays.asList(schoolArray);
            	}
            	if (request.getParameterValues("roadinstructor_fk") != null) {
            		testerArray = request.getParameterValues("roadinstructor_fk");
            		testers = Arrays.asList(testerArray);
            	}
                ArrayList<RoadTesterBean> beanCollection = Dao.getBeanCollection_RoadTester(connection, schoolType, schools, testers, startDate, endDate);
                //System.out.println(">>> Records returned: "+beanCollection.size());
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("studentOver1".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
                ArrayList beanCollection = Dao.getBeanCollection_Student1(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("studentOver2".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}

            	ArrayList beanCollection = Dao.getBeanCollection_Student2(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver1".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}

            	ArrayList beanCollection = Dao.getBeanCollection_Instructor1(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver2".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
            	ArrayList beanCollection = Dao.getBeanCollection_Instructor2(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver3".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
                ArrayList beanCollection = Dao.getBeanCollection_Instructor3(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver4".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
                ArrayList beanCollection = Dao.getBeanCollection_Instructor4(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver5".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
                ArrayList beanCollection = Dao.getBeanCollection_Instructor5(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver6".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}

            	ArrayList beanCollection = Dao.getBeanCollection_Instructor6(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorOver7".equals(reportName)){
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
            	ArrayList beanCollection = Dao.getBeanCollection_Instructor7(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
            	reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("vehicleOver1".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}

            	ArrayList beanCollection = Dao.getBeanCollection_Vehicle1(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("vehicleOver2".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}

                ArrayList beanCollection = Dao.getBeanCollection_Vehicle2(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("testerOver1".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
            	
            	ArrayList beanCollection = Dao.getBeanCollection_Tester1(connection, schools, request.getParameter("reportStartDate"), request.getParameter("reportEndDate"));
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("schoolCompletion".equals(reportName)) {
            	String[] schoolArray = {};
            	List<String> schools = new ArrayList<String>();
            	if (request.getParameterValues("school_fk") != null) {
            		schoolArray = request.getParameterValues("school_fk");
            		if (schoolArray.length > 1 || !"0".equals(schoolArray[0])) {
            			schools = Arrays.asList(schoolArray);
            		}
            	}
                ArrayList<OverlapTimeBean> beanCollection = Dao.getBeanCollection_SchoolCompletion(connection, schools, startDate, endDate);
            	long btwCount = 0l;
            	long obsCount = 0l;
            	long trainCount = 0l;
            	for (int i=0; i<beanCollection.size(); i++) {
            		OverlapTimeBean bean = beanCollection.get(i);
        			if ("YES".equals(bean.getBtw())) btwCount++;
        			if ("YES".equals(bean.getObservation())) obsCount++;
        			if ("YES".equals(bean.getTraining())) trainCount++;
            	}
            	parameters.put("training", String.valueOf(trainCount));
            	parameters.put("btw", String.valueOf(btwCount));
            	parameters.put("observation", String.valueOf(obsCount));

                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("instructorExpiration".equals(reportName)){
            	ArrayList<InstructorBean> beanCollection = Dao.getBeanCollection_InstructorLicenseExpiration(connection, startDate, endDate);
            	reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else if ("schoolExpiration".equals(reportName)){
            	ArrayList<School> beanCollection = Dao.getBeanCollection_SchoolExpiration(connection, startDate, endDate);
            	reportPrint = JasperFillManager.fillReport(reportJasper, parameters, new JRBeanCollectionDataSource(beanCollection));
            } else {
                reportPrint = JasperFillManager.fillReport(reportJasper, parameters, connection);
            }

            // 3) Export a PDF version of the report by converting the .jrprint object.
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, reportPrint);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();
            byte[] reportBytes = baos.toByteArray();

            // display PDF report on client browser
            response.setContentType("application/pdf");
            response.setContentLength(reportBytes.length);
            sos = response.getOutputStream();

            sos.write(reportBytes, 0, reportBytes.length);
            sos.flush();
        } finally {
            if (sos != null) {
                sos.close();
            }
        }
    }

    private void getConnection() throws Exception {

        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(DATA_SOURCE);
        connection = ds.getConnection();
        if (connection == null) {
            throw new Exception("Failed to get database connection.");
        } else {
            return;
        }
    }

    private void getPooledConnection() throws Exception {
        Context ctx = new InitialContext();
        ConnectionPoolDataSource ds = (ConnectionPoolDataSource) ctx.lookup(DATA_SOURCE);
        PooledConnection pooledConnection = ds.getPooledConnection();
        connection = pooledConnection.getConnection(); // Obtain connection from pool
        if (connection == null) {
            throw new Exception("Failed to get database connection.");
        } else {
            return;
        }
    }

    /** Closes db connection.
     * @throw Exception if an error occurs.
     */
    private void closeConnection() throws Exception {

        connection.close();
        connection = null;
    }

    /** Gets where clause string.
     * @param request an HttpServletRequest object.
     * @return a string
     * @throw Exception if an error occurs.
     */
    private String getWhereClause(HttpServletRequest request) throws Exception {

        String[] schoolFk = request.getParameterValues("schoolFk");
        if ("0".equals(schoolFk[0])) {
            return " ";
        }

        StringBuffer whereClause = new StringBuffer(" where school_pk in (" + schoolFk[0]);
        for (int i = 1; i < schoolFk.length; i++) {
            whereClause.append(", " + schoolFk[i]);
        }
        whereClause.append(") ");

        return whereClause.toString();
    }
    
	private String getWhereDate(HttpServletRequest request) throws Exception {
    
    	String startDate = request.getParameter("reportStartDate");
    	String endDate = request.getParameter("reportEndDate");
    	String result = " ";
    	
    	if (!"".equals(startDate) && !"".equals(endDate)) {
    		result = " and st.date <= convert(datetime, '" + endDate + "', 101)" +
    		" and st.date  >= convert(datetime, '" + startDate + "', 101)";
    	}
    	
    	return result;
    }

    private String getWhereExp(HttpServletRequest request) throws Exception {
    	
    	String expiredDate = request.getParameter("expiredDate");
    	String result = " ";
    	
    	if (!"".equals(expiredDate)) {
    		result = "where convert(datetime, '" + expiredDate + "', 101) > license_expire_date";
    	} else {
    		result = "where CURRENT_TIMESTAMP > license_expire_date";
    	}
    	
    	return result;
    }
    
    @SuppressWarnings("unchecked")
	private Collection sortCollection(List list) {
    	
    	CollectionComparator comparator = new CollectionComparator();
    	Collections.sort(list, comparator);
    	
    	return list;
    }
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

    public Person getLoggedInPerson(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (loggedInPerson == null) {
            Person temp = (Person) session.getAttribute(Constants.USER_KEY);
            setLoggedInPerson(temp);
        }
        return loggedInPerson;
    }

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public Integer getUmdPersonPk(HttpServletRequest request) {
        if (umdPersonPk == null) {
            Integer pk = getLoggedInPerson(request).getPersonPk();
            setUmdPersonPk(pk);
        }
        return umdPersonPk;
    }

    public void setUmdPersonPk(Integer pk) {
        this.umdPersonPk = pk;
    }
    
    private List<NameValueBean> getRoadTester(Person tester) {
    	
    	ArrayList<NameValueBean> list = new ArrayList<NameValueBean>();
    	NameValueBean nameValue = new NameValueBean(tester.getFirstName() + " " + tester.getLastName(), 
    			String.valueOf(tester.getPersonPk()));
    	list.add(nameValue);
    	return list;
    }
}

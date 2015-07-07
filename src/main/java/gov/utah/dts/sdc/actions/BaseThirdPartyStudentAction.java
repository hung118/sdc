package gov.utah.dts.sdc.actions;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.ThirdPartyTimes;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.webservice.DriversLicenseValidation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({ "serial", "rawtypes", "unused", "unchecked" })
public class BaseThirdPartyStudentAction extends SDCSupport {

    protected static Log log = LogFactory.getLog(BaseThirdPartyStudentAction.class);
    private Integer studentPk;
    private Integer schoolPk;
    private Long studentNumber;
    private Integer classroomPk;
    private Integer branchFk;
    private Integer section;
    private String schoolName;
    private String fileNumber;
    private Student currentStudent;
    private ArrayList studentArray;
    private Integer instructorFk;
    private Date observationDate;
    private Date trainingDate;
    private Date behindTheWheelDate;
    private Date observationCompletionDate;
    private Date trainingCompletionDate;
    private Date btwCompletionDate;
    private Integer vehicleFk;
    private String observationStartTime;
    private String observationEndTime;
    private String trainingStartTime;
    private String trainingEndTime;
    private String behindTheWheelStartTime;
    private String behindTheWheelEndTime;
    private String editStudentSubmit;
    private Integer trainingHours;
    private Integer behindTheWheelHours;
    private Integer observationHours;
    private Integer homeStudy;
    private Date eligibilityDate;
    private Collection branchList;
    private Integer schoolFk;

    public boolean checkRoster(Integer student, Integer classroom) throws Exception {
        boolean answer = true;
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentFk", student);
        hm.put("classroomFk", classroom);
        int i = getClassroomService().getEqualsCount(hm);
        if (i < 1) {
            answer = false;
        }
        return answer;
    }

    public int addRoster(Integer student, Integer classroom) throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentFk", student);
        hm.put("classroomFk", classroom);
        return getClassroomService().addStudent(hm);
    }

    public Long createStudentNumber(Integer classroom) throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomFk", classroom);
        return getClassroomService().getStudentNumber(hm);
    }

    public Collection getInstructorList() throws Exception {
        List instructorList = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomPk", getClassroomPk());
        instructorList = getPersonService().getClassroomInstructorSearch(hm);
        return instructorList;
    }

    public Collection getVehicleList() throws Exception {
        List list = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomPk", getClassroomPk());
        list = getVehicleService().getClassroomVehicleSearch(hm);
        return list;
    }

    public Collection getTrainingList() throws Exception {
        List list = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomPk", getClassroomPk());
        hm.put("studentPk", getStudentPk());
        list = getStudentService().getThirdPartyTraining(hm);
        return list;
    }

    public Date getTrainingTime() throws Exception {
        List list = (List) getTrainingList();
        Long totalTime = 0l;
        for (int i = 0; i < list.size(); i++) {
            ThirdPartyTimes time = (ThirdPartyTimes) list.get(i);
            Long startTime = time.getStartTime().getTime();
            Long endTime = time.getEndTime().getTime();
            totalTime = totalTime + (endTime - startTime);
        }
        Date today = getTodaysDate();
        return new Date(today.getTime() + totalTime);
    }

    public Collection getBehindTheWheelList() throws Exception {
        List list = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomPk", getClassroomPk());
        hm.put("studentPk", getStudentPk());
        list = getStudentService().getThirdPartyBehindTheWheel(hm);
        return list;
    }

    public Date getBehindTheWheelTime() throws Exception {
        List list = (List) getBehindTheWheelList();
        Long totalTime = 0l;
        for (int i = 0; i < list.size(); i++) {
            ThirdPartyTimes time = (ThirdPartyTimes) list.get(i);

            Long startTime = time.getStartTime().getTime();
            Long endTime = time.getEndTime().getTime();
            totalTime = totalTime + (endTime - startTime);
        }
        Date today = getTodaysDate();
        return new Date(today.getTime() + totalTime);
    }

    public Collection getObservationList() throws Exception {
        List list = new ArrayList();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomPk", getClassroomPk());
        hm.put("studentPk", getStudentPk());
        list = getStudentService().getThirdPartyObservation(hm);
        return list;
    }

    public Date getObservationTime() throws Exception {
        List list = (List) getObservationList();
        Long totalTime = 0l;
        for (int i = 0; i < list.size(); i++) {
            ThirdPartyTimes time = (ThirdPartyTimes) list.get(i);

            Long startTime = time.getStartTime().getTime();
            Long endTime = time.getEndTime().getTime();
            totalTime = totalTime + (endTime - startTime);
        }
        Date today = getTodaysDate();
        return new Date(today.getTime() + totalTime);
    }

    public String inputObservationTime() throws Exception {
        log.debug("inputObservationTime");
        return INPUT;
    }

    public String singleInsertObservation() throws Exception {
        log.debug("singleInsertObservation");
        return insertObservationTime();
    }

    public String multipleInsertObservation() throws Exception {
        log.debug("multipleInsertObservation");
        String retVal = insertObservationTime();
        if (retVal.equals(SUCCESS)) {
            if (getEditStudentSubmit() != null) {
                if (!getEditStudentSubmit().equals("")) {
                    return Constants.EDITSTUDENT;
                } else {
                    return SUCCESS;
                }
            } else {
                return SUCCESS;
            }
        } else {
            return retVal;
        }
    }

    public String insertObservationTime() throws Exception {
        log.debug("insertObservationTime");
        //log.debug("observationDate truncated "+ truncateDate(getObservationDate()));
        int start = decodeTime(observationStartTime);
        Date startDate = new Date(getObservationDate().getTime() + start);
        log.debug("startDate " + startDate.toString());

        int end = decodeTime(observationEndTime);
        Date endDate = new Date(getObservationDate().getTime() + end);
        log.debug("endDate " + endDate.toString());

        if (endDate.before(startDate)) {
            addActionError("Start Time Must Be Before End Time");
        } else {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("observationStartTime", startDate);
            hm.put("observationEndTime", endDate);
            hm.put("vehicleFk", vehicleFk);
            hm.put("instructorFk", instructorFk);
            hm.put("classroomFk", classroomPk);
            hm.put("branchFk", branchFk);
            for (int x = 0; x < studentArray.size(); x++) {
                hm.put("studentFk", studentArray.get(x));
                getStudentService().insertObservation(hm);
            }
        }

        if (hasErrors()) {
            return INPUT;
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("<br/>Observation Times Added For ");
            sb.append(studentArray.size());
            if (studentArray.size() == 1) {
                sb.append(" Student");
            } else {
                sb.append(" Students");
            }
            Collection<Object> col = new ArrayList<Object>();
            col.add(sb);
            setThirdPartyAjaxMessages(col);
            return SUCCESS;
        }
    }

    public String inputTrainingTime() throws Exception {
        log.debug("inputTrainingTime");
        return INPUT;
    }

    public String singleInsertTraining() throws Exception {
        log.debug("singleInsertTraining");
        return insertTrainingTime();
    }

    public String multipleInsertTraining() throws Exception {
        log.debug("multipleInsertTraining");
        String retVal = insertTrainingTime();
        if (retVal.equals(SUCCESS)) {
            if (getEditStudentSubmit() != null) {
                if (!getEditStudentSubmit().equals("")) {
                    return Constants.EDITSTUDENT;
                } else {
                    return SUCCESS;
                }
            } else {
                return SUCCESS;
            }
        } else {
            return retVal;
        }
    }

    public String insertTrainingTime() throws Exception {
        log.debug("insertTrainingTime");
        //log.debug("observationDate truncated "+ truncateDate(getObservationDate()));
        int start = decodeTime(trainingStartTime);
        Date startDate = new Date(getTrainingDate().getTime() + start);
        log.debug("startDate " + startDate.toString());

        int end = decodeTime(trainingEndTime);
        Date endDate = new Date(getTrainingDate().getTime() + end);
        log.debug("endDate " + endDate.toString());

        if (endDate.before(startDate)) {
            addActionError("Start Time Must Be Before End Time");
        } else {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("trainingStartTime", startDate);
            hm.put("trainingEndTime", endDate);
            hm.put("section", section);
            hm.put("vehicleFk", vehicleFk);
            hm.put("instructorFk", instructorFk);
            hm.put("classroomFk", classroomPk);
            hm.put("branchFk", branchFk);
            for (int x = 0; x < studentArray.size(); x++) {
                hm.put("studentFk", studentArray.get(x));
                getStudentService().insertTraining(hm);
            }
        }

        if (hasErrors()) {
            return INPUT;
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("<br/>Training Times Added For ");
            sb.append(studentArray.size());
            if (studentArray.size() == 1) {
                sb.append(" Student");
            } else {
                sb.append(" Students");
            }
            Collection<Object> col = new ArrayList<Object>();
            col.add(sb);
            setThirdPartyAjaxMessages(col);
            return SUCCESS;
        }
    }

    public String insertBtwTime() throws Exception {
        log.debug("insertBtwTime");
        //log.debug("observationDate truncated "+ truncateDate(getObservationDate()));
        int start = decodeTime(behindTheWheelStartTime);
        Date startDate = new Date(getBehindTheWheelDate().getTime() + start);
        log.debug("startDate " + startDate.toString());

        int end = decodeTime(behindTheWheelEndTime);
        Date endDate = new Date(getBehindTheWheelDate().getTime() + end);
        log.debug("endDate " + endDate.toString());

        if (endDate.before(startDate)) {
            addActionError("Start Time Must Be Before End Time");
        } else {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("btwStartTime", startDate);
            hm.put("btwEndTime", endDate);
            hm.put("vehicleFk", vehicleFk);
            hm.put("instructorFk", instructorFk);
            hm.put("classroomFk", classroomPk);
            hm.put("branchFk", branchFk);
            for (int x = 0; x < studentArray.size(); x++) {
                hm.put("studentFk", studentArray.get(x));
                getStudentService().insertBehindTheWheel(hm);
            }
        }

        if (hasErrors()) {
            return INPUT;
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("<br/>Behind The Wheel Times Added For ");
            sb.append(studentArray.size());
            if (studentArray.size() == 1) {
                sb.append(" Student");
            } else {
                sb.append(" Students");
            }
            Collection<Object> col = new ArrayList<Object>();
            col.add(sb);
            setThirdPartyAjaxMessages(col);
            return SUCCESS;
        }
    }

    public String singleInsertBtw() throws Exception {
        log.debug("singleInsertBtw");
        return insertBtwTime();
    }

    public String insertCompletionDates() throws Exception {
        log.debug("insertCompletionDates");
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("observationCompletionDate", observationCompletionDate);
        hm.put("classroomCompletionDate", trainingCompletionDate);
        hm.put("behindWheelCompletionDate", btwCompletionDate);
        hm.put("studentPk", studentPk);
        //hm.put("classroomFk", classroomPk);
        getStudentService().update(hm);
        return SUCCESS;
    }

    public CompletionByDLResponseType getCompletionByDl() throws Exception {
        Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
        String umdLogon = loggedInPerson.getEmail();

        DriversLicenseValidation dlv = new DriversLicenseValidation();
        String ccDate = sendStringDate(getTrainingCompletionDate());
        String ocDate = sendStringDate(getObservationCompletionDate());
        String btwDate = sendStringDate(getBtwCompletionDate());
        
        /* old web service - 12/01/2012
        dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(umdLogon, getFileNumber(), ccDate, ocDate, "", btwDate, "", schoolNumber));
        Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_CompletionByDL));
		*/
        String schoolNumber = getSchoolNumber();
        
		SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
		CompletionByDLRequestType wsRequest = new CompletionByDLRequestType();
		wsRequest.setLicenseNumber(getFileNumber());
		StudentDriverCertificateType sdc = new StudentDriverCertificateType();
		if (getTrainingCompletionDate() != null) {
			sdc.setClassroomCompletionDate(getTrainingCompletionDate());
			sdc.setClassroomSchoolId(schoolNumber);			
		}
		if (getObservationCompletionDate() != null) {
			sdc.setObservationCompletionDate(getObservationCompletionDate());
			sdc.setObservationSchoolId(schoolNumber);			
		}
		if (getBtwCompletionDate() != null) {
			sdc.setWheelCompletionDate(getBtwCompletionDate());
			sdc.setWheelSchoolId(schoolNumber);			
		}
		wsRequest.setStudentDriverCertificate(sdc);
		
		return wsService.completionByDL(wsRequest);
    }

    public String getSchoolNumber() throws DaoException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("schoolPk", getSchoolPk());
        School school = getSchoolService().getSchool(hm);
        String schoolNumber = school.getSchoolNumber();
        return schoolNumber;
    }

    public String generateFullCompletion() throws Exception {
        log.debug("generateFullCompletion");
        int dbCode = 0;

        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("studentPk", getStudentPk());
        hm.put("schoolFk", getSchoolPk());

        dbCode = getStudentService().update(hm);
        hm.remove("schoolFk");

        Student student = getStudentService().getStudent(hm);

        if (student.getStudentPk() != null) {
            getSession().put(Constants.Report_FullCompletionStudent, student);
            return Constants.REPORT; //return pdf
        } else {
            addActionError("Completion certificate not generated.");
        }

        return SUCCESS;
    }

    public Integer getClassroomPk() {
        return classroomPk;
    }

    public void setClassroomPk(Integer classroomPk) {
        this.classroomPk = classroomPk;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public ArrayList getStudentArray() {
        return studentArray;
    }

    public void setStudentArray(ArrayList studentArray) {
        this.studentArray = studentArray;
    }

    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void setThirdPartyAjaxMessages(Collection cmj) {
        /*
        Object[] al = cmj.toArray();
        for(int i=0;i<al.length;i++){
        log.debug("setThirdPartyAjaxMessages " + al[i]);
        }
         */
        getSession().put(Constants.ThirdParty_Ajax_Message, cmj);
    }

    public Integer getInstructorFk() {
        return instructorFk;
    }

    public void setInstructorFk(Integer instructorFk) {
        this.instructorFk = instructorFk;
    }

    public Date getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(Date observationDate) {
        this.observationDate = observationDate;
    }

    public Integer getVehicleFk() {
        return vehicleFk;
    }

    public void setVehicleFk(Integer vehicleFk) {
        this.vehicleFk = vehicleFk;
    }

    public Date getBehindTheWheelDate() {
        return behindTheWheelDate;
    }

    public void setBehindTheWheelDate(Date behindTheWheelDate) {
        this.behindTheWheelDate = behindTheWheelDate;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getBehindTheWheelEndTime() {
        return behindTheWheelEndTime;
    }

    public void setBehindTheWheelEndTime(String behindTheWheelEndTime) {
        this.behindTheWheelEndTime = behindTheWheelEndTime;
    }

    public String getBehindTheWheelStartTime() {
        return behindTheWheelStartTime;
    }

    public void setBehindTheWheelStartTime(String behindTheWheelStartTime) {
        this.behindTheWheelStartTime = behindTheWheelStartTime;
    }

    public String getObservationEndTime() {
        return observationEndTime;
    }

    public void setObservationEndTime(String observationEndTime) {
        this.observationEndTime = observationEndTime;
    }

    public String getObservationStartTime() {
        return observationStartTime;
    }

    public void setObservationStartTime(String observationStartTime) {
        this.observationStartTime = observationStartTime;
    }

    public String getTrainingEndTime() {
        return trainingEndTime;
    }

    public void setTrainingEndTime(String trainingEndTime) {
        this.trainingEndTime = trainingEndTime;
    }

    public String getTrainingStartTime() {
        return trainingStartTime;
    }

    public void setTrainingStartTime(String trainingStartTime) {
        this.trainingStartTime = trainingStartTime;
    }

    public String getEditStudentSubmit() {
        return editStudentSubmit;
    }

    public void setEditStudentSubmit(String editStudentSubmit) {
        this.editStudentSubmit = editStudentSubmit;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getTrainingHours() throws Exception {
        if (trainingHours == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("H");
            Date date = getTrainingTime();
            String str = sdf.format(date);
            setTrainingHours(Integer.parseInt(str));
        }
        return trainingHours;
    }

    public void setTrainingHours(Integer trainingHours) {
        this.trainingHours = trainingHours;
    }

    public Integer getBehindTheWheelHours() throws Exception {
        if (behindTheWheelHours == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("H");
            Date date = getBehindTheWheelTime();
            String str = sdf.format(date);
            setBehindTheWheelHours(Integer.parseInt(str));
        }
        return behindTheWheelHours;
    }

    public void setBehindTheWheelHours(Integer behindTheWheelHours) {
        this.behindTheWheelHours = behindTheWheelHours;
    }

    public Integer getObservationHours() throws Exception {
        if (observationHours == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("H");
            Date date = getObservationTime();
            String str = sdf.format(date);
            setObservationHours(Integer.parseInt(str));
        }
        return observationHours;
    }

    public void setObservationHours(Integer observationHours) {
        this.observationHours = observationHours;
    }

    public Date getBtwCompletionDate() {
        return btwCompletionDate;
    }

    public void setBtwCompletionDate(Date btwCompletionDate) {
        this.btwCompletionDate = btwCompletionDate;
    }

    public Date getObservationCompletionDate() {
        return observationCompletionDate;
    }

    public void setObservationCompletionDate(Date observationCompletionDate) {
        this.observationCompletionDate = observationCompletionDate;
    }

    public Date getTrainingCompletionDate() {
        return trainingCompletionDate;
    }

    public void setTrainingCompletionDate(Date trainingCompletionDate) {
        this.trainingCompletionDate = trainingCompletionDate;
    }

    public Integer getHomeStudy() {
        return homeStudy;
    }

    public void setHomeStudy(Integer homeStudy) {
        this.homeStudy = homeStudy;
    }

    public Integer getSchoolPk() throws DaoException {
        if (schoolPk == null && getClassroomPk() != null) {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("classroomPk", getClassroomPk());
            Classroom classroom = getClassroomService().getSchoolInfo(hm);
            setSchoolPk(classroom.getSchoolFk());
        }
        return schoolPk;
    }

    public void setSchoolPk(Integer schoolPk) {
        this.schoolPk = schoolPk;
    }

    public Date getEligibilityDate() {
        return eligibilityDate;
    }

    public void setEligibilityDate(Date eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    public Collection getBranchList() throws Exception {
        if (branchList == null) {
            List list = new ArrayList();
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("classroomPk", getClassroomPk());
            list = getClassroomService().getBranchList(hm);
            setBranchList(list);
        }
        return branchList;
    }

    public void setBranchList(Collection branchList) {
        this.branchList = branchList;
    }

    public Integer getBranchFk() {
        return branchFk;
    }

    public void setBranchFk(Integer branchFk) {
        this.branchFk = branchFk;
    }
    
    public Integer getSchoolFk() {
        return schoolFk;
    }
    
    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }
    
    public void setCommercialAjaxMessages(Collection cmj) {
        /*
        Object[] al = cmj.toArray();
        for(int i=0;i<al.length;i++){
        log.debug("setCommercialAjaxMessages " + al[i]);
        }
         */
        getSession().put(Constants.Commercial_Ajax_Message, cmj);
    }
    
}
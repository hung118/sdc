package gov.utah.dts.sdc.actions;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByDLResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.CompletionByNameResponseType;
import gov.utah.dps.dld.webservice.sdct.gen.PersonType;
import gov.utah.dps.dld.webservice.sdct.gen.StudentDriverCertificateType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.CommercialTimes;
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
public class BaseCommercialStudentAction extends SDCSupport {

	protected static Log log = LogFactory
			.getLog(BaseCommercialStudentAction.class);
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
	private Integer timePk;
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
	private CommercialTimes currentCommercialTimes;
	private Integer ocsScore;
	private String note;
	
	private String resultMsg = "";

	public boolean checkRoster(Integer student, Integer classroom)
			throws Exception {
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
		list = getStudentService().getCommercialTraining(hm);
		return list;
	}

	public Integer getTrainingTime() throws Exception {
		List list = (List) getTrainingList();
		Long totalTime = 1L; // Set greater than 0
		for (int i = 0; i < list.size(); i++) {
			CommercialTimes time = (CommercialTimes) list.get(i);
			Long startTime = time.getStartTime().getTime();
			Long endTime = time.getEndTime().getTime();
			totalTime = totalTime + (endTime - startTime);
		}
		Long hours = (totalTime / 3600000L);
		return hours.intValue();
	}

	public Collection getBehindTheWheelList() throws Exception {
		List list = new ArrayList();
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("classroomPk", getClassroomPk());
		hm.put("studentPk", getStudentPk());
		list = getStudentService().getCommercialBehindTheWheel(hm);
		return list;
	}

	public Integer getBehindTheWheelTime() throws Exception {
		List list = (List) getBehindTheWheelList();
		Long totalTime = 1L; // Set greater than 0
		for (int i = 0; i < list.size(); i++) {
			CommercialTimes time = (CommercialTimes) list.get(i);
			Long startTime = time.getStartTime().getTime();
			Long endTime = time.getEndTime().getTime();
			totalTime = totalTime + (endTime - startTime);
		}
		Long hours = (totalTime / 3600000L);
		return hours.intValue();
	}

	public Collection getObservationList() throws Exception {
		List list = new ArrayList();
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("classroomPk", getClassroomPk());
		hm.put("studentPk", getStudentPk());
		list = getStudentService().getCommercialObservation(hm);
		return list;
	}

	public Integer getObservationTime() throws Exception {
		List list = (List) getObservationList();
		Long totalTime = 1L; // Set greater than 0
		for (int i = 0; i < list.size(); i++) {
			CommercialTimes time = (CommercialTimes) list.get(i);
			Long startTime = time.getStartTime().getTime();
			Long endTime = time.getEndTime().getTime();
			totalTime = totalTime + (endTime - startTime);
		}
		Long hours = (totalTime / 3600000L);
		return hours.intValue();
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
		// log.debug("observationDate truncated "+
		// truncateDate(getObservationDate()));
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
			hm.put("observation_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
			hm.put("observation_datestamp", new Date());
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
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String viewUpdateBTWTime() throws Exception {
		log.debug("UpdateBTWTime");

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("timePk", getTimePk());
		List resultList = getStudentService().getCommercialBehindTheWheel(hm);
		setCurrentCommercialTimes((CommercialTimes) resultList.get(0));
		setBehindTheWheelDate(getCurrentCommercialTimes().getStartTime());
		setBehindTheWheelStartTime(getCurrentCommercialTimes()
				.getBehindTheWheelStartTime());
		setBehindTheWheelEndTime(getCurrentCommercialTimes()
				.getBehindTheWheelEndTime());
		setInstructorFk(getCurrentCommercialTimes().getInstructorFk());
		setVehicleFk(getCurrentCommercialTimes().getVehicleFk());
		setBranchFk(getCurrentCommercialTimes().getBranchFk());

		return INPUT;
	}

	public String updateBTWTime() throws Exception {
		log.debug("updateBtwTime");
		// log.debug("observationDate truncated "+
		// truncateDate(getObservationDate()));
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
			hm.put("branchFk", branchFk);
			hm.put("studentFk", studentPk);
			hm.put("timePk", timePk);
			hm.put("btw_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
			hm.put("btw_datestamp", new Date());
			getStudentService().updateBehindTheWheel(hm);
		}

		if (hasErrors()) {
			return INPUT;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<br/>Behind The Wheel Times Updated For Student ");
			Collection<Object> col = new ArrayList<Object>();
			col.add(sb);
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String deleteBTWTime() throws Exception {
		log.debug("deleteBTWTime");
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("timePk", timePk);
		getStudentService().deleteBehindTheWheel(hm);
		StringBuffer sb = new StringBuffer();
		sb.append("<br/>Behind The Wheel Time Deleted For Student");
		Collection<Object> col = new ArrayList<Object>();
		col.add(sb);
		setCommercialAjaxMessages(col);
		return SUCCESS;
	}

	// missing method. Copied from production version 3/2/2009 Hung
	public String viewUpdateObservationTime()
    throws Exception
{
    log.debug("viewUpdateObservationTime");
    Map hm = new HashMap();
    hm.put("timePk", getTimePk());
    List resultList = getStudentService().getCommercialObservation(hm);
    setCurrentCommercialTimes((CommercialTimes)resultList.get(0));
    setObservationDate(getCurrentCommercialTimes().getStartTime());
    setObservationStartTime(getCurrentCommercialTimes().getObservationStartTime());
    setObservationEndTime(getCurrentCommercialTimes().getObservationEndTime());
    setInstructorFk(getCurrentCommercialTimes().getInstructorFk());
    setVehicleFk(getCurrentCommercialTimes().getVehicleFk());
    setBranchFk(getCurrentCommercialTimes().getBranchFk());
    return "input";
}
	
	// duplicate method???, see updateObservationTime().
	public String UpdateObservationTime() throws Exception {
		log.debug("UpdateObservationTime");

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("timePk", getTimePk());
		List resultList = getStudentService().getCommercialObservation(hm);
		setCurrentCommercialTimes((CommercialTimes) resultList.get(0));
		setObservationDate(getCurrentCommercialTimes().getStartTime());
		setObservationStartTime(getCurrentCommercialTimes()
				.getObservationStartTime());
		setObservationEndTime(getCurrentCommercialTimes()
				.getObservationEndTime());
		setInstructorFk(getCurrentCommercialTimes().getInstructorFk());
		setVehicleFk(getCurrentCommercialTimes().getVehicleFk());
		setBranchFk(getCurrentCommercialTimes().getBranchFk());

		return INPUT;
	}

	public String updateObservationTime() throws Exception {
		log.debug("updateObservationTime");
		log.debug("updateObservationTime StartTime " + observationStartTime);
		int start = decodeTime(observationStartTime);
		log.debug("updateObservationTime StartTime decoded");
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
			hm.put("studentFk", studentPk);
			hm.put("timePk", timePk);
			hm.put("observation_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
			hm.put("observation_datestamp", new Date());
			getStudentService().updateObservation(hm);
		}

		if (hasErrors()) {
			return INPUT;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<br/>Observation Times Updated For Student");
			Collection<Object> col = new ArrayList<Object>();
			col.add(sb);
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String deleteObservationTime() throws Exception {
		log.debug("deleteObservationTime");
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("timePk", timePk);
		getStudentService().deleteObservation(hm);
		StringBuffer sb = new StringBuffer();
		sb.append("<br/>Observation Time Deleted For Student");
		Collection<Object> col = new ArrayList<Object>();
		col.add(sb);
		setCommercialAjaxMessages(col);
		return SUCCESS;
	}

	public String viewUpdateTrainingTime() throws Exception {
		log.debug("viewUpdateTrainingTime");

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("timePk", getTimePk());
		List resultList = getStudentService().getCommercialTraining(hm);
		setCurrentCommercialTimes((CommercialTimes) resultList.get(0));
		setTrainingDate(getCurrentCommercialTimes().getStartTime());
		setTrainingStartTime(getCurrentCommercialTimes().getTrainingStartTime());
		setTrainingEndTime(getCurrentCommercialTimes().getTrainingEndTime());
		setInstructorFk(getCurrentCommercialTimes().getInstructorFk());
		setSection(getCurrentCommercialTimes().getSection());
		setBranchFk(getCurrentCommercialTimes().getBranchFk());
		setClassroomPk(getCurrentCommercialTimes().getClassroomFk());

		return INPUT;
	}

	public String updateTrainingTime() throws Exception {
		log.debug("insertTrainingTime");
		// log.debug("observationDate truncated "+
		// truncateDate(getObservationDate()));
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
			hm.put("studentFk", studentPk);
			hm.put("timePk", timePk);
			hm.put("training_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
			hm.put("training_datestamp", new Date());
			getStudentService().updateTraining(hm);
		}

		if (hasErrors()) {
			return INPUT;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<br/>Training Times Updated For Student");
			Collection<Object> col = new ArrayList<Object>();
			col.add(sb);
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String deleteTrainingTime() throws Exception {
		log.debug("deleteTrainingTime");
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("timePk", timePk);
		getStudentService().deleteTraining(hm);
		StringBuffer sb = new StringBuffer();
		sb.append("<br/>Training Time Deleted For Student");
		Collection<Object> col = new ArrayList<Object>();
		col.add(sb);
		setCommercialAjaxMessages(col);
		return SUCCESS;
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
	
	public String addMultiTrainingTime() throws Exception {
		int start = decodeTime(trainingStartTime);
		Date startDate = new Date(getTrainingDate().getTime() + start);

		int end = decodeTime(trainingEndTime);
		Date endDate = new Date(getTrainingDate().getTime() + end);

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
			
			int result = 0;
			for (int i = 0; i < studentArray.size(); i++) {
				hm.put("studentFk", studentArray.get(i));
				if (getStudentService().insertTraining(hm) == 1) {
					result++;
				}
			}
			
			resultMsg = "These " + result + " students of " + getStudentList().size() + " available have been added to this training class.";
		}
		
		if (hasErrors()) {
			return INPUT;
		} else {
			return SUCCESS;
		}
	}
	
	public String addMultiObservationTime() throws Exception {
		int start = decodeTime(observationStartTime);
		Date startDate = new Date(getObservationDate().getTime() + start);

		int end = decodeTime(observationEndTime);
		Date endDate = new Date(getObservationDate().getTime() + end);

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
			
			int result = 0;
			for (int x = 0; x < studentArray.size(); x++) {
				hm.put("studentFk", studentArray.get(x));
				if (getStudentService().insertObservation(hm) == 1) {
					result++;
				}
			}
			
			resultMsg = "These " + result + " students of " + getStudentList().size() + " available have been added to this observation class.";
		}

		if (hasErrors()) {
			return INPUT;
		} else {
			return SUCCESS;
		}
	}
	
	public String insertTrainingTime() throws Exception {
		//log.debug("insertTrainingTime");
		// log.debug("observationDate truncated "+
		// truncateDate(getObservationDate()));
		int start = decodeTime(trainingStartTime);
		Date startDate = new Date(getTrainingDate().getTime() + start);
		//log.debug("startDate " + startDate.toString());

		int end = decodeTime(trainingEndTime);
		Date endDate = new Date(getTrainingDate().getTime() + end);
		//log.debug("endDate " + endDate.toString());

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
			hm.put("training_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
			hm.put("training_datestamp", new Date());
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
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String insertBtwTime() throws Exception {
		log.debug("insertBtwTime");
		// log.debug("observationDate truncated "+
		// truncateDate(getObservationDate()));
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
			hm.put("btw_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
			hm.put("btw_datestamp", new Date());
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
			setCommercialAjaxMessages(col);
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
		// hm.put("classroomFk", classroomPk);
		getStudentService().update(hm);
		return SUCCESS;
	}

	public CompletionByDLResponseType getCompletionByDl() throws Exception {
		/*String ccDate = sendStringDate(getTrainingCompletionDate());
		String ocDate = sendStringDate(getObservationCompletionDate());
		String btwDate = sendStringDate(getBtwCompletionDate());
		
		// old web service - 12/01/2012
		DriversLicenseValidation dlv = new DriversLicenseValidation();
		dlv.setTransactionParameters(dlv.getCompletionByDLTransactionParam(
				umdLogon, getFileNumber(), ccDate, ocDate, "", btwDate, "",
				schoolNumber));			
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

	public CompletionByNameResponseType getCompletionByName() throws Exception {
		
		/*Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
		String umdLogon = loggedInPerson.getEmail();

		String ccDate = sendStringDate(getTrainingCompletionDate());
		String ocDate = sendStringDate(getObservationCompletionDate());*/
		String schoolNumber = getSchoolNumber();
		
		// get student info
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("studentPk", getStudentPk());
		Student student = getStudentService().getStudent(hm);
		setCurrentStudent(student);

		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dob = sdf.format(student.getDob());

		// old web service - 12/01/2012
		DriversLicenseValidation dlv = new DriversLicenseValidation();
		dlv.setTransactionParameters(dlv.getCompletionByNameTransactionParam(
				umdLogon, student.getFirstName(), student.getMiddleName(), student.getLastName(), dob, 
				ccDate, ocDate, schoolNumber));
		Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_CompletionByName));			
		*/
		SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
		CompletionByNameRequestType wsRequest = new CompletionByNameRequestType();
		PersonType subject = new PersonType();
		subject.setGivenName(student.getFirstName());
		subject.setMiddleName(student.getMiddleName());
		subject.setSurName(student.getLastName());
		subject.setBirthDate(student.getDob());
		wsRequest.setSubject(subject);
		
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
		
		return wsService.completionByName(wsRequest);		
	}
	
	public Date parseCompletionByDlResults(Map results) throws Exception {
		Date date = null;
		int retVal = 0;
		String statusString = results.get("ResponseStatusCode").toString();
		// ResponseException available after 412 not 404 not 200
		int status = Integer.parseInt(statusString.trim());
		// 400 License Number not found in the system.
		log.debug("#################### status is " + status);
		if (status == 200) {
			if (results.get("EligibilityDate") != null) {
				date = blankDate(results.get("EligibilityDate").toString()
						.trim());
				if (date != null) {
					retVal = 1;
				} else {
					log.debug("#################### returning blank date");
					addActionError(Constants.Webservice_Error_EligibilityDate);
				}
			}
		} else if (status == 400) {
			addActionError(Constants.Webservice_Error_EligibilityDate);
			// hack... till dps fix their error message.
			String dpsError = (String) results.get("ResponseStatusDescription");
			if ("Wheel Date can not be set when lic type is LRN"
					.equals(dpsError.trim())) {
				addActionError("This driver cannot complete behind the wheel training or the driving skills test because they have not been issued a learner permit by the division");
			} else {
				addActionError(dpsError);
			}
			// end hack
		} else if (status == 404) {
			log.debug("status " + status);
			addActionError("Status " + status);
		} else if (status == 412) {
			// ResponseException available after 412 not 404 not 200
			// DL available but no permit.
			log.debug("status " + status);
			addActionError((String) results.get("ResponseException"));
		} else {
			addActionError((String) results.get("ResponseStatusDescription"));
		}
		return date;
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
			return Constants.REPORT; // return pdf
		} else {
			addActionError("Completion certificate not generated.");
		}

		return SUCCESS;
	}

	public String singleUpdateObservation() throws Exception {
		log.debug("singleUpdateObservation");
		List temp = new ArrayList();
		temp.add(getStudentPk());
		studentArray = (ArrayList) temp;
		return INPUT;
		// return insertObservationTime();
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

	public void setCommercialAjaxMessages(Collection cmj) {
		/*
		 * Object[] al = cmj.toArray(); for(int i=0;i<al.length;i++){
		 * log.debug("setCommercialAjaxMessages " + al[i]); }
		 */
		getSession().put(Constants.Commercial_Ajax_Message, cmj);
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
		return getTrainingTime();
	}

	public void setTrainingHours(Integer trainingHours) {
		this.trainingHours = trainingHours;
	}

	public Integer getBehindTheWheelHours() throws Exception {
		return getBehindTheWheelTime();
	}

	public void setBehindTheWheelHours(Integer behindTheWheelHours) {
		this.behindTheWheelHours = behindTheWheelHours;
	}

	public Integer getObservationHours() throws Exception {
		return getObservationTime();
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

	public Integer getTimePk() {
		return timePk;
	}

	public void setTimePk(Integer timePk) {
		this.timePk = timePk;
	}

	public CommercialTimes getCurrentCommercialTimes() {
		return currentCommercialTimes;
	}

	public void setCurrentCommercialTimes(CommercialTimes currentCommercialTimes) {
		this.currentCommercialTimes = currentCommercialTimes;
	}

	public Integer getOcsScore() {
		return ocsScore;
	}

	public void setOcsScore(Integer ocsScore) {
		this.ocsScore = ocsScore;
	}
	
	public String updateStudentOcsScore() throws Exception {

		log.debug("updateStudentOcsScore");
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("studentPk", getStudentPk());
		hm.put("ocsScore", getOcsScore());
		hm.put("ocs_score_userid", ((Person) getSession().get(Constants.USER_KEY)).getEmail());
		hm.put("ocs_score_datestamp", new Date());
		getStudentService().updateStudentOcsScore(hm);

		if (hasErrors()) {
			return INPUT;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<br/>Home Study Score updated for 1 student");
			Collection<Object> col = new ArrayList<Object>();
			col.add(sb);
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String updateStudentNote() throws Exception {

		log.debug("updateNote");
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("studentPk", getStudentPk());
		hm.put("note", getNote());
		getStudentService().updateStudentNote(hm);

		if (hasErrors()) {
			return INPUT;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("<br/>Student Note updated for 1 student");
			Collection<Object> col = new ArrayList<Object>();
			col.add(sb);
			setCommercialAjaxMessages(col);
			return SUCCESS;
		}
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public Classroom getCurrentClassroom() throws Exception {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("classroomPk", getClassroomPk());
        return getClassroomService().getClassroom(hm);
	}
	
	public List<Student> getStudentList() throws Exception {
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("classroomFk", classroomPk);
        return getStudentService().getCommercialStudentList(hm);
	}
	
	public List<Student> getStudentInfoFromStudentArray() throws Exception { // get student info for student array.
        List<Student> tmpList = getStudentList();
        List<Student> studentInfoFromStudentArray = new ArrayList<Student>();
        for (Object o : studentArray) {
        	for (Student s : tmpList) {
        		if (Integer.parseInt((String)o) == s.getStudentPk().intValue()) {
        			studentInfoFromStudentArray.add(s);
        		}
        	}
        }
        
        return studentInfoFromStudentArray;
	}

}

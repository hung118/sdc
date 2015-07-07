package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dps.dld.webservice.sdct.SdctWsService;
import gov.utah.dps.dld.webservice.sdct.SdctWsServiceImpl;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLRequestType;
import gov.utah.dps.dld.webservice.sdct.gen.QueryByDLResponseType;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.model.Student;
import gov.utah.dts.sdc.webservice.DriversLicenseValidation;
import gov.utah.dts.util.SendMail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class CommercialStudentSearchAction extends BaseCommercialStudentAction implements Preparable {

	private static final long serialVersionUID = 1L;

	protected static Log log = LogFactory.getLog(CommercialStudentSearchAction.class);
	
	private List mergeWebStudent;
	private List mergeDbStudent;
	private List multipleClassroomList;
	private List studentListSearchResult;
	

	public String editStudent() throws Exception {
		log.debug("editStudent");
		if (getCurrentStudent().getFileNumber() != null) {
			log.info("FILE NUMBER PRESENT, UPDATING STUDENT RECORD WITH WEBSERVICE");
			webserviceUpdate();
		}
		// set ClassroomInfo after possible webservice update so most current
		// information is set.
		setClassroomInfo();

		return SUCCESS;
	}

	public String inputStudentNumber() throws Exception {
		log.debug("inputStudentNumber");
		return INPUT;
	}

	public String inputFileNumber() throws Exception {
		log.debug("inputFileNumber");
		return INPUT;
	}

	public String inputDOB() throws Exception {
		log.debug("inputDOB");
		return INPUT;
	}

	public void setClassroomInfo() throws Exception {
		log.debug("setClassroomInfo");
		if (getCurrentStudent() != null) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put("studentFk", getCurrentStudent().getStudentPk());
			hm.put("classroomPk", getClassroomPk());
			Classroom school = getClassroomService().getSchoolInfo(hm);
			setSchoolName(school.getSchoolName());
			setHomeStudy(school.getHomeStudy());
			setSchoolPk(school.getSchoolFk());

			// setCompletionDate();
			setObservationCompletionDate(getCurrentStudent().getObservationCompletionDate());
			setBtwCompletionDate(getCurrentStudent().getBehindWheelCompletionDate());
			setTrainingCompletionDate(getCurrentStudent().getClassroomCompletionDate());
		} else {
			log.error("################# setClassroomInfo ERROR");
		}
	}

	public Student fetch(Integer tryId) throws DaoException {
		log.debug("enter fetch for " + tryId);
		Student result = null;
		if (tryId != null) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put("studentPk", tryId);
			result = getStudentService().getStudent(hm);
		}
		log.debug("exit fetch for " + tryId);
		return result;
	}

	public String save() throws Exception {
		log.debug("save");
		return SUCCESS;
	}

	public String mergeFileNumber() throws Exception {
		log.debug("******* mergeFileNumber " + getFileNumber() + " " + getStudentNumber());
		QueryByDLResponseType wsResponse = getQueryByDl();
		if (wsResponse != null) {
			if ("200".equals(wsResponse.getStatus())) {
				Student webStudent = parseQueryByDlResults(wsResponse, true);
				int i = getStudentService().fullUpdate(webStudent);
				setCurrentStudent(webStudent);
				setStudentPk(webStudent.getStudentPk());
			}
		}
		return SUCCESS;
	}

	public String syncFileNumber() throws Exception {
		log.debug("******* syncFileNumber " + getFileNumber());
		boolean studentMerged = false;
		boolean studentFound = false;

		QueryByDLResponseType wsResponse = getQueryByDl();
		if (wsResponse != null) {
			Student webStudent = parseQueryByDlResults(wsResponse);
			if ("200".equals(wsResponse.getStatus())) {
				if (webStudent.getStudentPk() == null) {
					log.debug("Student Insert");
					Student temp = new Student();
					if (getStudentNumber() != null) {
						temp.setStudentNumber(getStudentNumber());
						Student dbStudent = getStudentService().getStudent(temp);
						if (dbStudent.getDob().equals(webStudent.getDob())) {
							studentFound = true;
							if (dbStudent.getLastName().equals(webStudent.getLastName())) {
								if (dbStudent.getFirstName().equals(webStudent.getFirstName())) {
									studentMerged = true;
									// merge appropriate values
									webStudent.setStudentNumber(dbStudent.getStudentNumber());
									webStudent.setStudentPk(dbStudent.getStudentPk());
									webStudent.setSchoolFk(getSchoolPk());
									// fullUpdate sends nulls
									int i = getStudentService().fullUpdate(webStudent);
									setCurrentStudent(webStudent);
									setStudentPk(webStudent.getStudentPk());
								}
							}
							if (!studentMerged) {
								List<String> web = new ArrayList<String>();
								List<String> db = new ArrayList<String>();

								SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

								web.add(sdf.format(webStudent.getDob()));
								web.add(webStudent.getLastName());
								web.add(webStudent.getFirstName());
								setMergeWebStudent(web);

								db.add(sdf.format(dbStudent.getDob()));
								db.add(dbStudent.getLastName());
								db.add(dbStudent.getFirstName());
								setMergeDbStudent(db);
							}
						} else {
							// dob doesnt match.
							addActionError("File Number cannot sync.  Student dob does not match.");
						}
					}
				} else {
					// filenumber already in database.
					String note = null;
					if (webStudent.getStudentNumber() != null) {
						note = webStudent.getStudentNumber().toString();
					} else {
						note = webStudent.getStudentFullName();
					}
					addActionError("File Number already associated with a student "	+ note + ", please contact SDC administration for assistance.");
				}
			}
		} else {
			addActionError("Web service response was empty.");
			log.debug("Webservice RESULTS EMPTY");
		}

		if (studentMerged) {
			log.debug("SUCCESS");
			return SUCCESS;
		} else if (studentFound) {
			log.debug("MERGE");
			return Constants.MERGE;
		} else {
			log.debug("INPUT");
			Collection list = getActionErrors();
			setCommercialAjaxMessages(list);
			sendSyncErrorEmail((ArrayList) list, getStudentNumber(), getFileNumber());
			return INPUT;
		}
	}

	// This is a clone of the regular syncFileNumber that allows entry of
	// duplicate student records with the same fileNumber
	public String commercialSyncFileNumber() throws Exception {
		log.debug("******* commercialSyncFileNumber " + getFileNumber());
		boolean noErrors = true;
		boolean studentMerged = false;
		boolean studentExists = false;
		boolean studentFound = false;
		int status = 0;
    	Collection<String> col = new ArrayList<String>();

    	QueryByDLResponseType wsResponse = getQueryByDl();
		if (wsResponse != null) {
            Student webStudent = parseQueryByDlResults(wsResponse);
			if ("200".equals(wsResponse.getStatus())) {
				studentExists = true;
			
				//This handles duplicate file numbers, By removing file number from existing record so that it can be added
				if (webStudent.getFileNumber() != null) {
					log.debug("****** Existing FileNumber... preparing to remove");
					Student a = new Student();
					a.setFileNumber(webStudent.getFileNumber());
					Student temp = getDbStudent(a);
					//Only remove if it is the same student otherwise this could be used to remove file numbers from any existing record.
					if (temp != null) {
						log.debug("dbstudent " +temp.getDob() +"  webstudent "+ webStudent.getDob());
						if (temp.getDob().equals(webStudent.getDob())) {
							log.debug("dbstudent lastname " +temp.getLastName() +"  webstudent "+ webStudent.getLastName());
							if (temp.getLastName().equalsIgnoreCase(webStudent.getLastName())) {
								temp.setFileNumber(null);
								int i = getStudentService().studentUpdateFileNumber(temp);
								if (i > 0) {
									log.debug("****** FileNumber removed from: " + temp.getStudentNumber());
								} else {
									log.debug("****** FileNumber WAS NOT removed from: " + temp.getStudentNumber());
								}
							} else {
								col.add("Existing student using this filenumber: Last names do not match with DLD record.");
								noErrors = false;
							}
						} else {
							col.add("Existing student using this filenumber: DOB does not match with DLD record.");
							noErrors = false;
						}
					}
				}

				if (noErrors) {
					log.debug("Student Insert");
					if (getStudentNumber() != null) {
						Student a = new Student();
						a.setStudentNumber(getStudentNumber());
						// getDbStudent returns the sdc student with a matching dob.
						Student dbStudent = getDbStudent(a, webStudent.getDob());
                    
						if (dbStudent != null) {
							log.debug("dbstudent " +dbStudent.getDob() +"  webstudent "+ webStudent.getDob());
							studentFound = true;
							log.debug("db lastname " +dbStudent.getLastName() +"  webstudent "+ webStudent.getLastName());
							if (dbStudent.getLastName().equals(webStudent.getLastName())) {
								log.debug("db firstname " +dbStudent.getFirstName() +"  webstudent "+ webStudent.getFirstName());
								if (dbStudent.getFirstName().equals(webStudent.getFirstName())) {
									studentMerged = true;
									// merge appropriate values
									webStudent.setStudentNumber(dbStudent.getStudentNumber());
									webStudent.setStudentPk(dbStudent.getStudentPk());
									webStudent.setSchoolFk(getSchoolPk());
									// fullUpdate sends nulls
									int i = getStudentService().fullUpdate(webStudent);
									setCurrentStudent(webStudent);
									setStudentPk(webStudent.getStudentPk());
								}else{
									col.add("First names do not match");
									noErrors = false;
								}
							}else{
								col.add("Last names do not match");
								noErrors = false;
							}
							if (!studentMerged) {
								List<String> web = new ArrayList<String>();
								List<String> db = new ArrayList<String>();

								SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

								web.add(sdf.format(webStudent.getDob()));
								web.add(webStudent.getLastName());
								web.add(webStudent.getFirstName());
								setMergeWebStudent(web);

								db.add(sdf.format(dbStudent.getDob()));
								db.add(dbStudent.getLastName());
								db.add(dbStudent.getFirstName());
								setMergeDbStudent(db);
							}
						} else {
							// dob doesnt match.
							col.add("DOB does not match");
							noErrors = false;
						}
					}
				}
			} else {
				col.add("Student Not Found in DLD(Webservice)");
				noErrors = false;
			}
			if (!noErrors) {
				col.add("The File Number Entered Is Not Valid For This Student");
			}
		} else {
			log.debug("Webservice RESULTS EMPTY");
			noErrors = false;
			col.add("Web service response was empty.");
		}

		if (noErrors) {
			if (studentMerged) {
				return SUCCESS;
			} else if (studentFound) {
				return Constants.MERGE;
			} else {
				col = getActionErrors();
				setCommercialAjaxMessages(col);
				//sendSyncErrorEmail((ArrayList) col, getStudentNumber(), getFileNumber());
				return INPUT;
			}
		} else {
			if (col.isEmpty()) {
				col = getActionErrors();
			}
			setCommercialAjaxMessages(col);
			return INPUT;
		}
	}

	public String webSearch() throws Exception {
		log.debug("******* commercial webSearch " + getFileNumber());
		boolean studentExists = false;
		int status = 0;
		boolean noErrors = true;
		Collection<String> col = new ArrayList<String>();
		QueryByDLResponseType wsResponse = getQueryByDl();
		
		if (wsResponse != null) {
			Student student = parseQueryByDlResults(wsResponse);
			if ("200".equals(wsResponse.getStatus())) {
				studentExists = true;

				if (student.getStudentPk() == null) {
					log.debug("Student Insert");
					student.setStudentNumber(createStudentNumber(getClassroomPk()));
					int i = getStudentService().insert(student);
					student.setStudentPk(getSdcService().getLastInsertedId());
					// AUDIT
					insertAuditDates(student);
					addRoster(student.getStudentPk(), getClassroomPk());
				} else {
					// check if already on the roster
					boolean roster = checkRoster(student.getStudentPk(), getClassroomPk());
					if (!roster) {
						addRoster(student.getStudentPk(), getClassroomPk());
						if (student.getStudentNumber() == null) {
							log.debug("Student Number Insert");
							student.setStudentNumber(createStudentNumber(getClassroomPk()));
						}
					}
					// fullUpdate sends nulls
					int i = getStudentService().fullUpdate(student);
					// AUDIT
					checkAuditDates(student);
				}
				setCurrentStudent(student);
				setStudentPk(student.getStudentPk());
			}else{
				noErrors = false;
				if (status == 404) {
					col.add("File Number Not Found");
				}
			}
		} else {
			noErrors = false;
			col.add("Web service response was empty.");
			log.debug("Webservice RESULTS EMPTY");
		}

		if (noErrors) {
			if (studentExists) {
				return SUCCESS;
			}else{
				col = getActionErrors();
				setCommercialAjaxMessages(col);
				return INPUT;
			}
		} else {
			if (col.isEmpty()) {
				col = getActionErrors();
			}
			setCommercialAjaxMessages(col);
			return INPUT;
		}
	}

	public String fileNumberSearch() throws Exception {
		log.debug("******* commercial fileNumberSearch " + getFileNumber());
		boolean studentExists = false;
		boolean studentExistsInDl = false;
		boolean noErrors = true;
		
		Map hm = new HashMap();
		hm.put("fileNumber", getFileNumber());
		if (getSchoolPk() != null) {
			hm.put("schoolFk", getSchoolPk());
			List classroomList = getClassroomService().getClassroomList(hm);
			if (!classroomList.isEmpty()) {
				log.debug("...classroomlist size = " + classroomList.size());
				for (int i = 0; i < classroomList.size(); i++) {
					Classroom c = (Classroom) classroomList.get(i);
					hm.put("classroomFk", c.getClassroomPk());
					List studentList = getStudentService().getCommercialStudentList(hm);
					if (!studentList.isEmpty()) {
						Student s = (Student) studentList.get(0);
						studentExists = true;
						break;
					}
				}
			}
		}

		if (studentExists) {
			QueryByDLResponseType wsResponse = getQueryByDl();

			if (wsResponse != null) {
				Student student = parseQueryByDlResults(wsResponse);
				if ("200".equals(wsResponse.getStatus())) {
					studentExistsInDl = true;
					setCurrentStudent(student);
					setStudentPk(student.getStudentPk());
				}
			} else {
				noErrors = false;
				addActionError("Web service response was empty.");
				log.debug("Webservice RESULTS EMPTY");
			}
		}

		if (noErrors) {
			if (studentExistsInDl) {
				hm.put("studentPk", getStudentPk());
				List classroomList = getClassroomService().getSchoolInfoByStudent(hm);
				if (!classroomList.isEmpty()) {
					Classroom c = (Classroom) classroomList.get(0);
					setClassroomPk(c.getClassroomPk());
				}
				return SUCCESS;
			} else {
				addActionError("Student Not Found");
				setCommercialAjaxMessages(getActionErrors());
				return INPUT;
			}
		}else{
			Collection errList = getActionErrors();
			setCommercialAjaxMessages(errList);
			return INPUT;
		}
	}

	public String studentNumberSearch() throws Exception {
		log.debug("...Commercial studentNumberSearch");
		boolean studentExists = false;
		boolean studentClassesExist = false;
		boolean noClassroom = true;
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("studentNumber", getStudentNumber());

		List list = getStudentService().getStudentList(hm);
		if (!list.isEmpty()) {
			studentExists = true;
			setCurrentStudent((Student) list.get(0));
			setStudentPk(getCurrentStudent().getStudentPk());

			if (studentExists) {
				hm.put("studentPk", getStudentPk());
				List classroomList = getClassroomService().getSchoolInfoByStudent(hm);
				if (!classroomList.isEmpty()) {
					if(classroomList.size() > 1) {
						//means student is in multiple classrooms
						studentClassesExist = true;
					}
					noClassroom = false;
					Classroom c = (Classroom) classroomList.get(0);
					setClassroomPk(c.getClassroomPk());
					ArrayList<Object> multipleList = new ArrayList<Object>();
					for (int i = 0; i < classroomList.size(); i++) {
						Classroom temp = (Classroom) classroomList.get(i);
						multipleList.add(temp.getClassroomPk());
					}
					setMultipleClassroomList(multipleList);
				}
				
				if (noClassroom && !studentClassesExist) {
					// this would happen if a student was removed from a classroom;
					// 1.9.0 fix added here.  Ok to add student to classroom.
					log.debug("Student exists but is not associated with a classroom");
					if (getClassroomPk() != null && getClassroomPk().intValue() != 0) {
						hm.clear();
						hm.put("studentFk", getStudentPk());
						//log.debug("."+getClassroomPk()+".");
						hm.put("classroomFk", getClassroomPk());
						int retval = getClassroomService().addStudent(hm);
						if (retval > 0) {
							noClassroom = false;
						}
					}
				}
			}
		}

		if (studentClassesExist) {
			// Should be sent to a separate page which displays all classrooms
			// the student has been assigned to.
			return Constants.Forward_MultipleClasses;
		}else if (noClassroom && studentExists) {
			// should be sent to a separate page which finds all the
			// records regardless of classroom.
			return Constants.Forward_NoClassStudent;
		}else if (studentExists) {
			return SUCCESS;
		} else {
			addActionError("Student Not Found");
			Collection errList = getActionErrors();
			setCommercialAjaxMessages(errList);
			return "input";
		}
	}

	public String dobSearch() throws Exception {
		log.debug("...Commercial dobSearch");
		boolean studentExists = false;
		HashMap hm = new HashMap();
		
		// Redmine 9505 commercial
		hm.put("firstName", "%" + getCurrentStudent().getFirstName() + "%");
		hm.put("lastName", "%" + getCurrentStudent().getLastName() + "%");
		
		hm.put("dob", getCurrentStudent().getDob());

		if (getCurrentStudent().getSchoolFk() != null) {
			hm.put("schoolFk", getCurrentStudent().getSchoolFk());
			List classroomList = getClassroomService().getClassroomList(hm);
			if (!classroomList.isEmpty()) {
				log.debug("...classroomlist size = " + classroomList.size());
				for (int i = 0; i < classroomList.size(); i++) {
					Classroom c = (Classroom) classroomList.get(i);
					hm.put("classroomFk", c.getClassroomPk());
					List studentList = getStudentService().getCommercialStudentList(hm);
					if (!studentList.isEmpty()) {
						Student s = (Student) studentList.get(0);
						studentExists = true;
						setCurrentStudent(s);
						setStudentPk(s.getStudentPk());
						break;
					}
				}
			}
		} else {
			List list = getStudentService().getStudentList(hm);
			log.debug("...list size = " + list.size());

			if (!list.isEmpty()) {
				studentExists = true;
				
				if (list.size() == 1) {
					setCurrentStudent((Student) list.get(0));
					setStudentPk(getCurrentStudent().getStudentPk());					
				} else {	// multiple result - redmine 9505 commercial
					// set school name and classroomFk in list
					setSchoolClassroom(list);
					
					setStudentListSearchResult(list);
					return "multipleResult";
				}
			}
		}

		if (studentExists) {
			hm.put("studentPk", getStudentPk());
			List classroomList = getClassroomService().getSchoolInfoByStudent(hm);
			if (!classroomList.isEmpty()) {
				Classroom c = (Classroom) classroomList.get(0);
				setClassroomPk(c.getClassroomPk());
			}
			return SUCCESS;
		} else {
			addActionError("Student Not Found");
			Collection errList = getActionErrors();
			setCommercialAjaxMessages(errList);
			return "input";
		}
	}

	public void prepare() throws Exception {
		if (getCurrentStudent() != null) {
			log.debug("not null");
		} else {
			Student preFetched = fetch(getStudentPk());
			if (preFetched != null) {
				setCurrentStudent(preFetched);
			}
		}		
	}

	private QueryByDLResponseType getQueryByDl() throws Exception {
		/*Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
		String umdLogon = loggedInPerson.getEmail();
		
		//old web service - 12/01/2012
		DriversLicenseValidation dlv = new DriversLicenseValidation();
		dlv.setTransactionParameters(dlv.getQueryByDLTransactionParam(umdLogon, getFileNumber()));
		Map results = dlv.performSearch(dlv.getDefaultMap(Constants.Webservice_QueryByDL));
		*/
		
        SdctWsService wsService = new SdctWsServiceImpl(Constants.Webservice_EndPoint);
        QueryByDLRequestType wsRequest = new QueryByDLRequestType();
        wsRequest.setLicenseNumber(getFileNumber());
        QueryByDLResponseType wsResponse = wsService.queryByDL(wsRequest);
		
		return wsResponse;
	}

    private Student parseQueryByDlResults(QueryByDLResponseType wsResponse) throws Exception {
		return parseQueryByDlResults(wsResponse, false);
	}

	private Student parseQueryByDlResults(QueryByDLResponseType wsResponse, boolean lookupByStudentNumber) throws Exception {
		log.debug("LOOKUP BY STUDENT NUMBER " + lookupByStudentNumber);
		boolean studentInsert = false;
		Student temp = new Student();
		if (lookupByStudentNumber) {
			temp.setStudentNumber(getStudentNumber());
		} else if (getFileNumber() != null && getFileNumber().length() > 1) { 
			temp.setFileNumber(getFileNumber());
	 	} else {
			temp.setStudentPk(getStudentPk());  // required for multiple student records
		}

		if ("200".equals(wsResponse.getStatus())) {
			try {
				log.debug("Status 200");
				Student student = getStudentService().getStudent(temp);

                if (student.getStudentPk() == null) {
					studentInsert = true;
				}

                student.setDob(wsResponse.getSubject().getBirthDate());
                student.setFirstName(wsResponse.getSubject().getGivenName());
                student.setMiddleName(wsResponse.getSubject().getMiddleName());
                student.setLastName(wsResponse.getSubject().getSurName());
                
                if (wsResponse.getStudentDriverCertificate() != null) {
                	if (wsResponse.getStudentDriverCertificate().getClassroomCompletionDate() != null) {
                		student.setClassroomCompletionDate(wsResponse.getStudentDriverCertificate().getClassroomCompletionDate());
                	}
                	if (wsResponse.getStudentDriverCertificate().getClassroomSchoolId() != null) {
                		student.setClassroomCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getClassroomSchoolId()));
                	}

                	if (wsResponse.getStudentDriverCertificate().getObservationCompletionDate() != null) {
                		student.setObservationCompletionDate(wsResponse.getStudentDriverCertificate().getObservationCompletionDate());
                	}
                	if (wsResponse.getStudentDriverCertificate().getObservationSchoolId() != null) {
                		student.setObservationCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getObservationSchoolId()));
                	}
                	
                	if (wsResponse.getStudentDriverCertificate().getWheelCompletionDate() != null) {
                		student.setBehindWheelCompletionDate(wsResponse.getStudentDriverCertificate().getWheelCompletionDate());
                	}
                	if (wsResponse.getStudentDriverCertificate().getWheelSchoolId() != null) {
                		student.setBtwCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getWheelSchoolId()));
                	}
                	
                	if (wsResponse.getStudentDriverCertificate().getWrittenExamCompletionDateTime() != null) {
                		student.setWrittenTestCompletionDate(wsResponse.getStudentDriverCertificate().getWrittenExamCompletionDateTime().getTime());
                	}
                	if (wsResponse.getStudentDriverCertificate().getWrittenExamSchoolId() != null) {
                		student.setWrittenCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getWrittenExamSchoolId().trim()));
                	}

                	if (wsResponse.getStudentDriverCertificate().getRoadTestCompletionDateTime() != null) {
                		student.setRoadTestCompletionDate(wsResponse.getStudentDriverCertificate().getRoadTestCompletionDateTime().getTime());
                	}
                	if (wsResponse.getStudentDriverCertificate().getRoadTestSchoolId() != null) {
                		student.setRoadCompletionSchoolNumber(blankInteger(wsResponse.getStudentDriverCertificate().getRoadTestSchoolId()));
                	}
                	
                	if (wsResponse.getStudentDriverCertificate().getEligibilityDate() != null) {
                		student.setEligibilityDate(wsResponse.getStudentDriverCertificate().getEligibilityDate());
                	}
                	
                	if (wsResponse.getDriverLicense().getLicenseCategoryCode() != null) {
                		student.setLicenseType(wsResponse.getDriverLicense().getLicenseCategoryCode().getValue());
                	}
                	
                    student.setFileNumber(getFileNumber());
                    Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
                    student.setUpdatedBy(loggedInPerson.getEmail());
                }
				
				log.debug("#################### returning student");
				return student;
			} catch (DaoException de) {
				log.error("DAO ", de);
				throw new Exception(de);
			}
		} else {
			addActionError("Status " + wsResponse.getStatus() + " " + wsResponse.getStatusDescription());
		}
		
		log.debug("#################### returning blank student");
		return null;
	}

	public List getMergeDbStudent() {
		return mergeDbStudent;
	}

	public void setMergeDbStudent(List mergeDbStudent) {
		this.mergeDbStudent = mergeDbStudent;
	}

	public List getMergeWebStudent() {
		return mergeWebStudent;
	}

	public void setMergeWebStudent(List mergeWebStudent) {
		this.mergeWebStudent = mergeWebStudent;
	}

	public List getMultipleClassroomList() {
		return multipleClassroomList;
	}

	public void setMultipleClassroomList(List multipleClassroomList) {
		this.multipleClassroomList = multipleClassroomList;
	}

	public void webserviceUpdate() throws Exception {
		log.debug(">>> START webserviceUpdate...");
		setFileNumber(getCurrentStudent().getFileNumber());
		QueryByDLResponseType wsResponse = getQueryByDl();
		Student student = parseQueryByDlResults(wsResponse);
		log.debug("");
		if (student != null) {
			// redmine 28996
			if (student.getStudentNumber() != null) {
				setStudentNumber(student.getStudentNumber());
			}
			
			// fullUpdate sends nulls
			student.setAlmLog("No");
			int i = getStudentService().fullUpdate(student);
			if (student.getStudentNumber() == null) {
				student.setStudentNumber(getStudentNumber());
			}
			
			// AUDIT
			checkAuditDates(student);
			setCurrentStudent(student);
			setStudentPk(student.getStudentPk());
		} else {
			log.debug("webserviceUpdate failed");
		}
	}

	public Collection getCommercialAjaxMessages() {
		log.debug("getCommercialAjaxMessages");
		return (Collection) getSession().get(Constants.Commercial_Ajax_Message);
	}
	
	public List getStudentListSearchResult() {
		return this.studentListSearchResult;
	}
	
	public void setStudentListSearchResult(List studentListSearchResult) {
		this.studentListSearchResult = studentListSearchResult;
	}

	private void sendSyncErrorEmail(ArrayList list, Long studentNumber,	String fileNumber) throws Exception {
		Person loggedInPerson = (Person) getSession().get(Constants.USER_KEY);
		String umdLogon = loggedInPerson.getEmail();
		StringBuffer msg = new StringBuffer();
		msg.append(System.getProperty("line.separator"));
		msg.append(umdLogon);
		msg.append(" has attempted to sync the following student in SDC.");
		msg.append(System.getProperty("line.separator"));
		msg.append(System.getProperty("line.separator"));
		msg.append("Student Number: " + studentNumber + " to");
		msg.append(System.getProperty("line.separator"));
		msg.append("File Number: " + fileNumber);
		msg.append(System.getProperty("line.separator"));
		if (!list.isEmpty()) {
			msg.append(System.getProperty("line.separator"));
			msg.append("Error Message(s):");
			msg.append(System.getProperty("line.separator"));
			msg.append(System.getProperty("line.separator"));
			for (int i = 0; i < list.size(); i++) {
				msg.append(list.get(i));
				msg.append(System.getProperty("line.separator"));
			}
		}
		msg.append(System.getProperty("line.separator"));
		msg.append("Possible error resolutions:");
		msg.append(System.getProperty("line.separator"));
		msg.append("If a number is already associated with a student one solution may be to remove the student from the group and re-add them as an \"Add Student By File Number\". ");
		msg.append("However, before doing this you should note the students recorded times, because they will have to be re-entered into the newly added students record.");
		sendEmail("SDC Commercial Sync Error", msg.toString());
	}

	public void sendEmail(String subj, String msg) throws Exception {
		SendMail s = new SendMail();
		s.setMailSubject(subj);
		log.debug("sendEmail message = " + msg);
		s.setMailMessage(msg);
		s.send();
	}

	private Student getDbStudent(Student st, Date webDob) throws Exception {
		
		Student retObj = null;
		List list = getStudentService().getStudentList(st);
		for (int i = 0;  i < list.size(); i++) {
			Student dbStudent = (Student)list.get(i);
            log.debug("webdob = " +webDob+ " dob = "+ dbStudent.getDob());
            if(webDob != null){
                if (webDob.equals(dbStudent.getDob())) {
                    retObj = dbStudent;
                    break;
                }
            }
		}
		
		return retObj;
	}

	private Student getDbStudent(Student st) throws Exception {

		Student retObj = null;
		List list = getStudentService().getStudentList(st);
		for (int i = 0;  i < list.size(); i++) {
			Student dbStudent = (Student)list.get(i);
			retObj = dbStudent;
		}

		return retObj;
	}
	
	private void setSchoolClassroom(List students) throws Exception {
		
		for (int i = 0; i < students.size(); i++) {
			Student student = (Student)students.get(i);
			HashMap hm = new HashMap();
			hm.put("studentPk", student.getStudentPk());
			List classroomList = getClassroomService().getSchoolInfoByStudent(hm);
			if (!classroomList.isEmpty()) {
				Classroom c = (Classroom) classroomList.get(0);
				student.setClassroomFk(c.getClassroomPk());

				hm.put("studentFk", getCurrentStudent().getStudentPk());
				hm.put("classroomPk", c.getClassroomPk());
				Classroom school = getClassroomService().getSchoolInfo(hm);
				student.setSchoolName(school.getSchoolName());
			}
		}
	}
}


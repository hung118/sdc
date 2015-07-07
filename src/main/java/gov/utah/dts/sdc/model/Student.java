package gov.utah.dts.sdc.model;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer studentPk;
    private Integer schoolFk;
    private Integer classroomFk;
    private String schoolName;
    private String schoolNumber;
    private String fileNumber; //licenseNumber
    private String licenseType;
    private Long studentNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String suffix;
    private String studentFullName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String homePhone;
    private String email;
    private Date dob;
    private Date eligibilityDate;
    private Date writtenTestCompletionDate;
    private Integer writtenTestScore;
    private Date roadTestCompletionDate;
    private Integer roadTestScore;
    private Integer roadTestInstructorFk;
    private Date classroomCompletionDate;
    private boolean classroomCompletionCheck;
    private Date behindWheelCompletionDate;
    private boolean behindWheelCompletionCheck;
    private Date observationCompletionDate;
    private boolean observationCompletionCheck;
    @SuppressWarnings("unused")
	private String classroomCompletionCheck2;
    @SuppressWarnings("unused")
	private String behindWheelCompletionCheck2;
    @SuppressWarnings("unused")
	private String observationCompletionCheck2;
    private Integer classroomCompletionSchoolNumber;
    private Integer btwCompletionSchoolNumber;
    private Integer observationCompletionSchoolNumber;
    private Integer writtenCompletionSchoolNumber;
    private Integer roadCompletionSchoolNumber;
    private String updatedBy;
    private Date timestamp;
    private Integer[] schools;
    private Integer ocsScore;
    private String note;
    private String note_userid;
    private String hide;
    
    private String classroomCompletionSchoolName;
    private String btwCompletionSchoolName;
    private String observationCompletionSchoolName;
    private String writtenCompletionSchoolName;
    private String roadCompletionSchoolName; 
    
    // for reports rm 30825
    private String classroomHoursHigh = "27 Hours";
    private String classroomHoursHighHome = "30 Hours";
    private String observationHoursHigh = "6 Hours";
    private String btwHoursHigh = "6 Hours";
    private String classroomHoursCom = "18 Hours";
    private String classroomHoursComHome = "30 Hours";
    private String observationHoursCom = "6 Hours";
    private String btwHoursCom = "6 Hours";
    private Integer homeStudy;
    private String ocs_score_userid;
    private Date ocs_score_datestamp;
    
    // for rm 30821
    private StudentAudit studentAudit;
    
    private String almLog;

    public Student() {
    }

    public Student(String fileNumber, String firstName, String lastName, Date writtenTestCompletionDate) {
        this.setFileNumber(fileNumber);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setWrittenTestCompletionDate(writtenTestCompletionDate);
    }


    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }

    public Integer getBtwCompletionSchoolNumber() {
        return btwCompletionSchoolNumber;
    }

    public void setBtwCompletionSchoolNumber(Integer btwCompletionSchoolNumber) {
        this.btwCompletionSchoolNumber = btwCompletionSchoolNumber;
    }

    public Integer getClassroomCompletionSchoolNumber() {
        return classroomCompletionSchoolNumber;
    }

    public void setClassroomCompletionSchoolNumber(Integer classroomCompletionSchoolNumber) {
        this.classroomCompletionSchoolNumber = classroomCompletionSchoolNumber;
    }

    public Integer getObservationCompletionSchoolNumber() {
        return observationCompletionSchoolNumber;
    }

    public void setObservationCompletionSchoolNumber(Integer observationCompletionSchoolNumber) {
        this.observationCompletionSchoolNumber = observationCompletionSchoolNumber;
    }

    public Integer getRoadCompletionSchoolNumber() {
        return roadCompletionSchoolNumber;
    }

    public void setRoadCompletionSchoolNumber(Integer roadCompletionSchoolNumber) {
        this.roadCompletionSchoolNumber = roadCompletionSchoolNumber;
    }

    public Integer getWrittenCompletionSchoolNumber() {
        return writtenCompletionSchoolNumber;
    }

    public void setWrittenCompletionSchoolNumber(Integer writtenCompletionSchoolNumber) {
        this.writtenCompletionSchoolNumber = writtenCompletionSchoolNumber;
    }

    
    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getObservationHoursHigh() {
		return observationHoursHigh;
	}

	public void setObservationHoursHigh(String observationHoursHigh) {
		this.observationHoursHigh = observationHoursHigh;
	}

	public String getObservationHoursCom() {
		return observationHoursCom;
	}

	public void setObservationHoursCom(String observationHoursCom) {
		this.observationHoursCom = observationHoursCom;
	}

	public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer[] getSchools() {
        return schools;
    }

    public void setSchools(Integer[] schools) {
        this.schools = schools;
    }

    public Date getWrittenTestCompletionDate() {
        return writtenTestCompletionDate;
    }

    public void setWrittenTestCompletionDate(Date writtenTestCompletionDate) {
        this.writtenTestCompletionDate = writtenTestCompletionDate;
    }

    public Date getRoadTestCompletionDate() {
        return roadTestCompletionDate;
    }

    public void setRoadTestCompletionDate(Date roadTestCompletionDate) {
        this.roadTestCompletionDate = roadTestCompletionDate;
    }

    public Integer getRoadTestScore() {
        return roadTestScore;
    }

    public void setRoadTestScore(Integer roadTestScore) {
        this.roadTestScore = roadTestScore;
    }

    public Integer getRoadTestInstructorFk() {
        return roadTestInstructorFk;
    }

    public void setRoadTestInstructorFk(Integer roadTestInstructorFk) {
        this.roadTestInstructorFk = roadTestInstructorFk;
    }

    public Date getClassroomCompletionDate() {
        return classroomCompletionDate;
    }

    public void setClassroomCompletionDate(Date classroomCompletionDate) {
        this.classroomCompletionDate = classroomCompletionDate;
    }

    public Date getObservationCompletionDate() {
        return observationCompletionDate;
    }

    public void setObservationCompletionDate(Date observationCompletionDate) {
        this.observationCompletionDate = observationCompletionDate;
    }

    public Date getBehindWheelCompletionDate() {
        return behindWheelCompletionDate;
    }

    public void setBehindWheelCompletionDate(Date behindWheelCompletionDate) {
        this.behindWheelCompletionDate = behindWheelCompletionDate;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public boolean isClassroomCompletionCheck() {
        return classroomCompletionCheck;
    }

    public void setClassroomCompletionCheck(boolean classroomCompletionCheck) {
        this.classroomCompletionCheck = classroomCompletionCheck;
    }

    public boolean isBehindWheelCompletionCheck() {
        return behindWheelCompletionCheck;
    }

    public void setBehindWheelCompletionCheck(boolean behindWheelCompletionCheck) {
        this.behindWheelCompletionCheck = behindWheelCompletionCheck;
    }

    public boolean isObservationCompletionCheck() {
        return observationCompletionCheck;
    }

    public void setObservationCompletionCheck(boolean observationCompletionCheck) {
        this.observationCompletionCheck = observationCompletionCheck;
    }

    public String getClassroomCompletionCheck2() {
        if (isClassroomCompletionCheck()) {
            return "1";
        } else {
            return "0";
        }
    }

    public String getBehindWheelCompletionCheck2() {
        if (isBehindWheelCompletionCheck()) {
            return "1";
        } else {
            return "0";
        }
    }

    public String getObservationCompletionCheck2() {
        if (isObservationCompletionCheck()) {
            return "1";
        } else {
            return "0";
        }
    }

    public String getStudentFullName() {
        if (studentFullName != null) {
            if (studentFullName.equals("")) {
                setStudentFullName(firstName, lastName, middleName, suffix);
            }
        } else {
            setStudentFullName(firstName, lastName, middleName, suffix);
        }
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public void setStudentFullName(String firstName, String lastName, String middleName, String suffix) {
        String name = firstName;
        if (middleName != null) {
        	name = name + " " + middleName + " " + lastName;
        } else {
        	name = name + " " + lastName;
        }
        if (suffix != null) {
            if (!suffix.equals("")) {
                name = new StringBuffer().append(name).append(", ").append(suffix).toString();
            }
        }
        this.studentFullName = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public Date getEligibilityDate() {
        return eligibilityDate;
    }

    public void setEligibilityDate(Date eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    public Integer getWrittenTestScore() {
        return writtenTestScore;
    }

    public void setWrittenTestScore(Integer writtenTestScore) {
        this.writtenTestScore = writtenTestScore;
    }

    public Integer getClassroomFk() {
        return classroomFk;
    }

    public void setClassroomFk(Integer classroomFk) {
        this.classroomFk = classroomFk;
    }

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }

	public Integer getOcsScore() {
		return ocsScore;
	}

	public void setOcsScore(Integer ocsScore) {
		this.ocsScore = ocsScore;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDisplayDob() {
		String ret = "";
		if (dob != null) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			ret = df.format(dob);
		}
		return ret;
	}
	
	public void setDisplayDob(String displayDob) {
		//
	}

	public String getNameDob() {
		String ret = "";
		if (getStudentFullName() != null) {
			ret += getStudentFullName();
		}
		ret += " ";
		if (getDisplayDob() != null) {
			ret += " ("+getDisplayDob()+")";
		}else{
			ret += " ( )";
		}
		return ret;
	}
	
	public void setNameDob(String s) {
		//
	}

	public String getHide() {
		return hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}

	public String getClassroomCompletionSchoolName() {
		return classroomCompletionSchoolName;
	}

	public void setClassroomCompletionSchoolName(
			String classroomCompletionSchoolName) {
		this.classroomCompletionSchoolName = classroomCompletionSchoolName;
	}

	public String getBtwCompletionSchoolName() {
		return btwCompletionSchoolName;
	}

	public void setBtwCompletionSchoolName(String btwCompletionSchoolName) {
		this.btwCompletionSchoolName = btwCompletionSchoolName;
	}

	public String getObservationCompletionSchoolName() {
		return observationCompletionSchoolName;
	}

	public void setObservationCompletionSchoolName(
			String observationCompletionSchoolName) {
		this.observationCompletionSchoolName = observationCompletionSchoolName;
	}

	public String getWrittenCompletionSchoolName() {
		return writtenCompletionSchoolName;
	}

	public void setWrittenCompletionSchoolName(
			String writtenCompletionSchoolName) {
		this.writtenCompletionSchoolName = writtenCompletionSchoolName;
	}

	public String getRoadCompletionSchoolName() {
		return roadCompletionSchoolName;
	}

	public void setRoadCompletionSchoolName(String roadCompletionSchoolName) {
		this.roadCompletionSchoolName = roadCompletionSchoolName;
	}

	public String getAlmLog() {
		return almLog;
	}

	public void setAlmLog(String almLog) {
		this.almLog = almLog;
	}

	public String getClassroomHoursHigh() {
		return classroomHoursHigh;
	}

	public void setClassroomHoursHigh(String classroomHoursHigh) {
		this.classroomHoursHigh = classroomHoursHigh;
	}

	public String getClassroomHoursHighHome() {
		return classroomHoursHighHome;
	}

	public void setClassroomHoursHighHome(String classroomHoursHighHome) {
		this.classroomHoursHighHome = classroomHoursHighHome;
	}

	public String getClassroomHoursCom() {
		return classroomHoursCom;
	}

	public void setClassroomHoursCom(String classroomHoursCom) {
		this.classroomHoursCom = classroomHoursCom;
	}

	public String getClassroomHoursComHome() {
		return classroomHoursComHome;
	}

	public void setClassroomHoursComHome(String classroomHoursComHome) {
		this.classroomHoursComHome = classroomHoursComHome;
	}

	public Integer getHomeStudy() {
		return homeStudy;
	}

	public void setHomeStudy(Integer homeStudy) {
		this.homeStudy = homeStudy;
	}

	public String getBtwHoursHigh() {
		return btwHoursHigh;
	}

	public void setBtwHoursHigh(String btwHoursHigh) {
		this.btwHoursHigh = btwHoursHigh;
	}

	public String getBtwHoursCom() {
		return btwHoursCom;
	}

	public void setBtwHoursCom(String btwHoursCom) {
		this.btwHoursCom = btwHoursCom;
	}

	public StudentAudit getStudentAudit() {
		return studentAudit;
	}

	public void setStudentAudit(StudentAudit studentAudit) {
		this.studentAudit = studentAudit;
	}

	public String getOcs_score_userid() {
		return ocs_score_userid;
	}

	public void setOcs_score_userid(String ocs_score_userid) {
		this.ocs_score_userid = ocs_score_userid;
	}

	public Date getOcs_score_datestamp() {
		return ocs_score_datestamp;
	}

	public void setOcs_score_datestamp(Date ocs_score_datestamp) {
		this.ocs_score_datestamp = ocs_score_datestamp;
	}

	public String getNote_userid() {
		return note_userid;
	}

	public void setNote_userid(String note_userid) {
		this.note_userid = note_userid;
	}
}

package gov.utah.dts.sdc.model;

import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")
public class CommercialTimes implements Serializable {

    private Integer timePk;
    private Integer studentFk;
    private Integer classroomFk;
    private Integer section;
    private Date startTime;
    private Date endTime;
    private String instructorFullName;
    private Integer instructorFk;
    private String vehicleFullDesc;
    private Integer vehicleFk;
    private Date completionDate;
    private Integer roadScore;
    private Integer routeNumber;
    private String routeArea;
    private Integer roadInstructorFk;
    private String branchName;
    private Integer branchFk;
    private String observationStartTime;
    private String observationEndTime;
    private String trainingStartTime;
    private String trainingEndTime;
    private String behindTheWheelStartTime;
    private String behindTheWheelEndTime;
    private Date observationDate;
    private Date observationCreateDate;
    private Date trainingCreateDate;
    private Date behindTheWheelCreateDate;
    private Integer editable;
    private String schoolName;
    private String observation_userid;
    private Date observation_datestamp;
    private String training_userid;
    private Date training_datestamp;
    private String btw_userid;
    private Date btw_datestamp;
    
    private String notes;
    private String notes_userid;
    private String school_userid;
    private Date school_datestamp;
    private String completion_date_userid;
    private Date completion_date_datestamp;
    private String score_userid;
    private Date score_datestamp;
    private String instructor_userid;
    private Date instructor_datestamp;
    private String road_userid;
    private Date road_datestamp;

    public CommercialTimes() {
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    
    public Integer getClassroomFk() {
        return classroomFk;
    }

    public void setClassroomFk(Integer classroomFk) {
        this.classroomFk = classroomFk;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getInstructorFullName() {
        return instructorFullName;
    }

    public void setInstructorFullName(String instructorFullName) {
        this.instructorFullName = instructorFullName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStudentFk() {
        return studentFk;
    }

    public void setStudentFk(Integer studentFk) {
        this.studentFk = studentFk;
    }

    public Integer getTimePk() {
        return timePk;
    }

    public void setTimePk(Integer timePk) {
        this.timePk = timePk;
    }

    public String getVehicleFullDesc() {
        return vehicleFullDesc;
    }

    public void setVehicleFullDesc(String vehicleFullDesc) {
        this.vehicleFullDesc = vehicleFullDesc;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getRoadInstructorFk() {
        return roadInstructorFk;
    }

    public void setRoadInstructorFk(Integer roadInstructorFk) {
        this.roadInstructorFk = roadInstructorFk;
    }

    public Integer getRoadScore() {
        return roadScore;
    }

    public void setRoadScore(Integer roadScore) {
        this.roadScore = roadScore;
    }

    public Integer getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteArea() {
        return routeArea;
    }

    public void setRouteArea(String routeArea) {
        this.routeArea = routeArea;
    }

	public String getObservationStartTime() {
		return observationStartTime;
	}

	public void setObservationStartTime(String observationStartTime) {
		this.observationStartTime = observationStartTime;
	}

	public String getObservationEndTime() {
		return observationEndTime;
	}

	public void setObservationEndTime(String observationEndTime) {
		this.observationEndTime = observationEndTime;
	}

	public Date getObservationDate() {
		return observationDate;
	}

	public void setObservationDate(Date observationDate) {
		this.observationDate = observationDate;
	}

        public Date getObservationCreateDate() {
            return observationCreateDate;
        }

        public void setObservationCreateDate(Date observationCreateDate) {
            this.observationCreateDate = observationCreateDate;
        }

        public Date getBehindTheWheelCreateDate() {
            return behindTheWheelCreateDate;
        }

        public void setBehindTheWheelCreateDate(Date behindTheWheelCreateDate) {
            this.behindTheWheelCreateDate = behindTheWheelCreateDate;
        }

        public Date getTrainingCreateDate() {
            return trainingCreateDate;
        }

        public void setTrainingCreateDate(Date trainingCreateDate) {
            this.trainingCreateDate = trainingCreateDate;
        }
        
	public Integer getInstructorFk() {
		return instructorFk;
	}

	public void setInstructorFk(Integer instructorFk) {
		this.instructorFk = instructorFk;
	}

	public Integer getVehicleFk() {
		return vehicleFk;
	}

	public void setVehicleFk(Integer vehicleFk) {
		this.vehicleFk = vehicleFk;
	}

	public Integer getBranchFk() {
		return branchFk;
	}

	public void setBranchFk(Integer branchFk) {
		this.branchFk = branchFk;
	}

    public String getBehindTheWheelStartTime() {
        return behindTheWheelStartTime;
    }

    public void setBehindTheWheelStartTime(String behindTheWheelStartTime) {
        this.behindTheWheelStartTime = behindTheWheelStartTime;
    }

    public String getBehindTheWheelEndTime() {
        return behindTheWheelEndTime;
    }

    public void setBehindTheWheelEndTime(String behindTheWheelEndTime) {
        this.behindTheWheelEndTime = behindTheWheelEndTime;
    }

    public String getTrainingStartTime() {
        return trainingStartTime;
    }

    public void setTrainingStartTime(String trainingStartTime) {
        this.trainingStartTime = trainingStartTime;
    }

    public String getTrainingEndTime() {
        return trainingEndTime;
    }

    public void setTrainingEndTime(String trainingEndTime) {
        this.trainingEndTime = trainingEndTime;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public String getObservation_userid() {
		return observation_userid;
	}

	public void setObservation_userid(String observation_userid) {
		this.observation_userid = observation_userid;
	}

	public Date getObservation_datestamp() {
		return observation_datestamp;
	}

	public void setObservation_datestamp(Date observation_datestamp) {
		this.observation_datestamp = observation_datestamp;
	}

	public String getTraining_userid() {
		return training_userid;
	}

	public void setTraining_userid(String training_userid) {
		this.training_userid = training_userid;
	}

	public Date getTraining_datestamp() {
		return training_datestamp;
	}

	public void setTraining_datestamp(Date training_datestamp) {
		this.training_datestamp = training_datestamp;
	}

	public String getBtw_userid() {
		return btw_userid;
	}

	public void setBtw_userid(String btw_userid) {
		this.btw_userid = btw_userid;
	}

	public Date getBtw_datestamp() {
		return btw_datestamp;
	}

	public void setBtw_datestamp(Date btw_datestamp) {
		this.btw_datestamp = btw_datestamp;
	}

	public String getRoad_userid() {
		return road_userid;
	}

	public void setRoad_userid(String road_userid) {
		this.road_userid = road_userid;
	}

	public Date getRoad_datestamp() {
		return road_datestamp;
	}

	public void setRoad_datestamp(Date road_datestamp) {
		this.road_datestamp = road_datestamp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes_userid() {
		return notes_userid;
	}

	public void setNotes_userid(String notes_userid) {
		this.notes_userid = notes_userid;
	}

	public String getSchool_userid() {
		return school_userid;
	}

	public void setSchool_userid(String school_userid) {
		this.school_userid = school_userid;
	}

	public Date getSchool_datestamp() {
		return school_datestamp;
	}

	public void setSchool_datestamp(Date school_datestamp) {
		this.school_datestamp = school_datestamp;
	}

	public String getCompletion_date_userid() {
		return completion_date_userid;
	}

	public void setCompletion_date_userid(String completion_date_userid) {
		this.completion_date_userid = completion_date_userid;
	}

	public Date getCompletion_date_datestamp() {
		return completion_date_datestamp;
	}

	public void setCompletion_date_datestamp(Date completion_date_datestamp) {
		this.completion_date_datestamp = completion_date_datestamp;
	}

	public String getScore_userid() {
		return score_userid;
	}

	public void setScore_userid(String score_userid) {
		this.score_userid = score_userid;
	}

	public Date getScore_datestamp() {
		return score_datestamp;
	}

	public void setScore_datestamp(Date score_datestamp) {
		this.score_datestamp = score_datestamp;
	}

	public String getInstructor_userid() {
		return instructor_userid;
	}

	public void setInstructor_userid(String instructor_userid) {
		this.instructor_userid = instructor_userid;
	}

	public Date getInstructor_datestamp() {
		return instructor_datestamp;
	}

	public void setInstructor_datestamp(Date instructor_datestamp) {
		this.instructor_datestamp = instructor_datestamp;
	}
}

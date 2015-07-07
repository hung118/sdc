package gov.utah.dts.sdc.model;

import java.util.Date;

/**
 * Bean class for student_audit table.
 * @author hnguyen
 *
 */
public class StudentAudit {

	private Integer auditPk;
	private String writtenTestScore_userid;
	private Date writtenTestScore_datestamp;
	private String writtenCompletionDate_userid; 
	private Date writtenCompletionDate_datestamp;  
	private String writtenCompletionSchool_userid; 
	private Date writtenCompletionSchool_datestamp;
	private String classroomCompletionDate_userid; 
	private Date classroomCompletionDate_datestamp;
	private String classroomCompletionSchool_userid; 
	private Date classroomCompletionSchool_datestamp;
	private String observationCompletionDate_userid; 
	private Date observationCompletionDate_datestamp;
	private String observationCompletionSchool_userid; 
	private Date observationCompletionSchool_datestamp;
	private String btwCompletionDate_userid; 
	private Date btwCompletionDate_datestamp;
	private String btwCompletionSchool_userid; 
	private Date btwCompletionSchool_datestamp;
	private String roadInstructor_userid;
	private Date roadInstructor_datestamp;
	private String roadCompletionDate_userid; 
	private Date roadCompletionDate_datestamp;
	private String roadScore_userid; 
	private Date roadScore_datestamp;
	private String roadCompletionSchool_userid; 
	private Date roadCompletionSchool_datestamp;
	
	public Integer getAuditPk() {
		return auditPk;
	}
	public void setAuditPk(Integer auditPk) {
		this.auditPk = auditPk;
	}
	public String getWrittenTestScore_userid() {
		return writtenTestScore_userid;
	}
	public void setWrittenTestScore_userid(String writtenTestScore_userid) {
		this.writtenTestScore_userid = writtenTestScore_userid;
	}
	public Date getWrittenTestScore_datestamp() {
		return writtenTestScore_datestamp;
	}
	public void setWrittenTestScore_datestamp(Date writtenTestScore_datestamp) {
		this.writtenTestScore_datestamp = writtenTestScore_datestamp;
	}
	public String getWrittenCompletionDate_userid() {
		return writtenCompletionDate_userid;
	}
	public void setWrittenCompletionDate_userid(
			String writtenCompletionDate_userid) {
		this.writtenCompletionDate_userid = writtenCompletionDate_userid;
	}
	public Date getWrittenCompletionDate_datestamp() {
		return writtenCompletionDate_datestamp;
	}
	public void setWrittenCompletionDate_datestamp(
			Date writtenCompletionDate_datestamp) {
		this.writtenCompletionDate_datestamp = writtenCompletionDate_datestamp;
	}
	public String getWrittenCompletionSchool_userid() {
		return writtenCompletionSchool_userid;
	}
	public void setWrittenCompletionSchool_userid(
			String writtenCompletionSchool_userid) {
		this.writtenCompletionSchool_userid = writtenCompletionSchool_userid;
	}
	public Date getWrittenCompletionSchool_datestamp() {
		return writtenCompletionSchool_datestamp;
	}
	public void setWrittenCompletionSchool_datestamp(
			Date writtenCompletionSchool_datestamp) {
		this.writtenCompletionSchool_datestamp = writtenCompletionSchool_datestamp;
	}
	public String getClassroomCompletionDate_userid() {
		return classroomCompletionDate_userid;
	}
	public void setClassroomCompletionDate_userid(
			String classroomCompletionDate_userid) {
		this.classroomCompletionDate_userid = classroomCompletionDate_userid;
	}
	public Date getClassroomCompletionDate_datestamp() {
		return classroomCompletionDate_datestamp;
	}
	public void setClassroomCompletionDate_datestamp(
			Date classroomCompletionDate_datestamp) {
		this.classroomCompletionDate_datestamp = classroomCompletionDate_datestamp;
	}
	public String getClassroomCompletionSchool_userid() {
		return classroomCompletionSchool_userid;
	}
	public void setClassroomCompletionSchool_userid(
			String classroomCompletionSchool_userid) {
		this.classroomCompletionSchool_userid = classroomCompletionSchool_userid;
	}
	public Date getClassroomCompletionSchool_datestamp() {
		return classroomCompletionSchool_datestamp;
	}
	public void setClassroomCompletionSchool_datestamp(
			Date classroomCompletionSchool_datestamp) {
		this.classroomCompletionSchool_datestamp = classroomCompletionSchool_datestamp;
	}
	public String getObservationCompletionDate_userid() {
		return observationCompletionDate_userid;
	}
	public void setObservationCompletionDate_userid(
			String observationCompletionDate_userid) {
		this.observationCompletionDate_userid = observationCompletionDate_userid;
	}
	public Date getObservationCompletionDate_datestamp() {
		return observationCompletionDate_datestamp;
	}
	public void setObservationCompletionDate_datestamp(
			Date observationCompletionDate_datestamp) {
		this.observationCompletionDate_datestamp = observationCompletionDate_datestamp;
	}
	public String getObservationCompletionSchool_userid() {
		return observationCompletionSchool_userid;
	}
	public void setObservationCompletionSchool_userid(
			String observationCompletionSchool_userid) {
		this.observationCompletionSchool_userid = observationCompletionSchool_userid;
	}
	public Date getObservationCompletionSchool_datestamp() {
		return observationCompletionSchool_datestamp;
	}
	public void setObservationCompletionSchool_datestamp(
			Date observationCompletionSchool_datestamp) {
		this.observationCompletionSchool_datestamp = observationCompletionSchool_datestamp;
	}
	public String getBtwCompletionDate_userid() {
		return btwCompletionDate_userid;
	}
	public void setBtwCompletionDate_userid(String btwCompletionDate_userid) {
		this.btwCompletionDate_userid = btwCompletionDate_userid;
	}
	public Date getBtwCompletionDate_datestamp() {
		return btwCompletionDate_datestamp;
	}
	public void setBtwCompletionDate_datestamp(Date btwCompletionDate_datestamp) {
		this.btwCompletionDate_datestamp = btwCompletionDate_datestamp;
	}
	public String getBtwCompletionSchool_userid() {
		return btwCompletionSchool_userid;
	}
	public void setBtwCompletionSchool_userid(
			String btwCompletionSchool_userid) {
		this.btwCompletionSchool_userid = btwCompletionSchool_userid;
	}
	public Date getBtwCompletionSchool_datestamp() {
		return btwCompletionSchool_datestamp;
	}
	public void setBtwCompletionSchool_datestamp(
			Date btwCompletionSchool_datestamp) {
		this.btwCompletionSchool_datestamp = btwCompletionSchool_datestamp;
	}
	public String getRoadInstructor_userid() {
		return roadInstructor_userid;
	}
	public void setRoadInstructor_userid(String roadInstructor_userid) {
		this.roadInstructor_userid = roadInstructor_userid;
	}
	public Date getRoadInstructor_datestamp() {
		return roadInstructor_datestamp;
	}
	public void setRoadInstructor_datestamp(Date roadInstructor_datestamp) {
		this.roadInstructor_datestamp = roadInstructor_datestamp;
	}
	public String getRoadCompletionDate_userid() {
		return roadCompletionDate_userid;
	}
	public void setRoadCompletionDate_userid(String roadCompletionDate_userid) {
		this.roadCompletionDate_userid = roadCompletionDate_userid;
	}
	public Date getRoadCompletionDate_datestamp() {
		return roadCompletionDate_datestamp;
	}
	public void setRoadCompletionDate_datestamp(Date roadCompletionDate_datestamp) {
		this.roadCompletionDate_datestamp = roadCompletionDate_datestamp;
	}
	public String getRoadScore_userid() {
		return roadScore_userid;
	}
	public void setRoadScore_userid(String roadScore_userid) {
		this.roadScore_userid = roadScore_userid;
	}
	public Date getRoadScore_datestamp() {
		return roadScore_datestamp;
	}
	public void setRoadScore_datestamp(Date roadScore_datestamp) {
		this.roadScore_datestamp = roadScore_datestamp;
	}
	public String getRoadCompletionSchool_userid() {
		return roadCompletionSchool_userid;
	}
	public void setRoadCompletionSchool_userid(String roadCompletionSchool_userid) {
		this.roadCompletionSchool_userid = roadCompletionSchool_userid;
	}
	public Date getRoadCompletionSchool_datestamp() {
		return roadCompletionSchool_datestamp;
	}
	public void setRoadCompletionSchool_datestamp(
			Date roadCompletionSchool_datestamp) {
		this.roadCompletionSchool_datestamp = roadCompletionSchool_datestamp;
	}

}

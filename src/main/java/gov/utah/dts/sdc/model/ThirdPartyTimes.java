package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class ThirdPartyTimes implements Serializable {

    private Integer timePk;
    private Integer studentFk;
    private Integer classroomFk;
     private Integer section;
    private Date startTime;
    private Date endTime;
    private String instructorFullName;
    private String vehicleFullDesc;
    private Date completionDate;
    private Integer roadScore;
    private Integer routeNumber;
    private String routeArea;
    private Integer roadInstructorFk;
    private String branchName;

    public ThirdPartyTimes() {
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
    
}

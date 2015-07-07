package gov.utah.dts.sdc.model;

import java.util.Date;
import java.io.Serializable;

public class Audit implements Serializable {

    private Integer studentFk;
    private String schoolName;
    private String schoolNumber;
    private Long studentNumber;
    private Date writtenTestCompletionDate;
    private Integer writtenTestScore;
    private Date roadTestCompletionDate;
    private Integer roadTestScore;
    private String roadTestInstructorFk;
    private Date completionDate;
    private Integer completionSchoolNumber;
    private Integer writtenCompletionSchoolNumber;
    private Integer roadCompletionSchoolNumber;

    public Audit() {
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getCompletionSchoolNumber() {
        return completionSchoolNumber;
    }

    public void setCompletionSchoolNumber(Integer completionSchoolNumber) {
        this.completionSchoolNumber = completionSchoolNumber;
    }

    public Integer getRoadCompletionSchoolNumber() {
        return roadCompletionSchoolNumber;
    }

    public void setRoadCompletionSchoolNumber(Integer roadCompletionSchoolNumber) {
        this.roadCompletionSchoolNumber = roadCompletionSchoolNumber;
    }

    public Date getRoadTestCompletionDate() {
        return roadTestCompletionDate;
    }

    public void setRoadTestCompletionDate(Date roadTestCompletionDate) {
        this.roadTestCompletionDate = roadTestCompletionDate;
    }

    public String getRoadTestInstructorFk() {
        return roadTestInstructorFk;
    }

    public void setRoadTestInstructorFk(String roadTestInstructorFk) {
        this.roadTestInstructorFk = roadTestInstructorFk;
    }

    public Integer getRoadTestScore() {
        return roadTestScore;
    }

    public void setRoadTestScore(Integer roadTestScore) {
        this.roadTestScore = roadTestScore;
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

    public Integer getStudentFk() {
        return studentFk;
    }

    public void setStudentFk(Integer studentFk) {
        this.studentFk = studentFk;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getWrittenCompletionSchoolNumber() {
        return writtenCompletionSchoolNumber;
    }

    public void setWrittenCompletionSchoolNumber(Integer writtenCompletionSchoolNumber) {
        this.writtenCompletionSchoolNumber = writtenCompletionSchoolNumber;
    }

    public Date getWrittenTestCompletionDate() {
        return writtenTestCompletionDate;
    }

    public void setWrittenTestCompletionDate(Date writtenTestCompletionDate) {
        this.writtenTestCompletionDate = writtenTestCompletionDate;
    }

    public Integer getWrittenTestScore() {
        return writtenTestScore;
    }

    public void setWrittenTestScore(Integer writtenTestScore) {
        this.writtenTestScore = writtenTestScore;
    }
    
    
}

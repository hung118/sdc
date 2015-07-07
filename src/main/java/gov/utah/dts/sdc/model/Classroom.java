package gov.utah.dts.sdc.model;

import java.util.Date;
import java.io.Serializable;

public class Classroom implements Serializable {
    
    private Integer classroomPk;
    private Integer studentPk;
    private Integer schoolFk;
    private Date    timestamp;
    private String  schoolName;
    private String  alias;
    private Integer classroomClosed;
    private String  classroomNumber;
    private Integer homeStudy;
    private Integer instructorFk;
    private String  instructorFullName;
    private String  instructorFirstName;
    private String  instructorMiddleName;
    private String  instructorLastName;
    
    public Classroom() {
        super();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public Integer getClassroomClosed() {
        return classroomClosed;
    }

    public void setClassroomClosed(Integer classroomClosed) {
        this.classroomClosed = classroomClosed;
    }

    public Integer getClassroomPk() {
        return classroomPk;
    }

    public void setClassroomPk(Integer classroomPk) {
        this.classroomPk = classroomPk;
    }

    public Integer getInstructorFk() {
        return instructorFk;
    }

    public void setInstructorFk(Integer instructorFk) {
        this.instructorFk = instructorFk;
    }

   public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        this.instructorFirstName = instructorFirstName;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public String getInstructorMiddleName() {
        return instructorMiddleName;
    }

    public void setInstructorMiddleName(String instructorMiddleName) {
        this.instructorMiddleName = instructorMiddleName;
    }

    
    
    public String getInstructorFullName() {
        if (instructorFullName != null){
            if(instructorFullName.equals("")){
               setInstructorFullName(instructorFirstName,instructorLastName,instructorMiddleName);
            }
        } else {
            setInstructorFullName(instructorFirstName,instructorLastName,instructorMiddleName);
        }
        return instructorFullName;
    }

    public void setInstructorFullName(String instructorFullName) {
        this.instructorFullName = instructorFullName;
    }

    public void setInstructorFullName(String firstName,String lastName, String middleName) {
        String name = lastName + ", " + firstName;
        if (middleName != null) {
            if(!middleName.equals("")){
                name = name +" "+ middleName;
            }
        }
        this.instructorFullName = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }

    public Integer getHomeStudy() {
        return homeStudy;
    }

    public void setHomeStudy(Integer homeStudy) {
        this.homeStudy = homeStudy;
    }

    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }
}

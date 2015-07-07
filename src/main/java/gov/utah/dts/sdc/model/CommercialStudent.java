package gov.utah.dts.sdc.model;

import java.util.Date;
import java.io.Serializable;

public class CommercialStudent implements Serializable {
    private Integer studentPk;
    private Integer schoolFk;
    private String schoolName;
    private String schoolNumber;
    private String fileNumber; //licenseNumber
    private String licenseType;
    private Integer studentNum;
    private String  firstName;
    private String  lastName;
    private String  middleName;
    private String  suffix;
    private String studentFullName;
    private String  address1;
    private String  address2;
    private String  city;
    private String  state;
    private String  zip;
    private String  homePhone;
    private String email;
    private Date    dob;
    private Date eligibilityDate;
    private Date writtenTestCompletionDate;
    private Integer writtenTestScore;
    private Date roadTestCompletionDate;
    private Integer roadTestScore;
    private String roadTestInstructorFk;
    private Date classroomCompletionDate;
    private boolean classroomCompletionCheck;
    private Date behindWheelCompletionDate;
    private boolean behindWheelCompletionCheck;
    private Date observationCompletionDate;
    private boolean observationCompletionCheck;
    private String classroomCompletionCheck2;
    private String behindWheelCompletionCheck2;
    private String observationCompletionCheck2;
    private String  updatedBy;
    private Date    timestamp;
    private Integer[] schools;
    
    public CommercialStudent() {
        super();
    }
    
    public CommercialStudent(String fileNumber, String firstName, String lastName, Date writtenTestCompletionDate) {
        super();
        this.setFileNumber(fileNumber);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setWrittenTestCompletionDate(writtenTestCompletionDate);
    }
    
    public String getStudentFullName() {
        if (studentFullName != null){
            if(studentFullName.equals("")){
               setStudentFullName(firstName,lastName,middleName,suffix);
            }
        } else {
            setStudentFullName(firstName,lastName,middleName,suffix);
        }
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public void setStudentFullName(String firstName,String lastName, String middleName, String suffix) {
        String name = lastName + ", " + firstName;
        if (middleName != null) {
            if(!middleName.equals("")){
                name = name +" "+ middleName;
            }
        }
        if (suffix != null){
            if(!suffix.equals("")) {
                name = new StringBuffer().append(name).append(", ").append(suffix).toString();
            }
        }
        this.studentFullName = name;
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

    public boolean isBehindWheelCompletionCheck() {
        return behindWheelCompletionCheck;
    }

    public void setBehindWheelCompletionCheck(boolean behindWheelCompletionCheck) {
        this.behindWheelCompletionCheck = behindWheelCompletionCheck;
    }

    public String getBehindWheelCompletionCheck2() {
        return behindWheelCompletionCheck2;
    }

    public void setBehindWheelCompletionCheck2(String behindWheelCompletionCheck2) {
        this.behindWheelCompletionCheck2 = behindWheelCompletionCheck2;
    }

    public Date getBehindWheelCompletionDate() {
        return behindWheelCompletionDate;
    }

    public void setBehindWheelCompletionDate(Date behindWheelCompletionDate) {
        this.behindWheelCompletionDate = behindWheelCompletionDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isClassroomCompletionCheck() {
        return classroomCompletionCheck;
    }

    public void setClassroomCompletionCheck(boolean classroomCompletionCheck) {
        this.classroomCompletionCheck = classroomCompletionCheck;
    }

    public String getClassroomCompletionCheck2() {
        return classroomCompletionCheck2;
    }

    public void setClassroomCompletionCheck2(String classroomCompletionCheck2) {
        this.classroomCompletionCheck2 = classroomCompletionCheck2;
    }

    public Date getClassroomCompletionDate() {
        return classroomCompletionDate;
    }

    public void setClassroomCompletionDate(Date classroomCompletionDate) {
        this.classroomCompletionDate = classroomCompletionDate;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getEligibilityDate() {
        return eligibilityDate;
    }

    public void setEligibilityDate(Date eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public boolean isObservationCompletionCheck() {
        return observationCompletionCheck;
    }

    public void setObservationCompletionCheck(boolean observationCompletionCheck) {
        this.observationCompletionCheck = observationCompletionCheck;
    }

    public String getObservationCompletionCheck2() {
        return observationCompletionCheck2;
    }

    public void setObservationCompletionCheck2(String observationCompletionCheck2) {
        this.observationCompletionCheck2 = observationCompletionCheck2;
    }

    public Date getObservationCompletionDate() {
        return observationCompletionDate;
    }

    public void setObservationCompletionDate(Date observationCompletionDate) {
        this.observationCompletionDate = observationCompletionDate;
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

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
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

    public Integer[] getSchools() {
        return schools;
    }

    public void setSchools(Integer[] schools) {
        this.schools = schools;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(Integer studentPk) {
        this.studentPk = studentPk;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

   
}

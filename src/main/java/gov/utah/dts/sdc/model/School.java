package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class School implements Serializable {
    
    private Integer schoolPk;
    private String  schoolNumber;
    private String  schoolName;
    private Integer deleted;
    private Integer schoolType;
    private Integer homeStudy;
    private String  address1;
    private String  address2;
    private String  city;
    private String  state;
    private String  zip;
    private String  businessPhone;
    private String  businessPhone2;
    private String  updatedBy;
    private Date    timestamp;
    private Date    lastInspectionDate;
    private Date    schoolClosureDate;
    private Date reportStartDate;
    private Date reportEndDate;
    private Integer writtenTestCount;
    private Integer roadTestCount;
    private Date  expireDate;
    private String expireDateStr;
    
    public School() {
        super();
    }

    public Integer getSchoolPk() {
        return schoolPk;
    }

    public void setSchoolPk(Integer schoolPk) {
        this.schoolPk = schoolPk;
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

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessPhone2() {
        return businessPhone2;
    }

    public void setBusinessPhone2(String businessPhone2) {
        this.businessPhone2 = businessPhone2;
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

    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getSchoolClosureDate() {
        return schoolClosureDate;
    }

    public void setSchoolClosureDate(Date schoolClosureDate) {
        this.schoolClosureDate = schoolClosureDate;
    }

    public Integer getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Integer schoolType) {
        this.schoolType = schoolType;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Integer getRoadTestCount() {
        return roadTestCount;
    }

    public void setRoadTestCount(Integer roadTestCount) {
        this.roadTestCount = roadTestCount;
    }

    public Integer getWrittenTestCount() {
        return writtenTestCount;
    }

    public void setWrittenTestCount(Integer writtenTestCount) {
        this.writtenTestCount = writtenTestCount;
    }

    public Integer getHomeStudy() {
        return homeStudy;
    }

    public void setHomeStudy(Integer homeStudy) {
        this.homeStudy = homeStudy;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getExpireDateStr() {
		return expireDateStr;
	}

	public void setExpireDateStr(String expireDateStr) {
		this.expireDateStr = expireDateStr;
	}

}

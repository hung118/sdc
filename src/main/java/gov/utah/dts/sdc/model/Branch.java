package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class Branch implements Serializable {
    private Integer branchPk;
    private Integer schoolFk;
    private String  address1;
    private String  address2;
    private String  city;
    private String  state;
    private String  zip;
    private String  businessPhone;
    private String  businessPhone2;
    private String  updatedBy;
    private Date    timestamp;
     
    public Branch() {
        super();
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getBranchPk() {
        return branchPk;
    }

    public void setBranchPk(Integer branchPk) {
        this.branchPk = branchPk;
    }

    
}

package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class Vehicle implements Serializable {
    private Integer vehiclePk;
    private String  vehiclePlate;
    private String  vehicleState;
    private Integer  schoolFk;
    private String  schoolName;
    private String vehicleMake;
    private Integer vehicleYear;
    private String vehicleVin;
    private String insurancePolicy;
    private Date insuranceExpire;
    private String insuranceCompany;
    private String insuranceAgent;
    private String insurancePhone;
    private String deleted;
        
    public Vehicle() {
        super();
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

    public Integer getVehiclePk() {
        return vehiclePk;
    }

    public void setVehiclePk(Integer vehiclePk) {
        this.vehiclePk = vehiclePk;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(String vehicleState) {
        this.vehicleState = vehicleState;
    }

    public String getInsuranceAgent() {
        return insuranceAgent;
    }

    public void setInsuranceAgent(String insuranceAgent) {
        this.insuranceAgent = insuranceAgent;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public Date getInsuranceExpire() {
        return insuranceExpire;
    }

    public void setInsuranceExpire(Date insuranceExpire) {
        this.insuranceExpire = insuranceExpire;
    }

    public String getInsurancePhone() {
        return insurancePhone;
    }

    public void setInsurancePhone(String insurancePhone) {
        this.insurancePhone = insurancePhone;
    }

    public String getInsurancePolicy() {
        return insurancePolicy;
    }

    public void setInsurancePolicy(String insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}

package gov.utah.dts.sdc.jasper;

public class InstructorBean {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String schoolName;
	private String businessPhone;
	private java.util.Date licenseExpireDate;
	private String licenseExpireDateStr;
	
	public InstructorBean() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public java.util.Date getLicenseExpireDate() {
		return licenseExpireDate;
	}

	public void setLicenseExpireDate(java.util.Date licenseExpireDate) {
		this.licenseExpireDate = licenseExpireDate;
	}

	public String getLicenseExpireDateStr() {
		return licenseExpireDateStr;
	}

	public void setLicenseExpireDateStr(String licenseExpireDateStr) {
		this.licenseExpireDateStr = licenseExpireDateStr;
	}
	
}
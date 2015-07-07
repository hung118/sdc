package gov.utah.dts.sdc.jasper;

public class RoadTesterBean {
	
	private String schoolName;
	private String testerNameNumber;
	private Double passedTests;
	private Double failedTests;
	private Double totalTests;
	
	public RoadTesterBean() {
		
	}
	
	public void setTesterNameNumber(String testerNameNumber) {
		this.testerNameNumber = testerNameNumber;
	}
	public String getTesterNameNumber() {
		return testerNameNumber;
	}
	
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	
	public void setPassedTests(Double passedTests) {
		this.passedTests = passedTests;
	}
	public Double getPassedTests() {
		return passedTests;
	}
	
	public void setFailedTests(Double failedTests) {
		this.failedTests = failedTests;
	}
	public Double getFailedTests() {
		return failedTests;
	}

	public Double getTotalTests() {
		return totalTests;
	}

	public void setTotalTests(Double totalTests) {
		this.totalTests = totalTests;
	}
	
}

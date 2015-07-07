package gov.utah.dts.sdc.jasper;

public class OverlapTimeBean {

	private String firstName;
	private String middleName;
	private String lastName;
	private String btw = "NO";
	private String observation = "NO";
	private String training = "NO";
	private String byType;
	private int student_fk;
	private int instructor_fk;
	private long startTime;
	private String startTimeStr;
	private long endTime;
	private String endTimeStr;
	private long startDate;
	private String startDateStr;
	private double elapsedTime;
	private String vehicleInfo;
	private int vehicle_fk;
	private String studentNumber;
	private String studentName;
	private String fileNumber;
	private String dlnum;	// unused
	private String schoolName;
	
	private String btw_cr = "0";
	private String obs_cr = "0";
	private String btw_obs = "0";
	
	private String rd_btw = "0";
	private String rd_obs = "0";
	private String rd_cr = "0";
	
	private String road = "0";
	
	private String btw_road = "0";
	
	private int trainingInt = 0;
	private int btwInt = 0;
	private int observationInt = 0;
	
	public OverlapTimeBean() {
		
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getMiddleName() {
		return middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}

	public void setBtw(String btw) {
		this.btw = btw;
	}
	public String getBtw() {
		return btw;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getObservation() {
		return observation;
	}

	public void setTraining(String training) {
		this.training = training;
	}
	public String getTraining() {
		return training;
	}
	
	public void setByType(String byType) {
		this.byType = byType;
	}

	public String getByType() {
		return byType;
	}

	public void setStudent_fk(int student_fk) {
		this.student_fk = student_fk;
	}
	public int getStudent_fk() {
		return student_fk;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTimeStr(String startTimeStr) {	
		
		this.startTimeStr = startTimeStr;
	}
	
	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getEndTime() {
		return endTime;
	}

	public void setEndTimeStr(String endTimeStr) {
		
		// remove the date, keep time only 11/03/2007 03:00 PM
		this.endTimeStr = endTimeStr.substring(11);
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setInstructor_fk(int instructor_fk) {
		this.instructor_fk = instructor_fk;
	}

	public int getInstructor_fk() {
		return instructor_fk;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public void setVehicleInfo(String vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}

	public String getVehicleInfo() {
		return vehicleInfo;
	}

	public void setVehicle_fk(int vehicle_fk) {
		this.vehicle_fk = vehicle_fk;
	}

	public int getVehicle_fk() {
		return vehicle_fk;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setDlnum(String dlnum) {
		this.dlnum = dlnum;
	}

	public String getDlnum() {
		return dlnum;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getBtw_cr() {
		return btw_cr;
	}

	public void setBtw_cr(String btw_cr) {
		this.btw_cr = btw_cr;
	}

	public String getObs_cr() {
		return obs_cr;
	}

	public void setObs_cr(String obs_cr) {
		this.obs_cr = obs_cr;
	}

	public String getBtw_obs() {
		return btw_obs;
	}

	public void setBtw_obs(String btw_obs) {
		this.btw_obs = btw_obs;
	}

	public String getRd_btw() {
		return rd_btw;
	}

	public void setRd_btw(String rd_btw) {
		this.rd_btw = rd_btw;
	}

	public String getRd_obs() {
		return rd_obs;
	}

	public void setRd_obs(String rd_obs) {
		this.rd_obs = rd_obs;
	}

	public String getRd_cr() {
		return rd_cr;
	}

	public void setRd_cr(String rd_cr) {
		this.rd_cr = rd_cr;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getBtw_road() {
		return btw_road;
	}

	public void setBtw_road(String btw_road) {
		this.btw_road = btw_road;
	}

	public int getTrainingInt() {
		return trainingInt;
	}

	public void setTrainingInt(int trainingInt) {
		this.trainingInt = trainingInt;
	}

	public int getBtwInt() {
		return btwInt;
	}

	public void setBtwInt(int btwInt) {
		this.btwInt = btwInt;
	}

	public int getObservationInt() {
		return observationInt;
	}

	public void setObservationInt(int observationInt) {
		this.observationInt = observationInt;
	}
}

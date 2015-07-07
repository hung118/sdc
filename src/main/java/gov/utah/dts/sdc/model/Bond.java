package gov.utah.dts.sdc.model;

import java.util.Date;
import java.io.Serializable;

public class Bond implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer bondsPk;
	private Integer schoolFk;
	private String  schoolName;
	private Date issueDate;
	private Date expireDate;
	private Integer amount;
	private String company;
	private String agent;
	private String phone;
	private String bondNumber;
	private String deleted;
	
    public Bond() {
        super();
    }

	public Integer getBondsPk() {
		return bondsPk;
	}

	public void setBondsPk(Integer bondsPk) {
		this.bondsPk = bondsPk;
	}

	public Integer getSchoolFk() {
		return schoolFk;
	}

	public void setSchoolFk(Integer schoolFk) {
		this.schoolFk = schoolFk;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBondNumber() {
		return bondNumber;
	}

	public void setBondNumber(String bondNumber) {
		this.bondNumber = bondNumber;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}

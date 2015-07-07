package gov.utah.dts.sdc.model;

import java.io.Serializable;
import java.util.Date;

public class CompletionSlip implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer namevaluePk;
	private String name;
	private String value;
	private String description;
	private Date timestamp;
	
	public Integer getNamevaluePk() {
		return namevaluePk;
	}
	public void setNamevaluePk(Integer namevaluePk) {
		this.namevaluePk = namevaluePk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	

}

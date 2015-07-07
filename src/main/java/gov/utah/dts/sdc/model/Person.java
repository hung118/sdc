package gov.utah.dts.sdc.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class Person implements Serializable {
    private Integer personPk;
    private String  firstName;
    private String  lastName;
    private String  middleName;
    private String  fullName;
    private String  suffix;
    private Date    dob;
    private String  address1;
    private String  address2;
    private String  city;
    private String  state;
    private String  zip;
    private String  homePhone;
    private String  businessPhone;
    private String  email;
    private String  driversLicenseNumber;
    private String  driversLicenseState;
    private String  updatedBy;
    private Date    timestamp;
    private String  instructorNum;
    private String  dn; 
    private Integer[] roles;
    private Integer[] testTypes;
    private boolean knowledgeTest;
    private boolean skillsTest;
    private Integer[] associatedSchools;
    private String languages; 
    private Date   backgroundCheckDate;
    private Date   pdpCheckDate;
    private Date   instructorTrainingDate;
    private Date   licenseExpireDate; 
    private Date   dlHistoryDate;
    private String deleted;
    
    public Person() {
        super();
    }
    
    public Person(Integer personPk, String firstName, String lastName) {
        super();
        this.setPersonPk(personPk);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Integer getPersonPk() {
        return personPk;
    }

    public void setPersonPk(Integer personPk) {
        this.personPk = personPk;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public void setDriversLicenseNumber(String driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }

    public String getDriversLicenseState() {
        return driversLicenseState;
    }

    public void setDriversLicenseState(String driversLicenseState) {
        this.driversLicenseState = driversLicenseState;
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

    public String getInstructorNum() {
        return instructorNum;
    }

    public void setInstructorNum(String instructorNum) {
        this.instructorNum = instructorNum;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    

    public Integer[] getRoles() {
        
        return roles;
    }

    public void setRoles(Integer[] roles) {
        
        this.roles = roles;
    }

    public void setTestTypes(Integer[] testTypes) {
    	for (int i = 0; i < testTypes.length; i++) {
    		if (testTypes[i].intValue() == 1) {
    			setKnowledgeTest(true);
    		} else {
    			setSkillsTest(true);
    		}
    	}
	}

	public Integer[] getTestTypes() {
		Integer[] tmp = null;
		if (isKnowledgeTest() && isSkillsTest()) {
			tmp = new Integer[2];
			tmp[0] = new Integer(1);
			tmp[1] = new Integer(2);
		} else if (isKnowledgeTest() && !isSkillsTest()) {
			tmp = new Integer[1];
			tmp[0] = new Integer(1);
		} else if (!isKnowledgeTest() && isSkillsTest()) {
			tmp = new Integer[1];
			tmp[0] = new Integer(2);
		} else {
			// do nothing
		}
				
		return tmp;
	}

	public void setKnowledgeTest(boolean knowledgeTest) {
		this.knowledgeTest = knowledgeTest;
	}

    public boolean isKnowledgeTest() {
    	return knowledgeTest;
    }
    
	public String getKnowledgeTest2() {
        if (isKnowledgeTest()) {
            return "1";
        } else {
            return "0";
        }
	}

	public void setSkillsTest(boolean skillsTest) {
		this.skillsTest = skillsTest;
	}

	public boolean isSkillsTest() {
		return skillsTest;
	}
	
	public String getSkillsTest2() {
        if (isSkillsTest()) {
            return "1";
        } else {
            return "0";
        }		
	}

	public String getFullName() {
        if (fullName != null){
            if(fullName.equals("")){
               setFullName(firstName,lastName,middleName,suffix);
            }
        } else {
            setFullName(firstName,lastName,middleName,suffix);
        }
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFullName(String firstName,String lastName, String middleName, String suffix) {
        String name = firstName;
        if (middleName != null) {
            if(!middleName.equals("")){
                name = name +" "+ middleName;
            }
        }
        if (lastName != null) {
            if(!lastName.equals("")){
                name = name +" "+ lastName;
            }
        }
        if (suffix != null){
            if(!suffix.equals("")) {
                name = new StringBuffer().append(name).append(", ").append(suffix).toString();
            }
        }
        this.fullName = name;
    }

    public Integer[] getAssociatedSchools() {
        return associatedSchools;
    }

    public void setAssociatedSchools(Integer[] associatedSchools) {
        this.associatedSchools = associatedSchools;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Date getBackgroundCheckDate() {
        return backgroundCheckDate;
    }

    public void setBackgroundCheckDate(Date backgroundCheckDate) {
        this.backgroundCheckDate = backgroundCheckDate;
    }

    public Date getInstructorTrainingDate() {
        return instructorTrainingDate;
    }

    public void setInstructorTrainingDate(Date instructorTrainingDate) {
        this.instructorTrainingDate = instructorTrainingDate;
    }

    public Date getLicenseExpireDate() {
        return licenseExpireDate;
    }

    public void setLicenseExpireDate(Date licenseExpireDate) {
        this.licenseExpireDate = licenseExpireDate;
    }

    public Date getPdpCheckDate() {
        return pdpCheckDate;
    }

    public void setPdpCheckDate(Date pdpCheckDate) {
        this.pdpCheckDate = pdpCheckDate;
    }

    public Date getDlHistoryDate() {
        return dlHistoryDate;
    }

    public void setDlHistoryDate(Date dlHistoryDate) {
        this.dlHistoryDate = dlHistoryDate;
    }

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}

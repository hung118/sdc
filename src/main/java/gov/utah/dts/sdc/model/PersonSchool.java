package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class PersonSchool implements Serializable {
    private Integer  personSchoolPk;
    private Integer  personFk;
    private Integer  schoolFk;
    
    
    public PersonSchool() {
        super();
    }

    public Integer getPersonSchoolPk() {
        return personSchoolPk;
    }

    public void setPersonSchoolPk(Integer personSchoolPk) {
        this.personSchoolPk = personSchoolPk;
    }

    public Integer getPersonFk() {
        return personFk;
    }

    public void setPersonFk(Integer personFk) {
        this.personFk = personFk;
    }

    public Integer getSchoolFk() {
        return schoolFk;
    }

    public void setSchoolFk(Integer schoolFk) {
        this.schoolFk = schoolFk;
    }
    
}

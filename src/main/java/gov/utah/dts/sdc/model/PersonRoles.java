package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class PersonRoles implements Serializable {
    private Integer  personRolesPk;
    private Integer  personFk;
    private Integer  roleTypesFk;
    private String  email;
    
 
    
    public PersonRoles() {
        super();
    }

    public Integer getPersonRolesPk() {
        return personRolesPk;
    }

    public void setPersonRolesPk(Integer personRolesPk) {
        this.personRolesPk = personRolesPk;
    }

    public Integer getPersonFk() {
        return personFk;
    }

    public void setPersonFk(Integer personFk) {
        this.personFk = personFk;
    }

    public Integer getRoleTypesFk() {
        return roleTypesFk;
    }

    public void setRoleTypesFk(Integer roleTypesFk) {
        this.roleTypesFk = roleTypesFk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    
}

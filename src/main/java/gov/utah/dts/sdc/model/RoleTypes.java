package gov.utah.dts.sdc.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class RoleTypes implements Serializable {
    private Integer  roleTypesPk;
    private String  description;
    
 
    
    public RoleTypes() {
        super();
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleTypesPk() {
        return roleTypesPk;
    }

    public void setRoleTypesPk(Integer roleTypesPk) {
        this.roleTypesPk = roleTypesPk;
    }
    
    
}

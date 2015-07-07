package gov.utah.dts.sdc.model;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.service.RoleTypesService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class News implements Serializable {
    private Integer newsPk;
    private String description;
    private String news;
    private Date timestamp;
    private String roleName;
    private Integer roleTypesFk;
    private String deleted;

    public Integer getRoleTypesFk() {
        return roleTypesFk;
    }

    public void setRoleTypesFk(Integer roleTypesFk) {
        this.roleTypesFk = roleTypesFk;
    }
    private Integer[] rolesList;
    
    
    public News() {
        super();
    }
    
    public Integer getNewsPk() {
        return newsPk;
    }
    
    public void setNewsPk(Integer newsPk) {
        this.newsPk = newsPk;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getNews() {
        return news;
    }
    
    public void setNews(String news) {
        this.news = news;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
       
    public Integer[] getRolesList() {
        return rolesList;
    }
    
    public void setRolesList(Integer[] rolesList) {
        this.rolesList = rolesList;
    }
    
    public String getRoleName() {
        if (roleName == null) {
            if (getRoleTypesFk().intValue() != 0) {
                RoleTypesService service = new RoleTypesService();
                Map hm = new HashMap();
                hm.put("roleTypesPk",getRoleTypesFk());
                try {
                    setRoleName(service.getRole(hm));
                } catch (DaoException ex) {
                    ex.printStackTrace();
                    setRoleName("");
                }
            } else {
                setRoleName("ALL USERS");
            }
        }
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}

package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.model.Classroom;
import gov.utah.dts.sdc.model.School;
import gov.utah.dts.sdc.service.PersonSchoolService;
import gov.utah.dts.sdc.service.SchoolService;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.model.Branch;
import gov.utah.dts.sdc.service.ClassroomService;
import gov.utah.dts.sdc.service.VehicleService;
import java.util.ArrayList;
import java.util.List;

public class SchoolAction extends SDCSupport implements Preparable, ModelDriven{
    
    protected static Log log = LogFactory.getLog(SchoolAction.class);
    private Integer schoolPk;
    private Integer branchPk;
    private Integer classroomPk;
    private String searchGroupName;
    private String searchGroupNumber;
    private String searchSchoolFk;
    private String searchSchoolName;
    private String searchSchoolNumber;
    private String searchSchoolType;
    private String searchCity;
    private String activeFlag;
    private String editAction;
    private School currentSchool;
    private Branch currentBranch;
    private List<School> availableList;
    private List<Classroom> availableGroupList;
    private VehicleService vehicleService;
    private SchoolService schoolService;
    private ClassroomService classroomService;
    private PersonSchoolService personSchoolService;
           
    public String edit() throws Exception {
        return SUCCESS;
    }
    
    public String editBranch() throws Exception {
        return SUCCESS;
    }
    
    public String save() throws Exception {
        log.debug("save");
        if (currentSchool.getSchoolPk() != null){
            log.debug("enter save");
            log.debug("a "+  currentSchool.getSchoolPk() + " "+ currentSchool.getSchoolName());
            
            int returnCode = getSchoolService().update(currentSchool);
            if (returnCode > 0){
                log.debug("returnCode "+ returnCode);
                //Won't work cause returning to a redirected action and it becomes lost.
                addActionMessage(currentSchool.getSchoolName() +" Successfully Updated.");
                log.debug("actionMessage set");
                availableList = null;
                return Constants.LIST;
            }
        }else {
            return insert();
        }
        return SUCCESS;
    }
    
    public String insert() throws Exception {
        log.debug("insert");
        int returnCode = getSchoolService().insert(currentSchool);
        //Integer schoolPk = getSdcService().getLastInsertedId();
        //currentSchool.setSchoolPk(schoolPk);
        if (returnCode > 0){
            log.debug("returnCode "+ returnCode);
            //Won't work cause returning to a redirected action and it becomes lost.
            addActionMessage(currentSchool.getSchoolName() +" Successfully Added.");
            log.debug("actionMessage set");
            availableList = null;
            return Constants.LIST;
        }
        addActionError("Error "+currentSchool.getSchoolName() +" Was Not Added.");
        return INPUT; //return Constants.LIST;
    }
    
    public String delete() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("schoolPk",getSchoolPk());
        hm.put("classroomClosed","1");
        hm.put("deleted","1");
        setSchoolPk(null);
        try {
        	/*
        	 * As of Enhancement 7292 we no longer need to delete associated instructors or vehicles.
        	 */
            //delete all associations between an instructor and this school.
            //getPersonSchoolService().delete(hm);
            // delete branch?
            // getSchoolService().deleteBranch(hm);
            
            //delete vehicles
            //getVehicleService().delete(hm);
            
            //close classrooms associated with this school.
            getClassroomService().update(hm);

            //update school with deleted column instead of delete for audit purposes.
            getSchoolService().delete(hm);
           	addActionMessage("School Inactivated");
           	availableList = null;
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.school",ex.getMessage() ) );
        }
        return SUCCESS;
    }

    public String undelete() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("schoolPk",getSchoolPk());
        hm.put("classroomClosed","0");
        hm.put("deleted","0");
        try {
            getSchoolService().delete(hm);
            //open classrooms associated with this school.
            getClassroomService().update(hm);
            addActionMessage("School Activated");
            availableList = null;
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.school",ex.getMessage() ) );
        }
        return SUCCESS;
    }

    public String saveBranch() throws Exception {
        log.debug("saveBranch");
        if (currentBranch.getBranchPk() != null){
            log.debug("school "+  getSchoolPk() + " branch "+ getBranchPk());
            if (currentBranch.getSchoolFk() == null) {
                currentBranch.setSchoolFk(getSchoolPk());
            }
            int returnCode = getSchoolService().updateBranch(currentBranch);
            if (returnCode > 0){
                log.debug("returnCode "+ returnCode);
                addActionMessage(currentBranch.getCity() +" Branch Successfully Updated");
                log.debug("actionMessage set");
            }else{
            	return INPUT;
            }
        }else {
            return insertBranch();
        }
        return SUCCESS;
    }
    
    public String insertBranch() throws Exception {
        log.debug("insertBranch");
        if (currentBranch.getSchoolFk() == null) {
        	currentBranch.setSchoolFk(getSchoolPk());
        }
        int returnCode = getSchoolService().insertBranch(currentBranch);
        if (returnCode > 0){
            log.debug("returnCode "+ returnCode);
            addActionMessage(currentSchool.getCity() +" Branch Successfully Added");
            log.debug("actionMessage set");
        }else{
        	addActionError("Error: "+currentSchool.getCity() +" Branch Was Not Added");
        	return INPUT;
        }
        return SUCCESS;
    }
    
    public String deleteBranch() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("branchPk",getBranchPk());
        setBranchPk(null);
        try {
            int ret = getSchoolService().deleteBranch(hm);
            addActionMessage("Branch Deleted");
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.school",ex.getMessage() ) );
        }
        return SUCCESS;
    }
    
    public Collection getBranchList() throws Exception {
        SchoolService ps = new SchoolService();
        Collection b = new ArrayList();
        try {
            log.debug("branchList "+currentSchool.getSchoolPk());
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("schoolFk",currentSchool.getSchoolPk());
            b = ps.selectBranch(hm);
            
        } catch (DaoException de) {
            log.error("DAOEXCEPTION",de);
        }
        
        return b;
    }
    
    public String doSearch() throws Exception {
        log.debug("doSearch");
    	if (activeFlag == null) {
    		activeFlag = "0";
    	}
        return SUCCESS;
    }

    public String doList() throws Exception {
        log.debug("doList");
        return SUCCESS;
    }
    
    public Collection<School> getAvailableList() throws Exception {
    	if (availableList == null) {
    		availableList = new ArrayList<School>();
    		SchoolService ps = new SchoolService();
    		Map<String, String> hm = new HashMap<String, String>();
        
    		if (searchSchoolType != null && !"".equals(searchSchoolType)) {
    			hm.put("schoolType", searchSchoolType);
    		}
    		if (searchSchoolName != null && !"".equals(searchSchoolName)) {
    			hm.put("schoolName", searchSchoolName.trim().toUpperCase()+'%');
    		}
    		if (searchSchoolNumber != null && !"".equals(searchSchoolNumber)) {
    			hm.put("schoolNumber", searchSchoolNumber.trim().toUpperCase()+'%');
    		}
    		if (searchCity != null && !"".equals(searchCity)) {
    			hm.put("city", searchCity.toUpperCase().trim()+'%');
    		}
    		if (activeFlag != null && !"".equals(activeFlag)) {
    			hm.put("deleted", activeFlag);
    		}
    		try {
    			availableList = ps.searchBeginsWith(hm);
    		} catch (DaoException de) {
    			log.error("DAOEXCEPTION",de);
    		}
    	}
        
        return availableList;
    }

    public String doSearchGroup() throws Exception {
        log.debug("doSearchGroup");
        if (activeFlag == null) {
        	activeFlag = "0";
        }
        return SUCCESS;
    }

    public String doListGroup() throws Exception {
        log.debug("doListGroup");
        return SUCCESS;
    }

    public Collection getAvailableGroupList() throws Exception {
        log.debug("getAvailableGroupList");
        if (availableGroupList == null) {
        	ClassroomService ps = new ClassroomService(getUmdLogonEmail());
        	Map<String,Object> hm = new HashMap<String,Object>();
        	if (searchGroupName != null && !"".equals(searchGroupName.trim())) {
        		hm.put("groupName", searchGroupName.trim().toUpperCase()+'%');
        	}
        	if (searchSchoolFk != null && !"".equals(searchSchoolFk)) {
        		hm.put("schoolFk", searchSchoolFk);
        	}
        	if (activeFlag != null && !"".equals(activeFlag)) {
        		hm.put("closed", activeFlag);
        	}
            availableGroupList = ps.classroomSearchBeginsWith(hm);
        }
        return availableGroupList;
    }

    public String deleteGroup() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("classroomPk",getClassroomPk());
        hm.put("closed","1");
        setClassroomPk(null);
        try {
            int ret = getClassroomService().delete(hm);
            if (ret > 0) {
                addActionMessage("Group Inactivated");
                availableGroupList = null;
            }
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.school",ex.getMessage() ) );
        }
        return SUCCESS;
    }

    public String undeleteGroup() throws Exception {
        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("classroomPk",getClassroomPk());
        hm.put("closed","0");
        setClassroomPk(null);
        try {
            int ret = getClassroomService().delete(hm);
            if (ret > 0) {
                addActionMessage("Group Activated");
            	availableGroupList = null;
            }
        } catch (DaoException ex) {
            log.debug("actionError", ex);
            addActionError(getText("error.dao.school",ex.getMessage() ) );
        }
        return SUCCESS;
    }
    
    public void prepare() throws Exception {
        if (getCurrentSchool() == null){
            School preFetched = fetch(getSchoolPk());
            if (preFetched != null) {
                setCurrentSchool(preFetched);
            }
        }
        
        if(getCurrentBranch() == null){
            if (getBranchPk() != null) {
                Branch branch = fetchBranch(getBranchPk());
                if (branch != null){
                    setCurrentBranch(branch);
                }
            }
        }       
    }
    
     public Branch fetchBranch(Integer tryId) throws DaoException {
        log.debug("enter fetchBranch for "+tryId);
        Branch result = null;
        if (tryId != null) {
            Map<String,Object>hm = new HashMap<String,Object>();
            hm.put("branchPk",tryId);
            List list = getSchoolService().selectBranch(hm);
            result = (Branch) list.get(0);
        } else {
            result = new Branch();
        }
        log.debug("exit fetchBranch for "+tryId);
        return result;
    }
    
    public School fetch(Integer tryId) throws DaoException {
        log.debug("enter fetch for "+tryId);
        School result = null;
        if (tryId != null) {
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("schoolPk",tryId);
            result = getSchoolService().getSchool(hm);
        } else {
            result = new School();
        }
        log.debug("exit fetch for "+tryId);
        return result;
    }
    
    public Integer getSchoolPk() {
        return schoolPk;
    }
    
    public void setSchoolPk(Integer schoolPk) {
        this.schoolPk = schoolPk;
    }
    
    public SchoolService getSchoolService() {
        if(schoolService == null){
            setSchoolService(new SchoolService(getUmdLogonEmail()));
        }
        return schoolService;
    }
    
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
    public PersonSchoolService getPersonSchoolService() {
        if(personSchoolService == null){
            setPersonSchoolService(new PersonSchoolService(getUmdLogonEmail()));
        }
        return personSchoolService;
    }
    
    public void setPersonSchoolService(PersonSchoolService personSchoolService) {
        this.personSchoolService = personSchoolService;
    }
    
    public School getCurrentSchool() {
        return currentSchool;
    }
    
    public void setCurrentSchool(School currentSchool) {
        this.currentSchool = currentSchool;
    }
    
    public Object getModel() {
        return currentSchool;
    }
    
    public ClassroomService getClassroomService() {
        if(classroomService == null){
            setClassroomService(new ClassroomService(getUmdLogonEmail()));
        }
        return classroomService;
    }
    
    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    public Integer getBranchPk() {
        return branchPk;
    }

    public void setBranchPk(Integer branchPk) {
        this.branchPk = branchPk;
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(Branch currentBranch) {
        this.currentBranch = currentBranch;
    }
    
    
    @Override
    public String getUpdatedEmail() {
        return super.getUpdatedEmail();
    }
     public VehicleService getVehicleService() {
        if(vehicleService == null){
            setVehicleService(new VehicleService(getUmdLogonEmail()));
        }
        return vehicleService;
    }
    
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Integer getClassroomPk() {
        return classroomPk;
    }

    public void setClassroomPk(Integer classroomPk) {
        this.classroomPk = classroomPk;
    }

	public String getSearchGroupName() {
		return searchGroupName;
	}

	public void setSearchGroupName(String searchGroupName) {
		this.searchGroupName = searchGroupName;
	}

	public String getSearchGroupNumber() {
		return searchGroupNumber;
	}

	public void setSearchGroupNumber(String searchGroupNumber) {
		this.searchGroupNumber = searchGroupNumber;
	}

	public String getSearchSchoolFk() {
		return searchSchoolFk;
	}

	public void setSearchSchoolFk(String searchSchoolFk) {
		this.searchSchoolFk = searchSchoolFk;
	}

	public String getSearchSchoolName() {
		return searchSchoolName;
	}

	public void setSearchSchoolName(String searchSchoolName) {
		this.searchSchoolName = searchSchoolName;
	}

	public String getSearchSchoolNumber() {
		return searchSchoolNumber;
	}

	public void setSearchSchoolNumber(String searchSchoolNumber) {
		this.searchSchoolNumber = searchSchoolNumber;
	}

	public String getSearchSchoolType() {
		return searchSchoolType;
	}

	public void setSearchSchoolType(String searchSchoolType) {
		this.searchSchoolType = searchSchoolType;
	}

	public String getSearchCity() {
		return searchCity;
	}

	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getEditAction() {
		return editAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}

}

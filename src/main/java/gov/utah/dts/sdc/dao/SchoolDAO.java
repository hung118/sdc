package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.School;
import java.util.List;

@SuppressWarnings("rawtypes")
public class SchoolDAO extends BaseDAO {

    private static final String selectStatement = "schoolSearchEquals";
    private static final String equalsCountStatement = "schoolEqualsCount";
    private static final String searchEqualsStatement = "schoolSearchEquals";
    private static final String searchBeginsWithStatement = "schoolSearchBeginsWith";
    private static final String insertStatement = "schoolInsert";
    private static final String updateStatement = "schoolUpdate";
    private static final String deleteStatement = "schoolDelete";
    private static final String userLabelValueStatement = "schoolLabelValue";
    private static final String userLabelValueStatement2 = "schoolLabelValue2";
    private static final String allLabelValueStatement = "allSchoolLabelValue";
    private static final String allLabelValueStatement2 = "allSchoolLabelValue2";
    private static final String completionTestsStatement = "completionTests";
    private static final String branchInsertStatement = "branchInsert";
    private static final String branchUpdateStatement = "branchUpdate";
    private static final String branchDeleteStatement = "branchDelete";
    private static final String branchEqualsCountStatement = "branchEqualsCount";
    private static final String branchSelectStatement = "branchSearchEquals";

    public SchoolDAO() {
    	
    }
    
    public SchoolDAO(String userId) {
    	super.userId = userId;
    }
    
    public java.lang.Integer getEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(equalsCountStatement, parameterObject);
    }

    public List searchEquals(Object parameterObject) throws DaoException {
        return getBaseList(searchEqualsStatement, parameterObject);
    }

    public List searchBeginsWith(Object parameterObject) throws DaoException {
        return getBaseList(searchBeginsWithStatement, parameterObject);
    }

    public List getSchoolList(Object parameterObject) throws DaoException {
        return getBaseList(searchEqualsStatement, parameterObject);
    }

    public School getSchool(Object tryId) throws DaoException {
        List list = getBaseList(selectStatement, tryId);
        if (list.isEmpty()) {
            return new School();
        } else {
            return (School) list.get(0);
        }
    }

    public int insert(Object parameterObject) throws DaoException {
        return baseUpdate(insertStatement, parameterObject);
    }

    public int update(Object parameterObject) throws DaoException {
        return baseUpdate(updateStatement, parameterObject);
    }

    public int delete(Object parameterObject) throws DaoException {
        return baseUpdate(deleteStatement, parameterObject);
    }

    public List getUserSchoolLabelValue(Object parameterObject) throws DaoException {
        return getBaseList(userLabelValueStatement, parameterObject);
    }

	public List getUserSchoolLabelValue2(Object parameterObject) throws DaoException {
        return getBaseList(userLabelValueStatement2, parameterObject);
    }
    
    public List getAllSchoolLabelValue(Object parameterObject) throws DaoException {
        return getBaseList(allLabelValueStatement, parameterObject);
    }

    public List getAllSchoolLabelValue2(Object parameterObject) throws DaoException {
        return getBaseList(allLabelValueStatement2, parameterObject);
    }
    
    public List getCompletionTests(Object parameterObject) throws DaoException {
        return getBaseList(completionTestsStatement, parameterObject);
    }

    public java.lang.Integer getBranchEqualsCount(Object parameterObject) throws DaoException {
        return (Integer) getBaseObject(branchEqualsCountStatement, parameterObject);
    }

    public List selectBranch(Object parameterObject) throws DaoException {
        return getBaseList(branchSelectStatement, parameterObject);
    }

    public int insertBranch(Object parameterObject) throws DaoException {
        return baseUpdate(branchInsertStatement, parameterObject);
    }

    public int updateBranch(Object parameterObject) throws DaoException {
        return baseUpdate(branchUpdateStatement, parameterObject);
    }

    public int deleteBranch(Object parameterObject) throws DaoException {
        return baseUpdate(branchDeleteStatement, parameterObject);
    }
}

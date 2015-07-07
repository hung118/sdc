package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.Classroom;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class ClassroomDAO extends BaseDAO {
	private static final String selectStatement = "classroomSearchEquals";
	private static final String equalsCountStatement = "classroomEqualsCount";
	private static final String searchBeginsWithStatement = "classroomSearchBeginsWith";
	private static final String searchEqualsStatement = "classroomSearchEquals";
	private static final String insertStatement = "classroomInsert";
	private static final String updateStatement = "classroomUpdate";
	private static final String deleteStatement = "classroomDelete";
	private static final String userLabelValueStatement = "classroomLabelValue";
	private static final String allLabelValueStatement = "allClassroomLabelValue";
	private static final String removeStudentStatement = "classroomStudentRemove";
	private static final String addStudentStatement = "classroomStudentAdd";
	private static final String selectSchoolStatement = "classroomSchoolSearch";
	private static final String studentNumberStatement = "classroomStudentNumber";
	private static final String selectStudentStatement = "classroomStudentSearch";
	private static final String selectBranchStatement = "getBranchList";
	private static final String hideStudentStatement = "hideClassroomStudent";

	protected static Log log = LogFactory.getLog(ClassroomDAO.class);

	public ClassroomDAO() {
		
	}
	
	public ClassroomDAO(String userId) {
		super.userId = userId;
	}
	
	public java.lang.Integer getEqualsCount(Object parameterObject)
			throws DaoException {
		return (Integer) getBaseObject(equalsCountStatement, parameterObject);
	}

	public List searchEquals(Object parameterObject) throws DaoException {
		return getBaseList(searchEqualsStatement, parameterObject);
	}

	public List getClassroomList(Object parameterObject) throws DaoException {
		return getBaseList(searchEqualsStatement, parameterObject);
	}

	public List classroomSearchBeginsWith(Object parameterObject)
			throws DaoException {
		return getBaseList(searchBeginsWithStatement, parameterObject);
	}

	public List getBranchList(Object parameterObject) throws DaoException {
		return getBaseList(selectBranchStatement, parameterObject);
	}

	public Classroom getClassroom(Object tryId) throws DaoException {
		List list = getBaseList(selectStatement, tryId);
		if (list.isEmpty()) {
			return new Classroom();
		} else {
			return (Classroom) list.get(0);
		}
	}

	public Classroom getSchoolInfo(Object tryId) throws DaoException {
		List list = getBaseList(selectSchoolStatement, tryId);
		if (list.isEmpty()) {
			return new Classroom();
		} else {
			return (Classroom) list.get(0);
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

	public List getUserClassroomLabelValue(Object parameterObject)
			throws DaoException {
		return getBaseList(userLabelValueStatement, parameterObject);
	}

	public List getAllClassroomLabelValue(Object parameterObject)
			throws DaoException {
		return getBaseList(allLabelValueStatement, parameterObject);
	}

	public int removeStudent(Object parameterObject) throws DaoException {
		return baseUpdate(removeStudentStatement, parameterObject);
	}

	public int addStudent(Object parameterObject) throws DaoException {
		return baseUpdate(addStudentStatement, parameterObject);
	}

	public java.lang.Long getStudentNumber(Object parameterObject)
			throws DaoException {
		return (Long) getBaseObject(studentNumberStatement, parameterObject);
	}

	public List getSchoolInfoByStudent(Object tryId) throws DaoException {
		return getBaseList(selectStudentStatement, tryId);
	}

	public int hideClassroomStudent(Object parameterObject) throws DaoException {
		return baseUpdate(hideStudentStatement, parameterObject);
	}
}

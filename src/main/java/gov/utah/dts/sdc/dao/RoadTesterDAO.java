package gov.utah.dts.sdc.dao;

import gov.utah.dts.sdc.model.LabelValue;
import java.util.List;

public class RoadTesterDAO extends BaseDAO {

    private static final String roadTestersLabelValueStatement = "selectRoadTestersLabelValue";
    private static final String schoolsLabelValueStatement = "selectSchoolLabelValue";

    @SuppressWarnings("unchecked")
	public List<LabelValue> getRoadTestersLabelValue(Object parameterObject) throws DaoException {
        return getBaseList(roadTestersLabelValueStatement, parameterObject);
    }

    @SuppressWarnings("unchecked")
    public List getSchoolsLabelValue(Object parameterObject) throws DaoException {
        return getBaseList(schoolsLabelValueStatement, parameterObject);
    }


}

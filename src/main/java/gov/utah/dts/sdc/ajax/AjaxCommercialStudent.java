package gov.utah.dts.sdc.ajax;

import gov.utah.dts.sdc.dao.DaoException;
import gov.utah.dts.sdc.service.ClassroomService;
import org.directwebremoting.util.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AjaxCommercialStudent {

    private static final Logger log = Logger.getLogger(AjaxCommercialStudent.class);

    public Collection getAllClassroom() {
        Collection c = new ArrayList();
        try {
            ClassroomService ps = new ClassroomService();
            log.debug("getAllClassroom");
            c = ps.getClassroomList(null);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    public void addStudent(Integer instructorFk) {
        try {
            ClassroomService ps = new ClassroomService();
            log.debug("Adding classroom for instructor: " + instructorFk);
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("instructorFk", instructorFk);
            ps.insert(hm);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteClassroom(Integer classroomPk) {
        try {
            ClassroomService ps = new ClassroomService();
            log.debug("Removing classroom: " + classroomPk);
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("classroomPk", classroomPk);
            ps.delete(hm);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
    }
}

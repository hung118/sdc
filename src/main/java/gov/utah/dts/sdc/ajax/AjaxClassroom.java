package gov.utah.dts.sdc.ajax;

import gov.utah.dts.sdc.dao.DaoException;
import org.directwebremoting.util.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AjaxClassroom {

    private static final Logger log = Logger.getLogger(AjaxClassroom.class);

    public Collection getAllClassroom(Integer schoolFk) {
        Collection c = new ArrayList();
        try {
            gov.utah.dts.sdc.service.ClassroomService ps = new gov.utah.dts.sdc.service.ClassroomService();
            log.debug(" getAllClassroom");
            log.debug(" schoolFk = "+schoolFk);
            Map<String,Object> hm = new HashMap<String,Object>();
            hm.put("schoolFk",schoolFk);
            hm.put("classroomClosed",new Integer(0)); //means not closed.
            c = ps.getClassroomList(hm);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    public void addClassroom(Integer instructorFk) {
        try {
            gov.utah.dts.sdc.service.ClassroomService ps = new gov.utah.dts.sdc.service.ClassroomService();
            log.debug("Adding classroom for instructor: " + instructorFk);
            java.util.Map<String,Object> hm = new java.util.HashMap<String,Object>();
            hm.put("instructorFk", instructorFk);
            int insert = ps.insert(hm);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteClassroom(Integer classroomPk) {
        try {
            gov.utah.dts.sdc.service.ClassroomService ps = new gov.utah.dts.sdc.service.ClassroomService();
            log.debug("Removing classroom: " + classroomPk);
            java.util.Map<String,Object> hm = new java.util.HashMap<String,Object>();
            hm.put("classroomPk", classroomPk);
            int removeStudent = ps.removeStudent(hm);
            int delete = ps.delete(hm);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
    }
    
    public void closeClassroom(Integer classroomPk) {
        try {
            gov.utah.dts.sdc.service.ClassroomService ps = new gov.utah.dts.sdc.service.ClassroomService();
            log.debug("Closing classroom: " + classroomPk);
            java.util.Map<String,Object> hm = new java.util.HashMap<String,Object>();
            hm.put("classroomPk", classroomPk);
            hm.put("classroomClosed",new Integer(1)); //means not closed.
            int update = ps.update(hm);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
    }
}

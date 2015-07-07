package gov.utah.dts.util;

import gov.utah.dts.alm.webservice.model.LogData_Type;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.xml.datatype.XMLGregorianCalendar;
import org.displaytag.decorator.TableDecorator;

/**
 * Decorator class for display tag: <%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
 * 
 * @author HNGUYEN
 *
 */
public class DisplayDecorator extends TableDecorator {

	public String getLogDate() {
		
		LogData_Type logData_Type = (LogData_Type)getCurrentRowObject();
		XMLGregorianCalendar xLogDate = logData_Type.getLogDate();
		GregorianCalendar gLogDate = xLogDate.toGregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		return sdf.format(gLogDate.getTime());
	}
}

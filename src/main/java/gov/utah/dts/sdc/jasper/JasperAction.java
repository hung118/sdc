/*
 * JasperAction.java
 *
 * Created on April 27, 2007, 12:37 PM
 *
 */

package gov.utah.dts.sdc.jasper;

/**
 *
 * @author CHWARDLE
 */
import com.opensymphony.xwork2.ActionSupport;
import gov.utah.dts.sdc.model.Person;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;


public class JasperAction extends ActionSupport {

	//basic List - it will serve as our dataSource later on
	private List myList;

	public String execute() throws Exception {

		// create some imaginary persons
		Person p1 = new Person(new Integer(1), "Patrick", "Lightbuddie");
		Person p2 = new Person(new Integer(2), "Jason", "Carrora");
		Person p3 = new Person(new Integer(3), "Alexandru", "Papesco");
		Person p4 = new Person(new Integer(4), "Jay", "Boss");

		/*
		 * store everything in a list - normally, this should be coming from a
		 * database but for the sake of simplicity, I left that out
		 */
		myList = new ArrayList();
		myList.add(p1);
		myList.add(p2);
		myList.add(p3);
		myList.add(p4);

		//if all goes well ..
		return SUCCESS;
	}

	/**
	 * @return Returns the myList.
	 */
	public List getMyList() {
		return myList;
	}

}

package gov.utah.dts.util;

import gov.utah.dts.sdc.jasper.OverlapTimeBean;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

/**
 * Comparator sorts 4 columns (firstName, middleName, lastName, and startTime) in a collection of beans (OverlapTimeBean).
 * 
 * @author HNGUYEN
 *
 */
public class CollectionComparator implements Comparator {
	
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		int result = -1;
		
		OverlapTimeBean bean1 = (OverlapTimeBean)o1;
		OverlapTimeBean bean2 = (OverlapTimeBean)o2;
		
		StringBuffer fullName1 = new StringBuffer(bean1.getFirstName() == null ? " " : bean1.getFirstName())
		.append(bean1.getMiddleName() == null ? " " : bean1.getMiddleName())
		.append(bean1.getLastName() == null ? " " : bean1.getLastName());
			
		StringBuffer fullName2 = new StringBuffer(bean2.getFirstName() == null ? " " : bean2.getFirstName())
		.append(bean2.getMiddleName() == null ? " " : bean2.getMiddleName())
		.append(bean2.getLastName() == null ? " " : bean2.getLastName());
		
		Collator collator = Collator.getInstance();
		CollationKey[] keys = new CollationKey[2];
		keys[0] = collator.getCollationKey(fullName1.toString());
		keys[1] = collator.getCollationKey(fullName2.toString());
		
		result = keys[0].compareTo(keys[1]);	// compare names
		
		if (result == 0) {	// when names equals, compare startTime
			result =  compareLong(bean1.getStartTime(), bean2.getStartTime());
		}
		
		return result;
	}
	
	private int compareLong(long num1, long num2) {
		
		int result;
		if (num1 < num2) {
			result = -1;
		} else if (num1 ==  num2) {
			result = 0;
		} else {
			result = 1;
		}
		
		return result;
	}
}

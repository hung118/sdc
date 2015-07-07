package gov.utah.dts.sdc.jasper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for JasperReports.
 * 
 * @author HNGUYEN
 *
 */
public class Util {

    /** The format pattern used to format Strings as currency Strings.
     **/
    private static final String CURRENCY_PATTERN = "$#,##0.00;(#)";
        
	/**
	 * Concat address1, address2, city, state, and zip fields into one single formatted string
	 */
	public static String concat(String address1, String address2, String city, String state, String zip) {
		
		StringBuffer retStr = new StringBuffer();
		retStr.append(address1 == null ? "" : address1)
		.append(address2 == null ? "" : ", " + address2)
		.append(city == null ? "" : ", " + city)
		.append(state == null ? "" : ", " + state)
		.append(zip == null ? "" : ", " + zip);
		
		return retStr.toString();
	}
	
	/**
	 * Format to currency.
	 */
    public static String formatCurrency(String str) {
        double d = Double.parseDouble(str);
        
        DecimalFormat df = new DecimalFormat(CURRENCY_PATTERN);
        return df.format(d);
    }
    
    /** Formats the specified Date using the specified format pattern. For more information on
     ** format patterns, refer to the documentation for the SimpleDateFormat class.
     **
     ** @param dt the Date to be formatted
     ** @param pattern the format pattern to be used to format the Date
     ** @return the formatted String representation of the Date
     ** @see java.text.SimpleDateFormat
     **/
    public static String formatDate(Date dt, String pattern) {
    	
       SimpleDateFormat df = new SimpleDateFormat(pattern);
       return df.format(dt);
    }
    
    public static String concatName(String firstName, String middleName, String lastName) {

    	if (firstName == null && middleName == null & lastName == null) {
    		return null;
    	}
    	
    	String fullName = null;
    	if (middleName == null) {
    		fullName = firstName + " " + lastName;
    	} else {
    		fullName = firstName + " " + middleName + " " + lastName;
    	}

    	return fullName;
    }
    
    public static String getByType(String byType) {
    	String ret = "";
    	switch (byType) {
    	case "1":
    		ret = "Behind-The-Wheel"; 
    		break;
    	case "2":
    		ret = "Observation";
    		break;
    	case "3":
    		ret = "Classroom";
    	}
    	
    	return ret;
    }
}

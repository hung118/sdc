package gov.utah.dts.sdc.actions;

import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.actions.SDCSupport;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.service.PersonService;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class LoginAction extends SDCSupport {

	private static final long serialVersionUID = 4933735289681575454L;
    private String contextPath;
    protected static Log log = LogFactory.getLog(LoginAction.class);

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() {
        log.info("LoginAction execute...");
        PersonService service = new PersonService();

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("dn", (String) getSession().get(Constants.USER_DN));
        Integer cnt = new Integer(0);

        try {
            log.info("LoginAction searching " + map.get("dn"));
            if (map.get("dn") != null) {
                cnt = service.getEqualsCount(map);
                log.debug("step 2");
            }
            if (cnt.intValue() == 1) {
                /*Match found containing authenticated users DN
                 *forward to the success page.
                 */
                List list = service.searchEquals(map);
                log.info("LoginAction (DN) Match Found " + list.size());
                Person person = (Person) list.get(0);
                getSession().put(Constants.USER_KEY, person);
            } else {
                map.clear();
                if (getSession().get(Constants.USER_EMAIL) != null) {
                    map.put("email", getSession().get(Constants.USER_EMAIL).toString().toUpperCase());
                    cnt = service.getEqualsCount(map);
                }

                if (cnt.intValue() == 1) {
                    /*A email match was found in the DB for further
                     *identificaiton purposes forward to the registration
                     *page and request additional information
                     */
                    List list = service.searchEquals(map);
                    log.info("LoginAction (Email) match Found " + list.size());
                    Person person = (Person) list.get(0);
                    person = setUMDValues(person);
                    getSession().put(Constants.USER_KEY, person);
                    return Constants.REGISTER_MERGE;
                } else if (cnt.intValue() < 1) {
                    log.info("No match found ");
                    Person person = setUMDValues();
                    getSession().put(Constants.USER_KEY, person);
                    return Constants.REGISTER;
                }
            }
        } catch (Exception e) {
            log.error("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR", e);
            e.printStackTrace();
            addActionError(e.getMessage());
        }

        if (hasErrors()) {
            return ERROR;
        } else {
            return SUCCESS;
        }
    }

    public String getContextPath() {
        return contextPath;
    }

    private Person setUMDValues() {
        Person person = new Person();
        return setUMDValues(person);
    }
    
    private Person setUMDValues(Person person) {
        person.setDn((String) getSession().get(Constants.USER_DN));
        person.setEmail((String) getSession().get(Constants.USER_EMAIL));
        person.setUpdatedBy(person.getEmail());
        //Make states fields default to UT...
        person.setDriversLicenseState("UT");
        person.setState("UT");
        person.setFirstName((String) getSession().get(Constants.USER_FirstName));
        person.setLastName((String) getSession().get(Constants.USER_LastName));
        person.setMiddleName((String) getSession().get(Constants.USER_MiddleName));
        person.setAddress1((String) getSession().get(Constants.USER_HomeAddress));
        person.setCity((String) getSession().get(Constants.USER_HomeCity));
        if (getSession().get(Constants.USER_HomeState) != null) {
            person.setState((String) getSession().get(Constants.USER_HomeState));
        }
        person.setZip((String) getSession().get(Constants.USER_HomeZip));
        person.setBusinessPhone((String) getSession().get(Constants.USER_WorkPhone));
        person.setHomePhone((String) getSession().get(Constants.USER_HomePhone));
        return person;
    }
}

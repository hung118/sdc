package gov.utah.dts.sdc.actions;

import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.sdc.Constants;
import gov.utah.dts.sdc.model.Person;
import gov.utah.dts.sdc.service.PersonService;
import gov.utah.dts.util.SendMail;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

public final class RegisterAction extends SDCSupport implements Preparable, ServletRequestAware {

    protected static Log log = LogFactory.getLog(RegisterAction.class);

    HttpServletRequest req;
    private Person person;
    private String notes;
    private PersonService personService;

    @Override
    public String execute() {
        log.debug("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        return SUCCESS;
    }

    public void setServletRequest(HttpServletRequest req) {
        this.req = req;
    }

    private HttpServletRequest getServletRequest() {
        return req;
    }

    public String merge() throws Exception {
        return Constants.REGISTER_MERGE;
    }

    @Override
    public String doDefault() throws Exception {
        return SUCCESS;
    }

    public void prepare() throws Exception {
        HttpSession ses = req.getSession();
        log.debug("prepare");
        setPerson((Person) ses.getAttribute(Constants.USER_KEY));
    }

    public String save() throws Exception {
        log.debug("save");
        if (person.getPersonPk() == null) {
            log.debug("goto insert");
            return insert();
        } else {
            log.debug("goto update");
            return update();
        }
    }

    public String insert() throws Exception {
        log.debug("insert");
        int code = getPersonService().insert(person);
        log.debug("insert returned Code" + code);
        sendEmail(person.getEmail(), Constants.Email_Subject, createEmailMessage(getNotes()));
        return SUCCESS;
    }

    public String update() throws Exception {
        log.debug("update");
        int code = getPersonService().update(person);
        log.debug("" + code);
        return SUCCESS;
    }


    public PersonService getPersonService() {
        if (personService == null) {
            setPersonService(new PersonService());
        }
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public Person getPerson() {
        log.debug("getPerson");
        return person;
    }

    public void setPerson(Person person) {
        log.debug("setPerson");
        this.person = person;
    }

    public String createEmailMessage(String notes) throws Exception {
        log.debug("enter createEmailMessage");
        StringBuffer msg = new StringBuffer();
        msg.append(System.getProperty("line.separator"));
        msg.append(person.getFullName());
        msg.append(" (" + person.getEmail() + ") has requested access to SDC.");

        if (notes != null || !notes.equals("")) {
            msg.append(System.getProperty("line.separator"));
            msg.append(System.getProperty("line.separator"));
            msg.append("User Notes:");
            msg.append(System.getProperty("line.separator"));
            msg.append(notes);
        }

        if (person.getBusinessPhone() != null || person.getHomePhone() != null) {
            msg.append(System.getProperty("line.separator"));
            msg.append(System.getProperty("line.separator"));
            msg.append("Additional Contact Information");
            if (person.getBusinessPhone() != null && !person.getBusinessPhone().equals("")) {
                msg.append(System.getProperty("line.separator"));
                msg.append("Phone Number " + person.getBusinessPhone());
                msg.append(System.getProperty("line.separator"));
            }
            if (person.getHomePhone() != null && !(person.getHomePhone().equals(""))) {
                msg.append(System.getProperty("line.separator"));
                msg.append("Alt Phone Number " + person.getHomePhone());
                msg.append(System.getProperty("line.separator"));
            }
        }
        log.debug("exit createEmailMessage");
        return msg.toString();
    }

    public void sendEmail(String from, String subj, String msg) throws Exception {
        log.debug("enter sendEmail");
        SendMail s = new SendMail();
        s.setMailFrom(from);
        s.setMailSubject(subj);
        log.debug("sendEmail message = " + msg);
        s.setMailMessage(msg);
        s.send();
        log.debug("exit sendEmail");
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getUpdatedEmail() {
        return super.getUpdatedEmail();
    }
}

package gov.utah.dts.util;

import gov.utah.dts.sdc.Constants;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendMail {
    
    protected static Log log = LogFactory.getLog(SendMail.class);
    private String host;
    private String subject;
    private String fromMail;
    private String toName;
    private String toAddress;
    private String mess;
   
    // constructor
    public SendMail() {
  //  Email_Activated="true";
        setMailHost(Constants.Email_SMTP);
        setMailFrom(Constants.Email_From);
        addMailTo(Constants.Email_To,Constants.Email_To);
    }
    
    // setter methods
    public void setMailHost(String host) {
        this.host = host;
    }
    
    public void setMailSubject(String sub) {
        this.subject = sub;
    }
    
    public void setMailFrom(String fromMail) {
        this.fromMail = fromMail;
    }
    
    public void addMailTo(String toName, String toAddress) {
        this.toName = toName;
        this.toAddress = toAddress;
    }
    
    public void setMailMessage(String mess) {
        this.mess = mess;
    }
    
    // this sends the email and check for success and returns results
    public boolean send() {
        boolean sent = false;
        log.info("Email is "+Constants.Email_Activated);
        if (Boolean.parseBoolean(Constants.Email_Activated)) {
            // set the host
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            
            // create some properties and get the default Session
            Session session = Session.getDefaultInstance(props, null);
                        
            try {
                // set the from
                InternetAddress from = new InternetAddress(fromMail);
                InternetAddress address = new InternetAddress(toAddress);

                // Define message
                MimeMessage message = new MimeMessage(session);
                message.setFrom(from);
                message.addRecipient(Message.RecipientType.TO, address);
                message.setSubject(subject);
                
               // Fill the message
                message.setText(mess);
                // send it
                Transport.send(message);
                sent = true;
            } catch (MessagingException mex) {
                sent = false;
                log.error("MessagingException",mex);
            } catch (Exception ex) {
                log.error("error sending email to " + toName
                + " Caught exception in MailSender.ms_send: ",ex);
            }
        }else {
            log.warn("email is off");
        }
        return sent;
    }
}

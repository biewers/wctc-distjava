package edu.wctc.web.demo.bookwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author jlombardo
 */
@Service
public class MailService {
    @Autowired // required by Spring to inject object from Spring config file
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;
    
    public void sendMessage(String emailAddress) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(emailAddress);
        msg.setText("This is a message");
        try {
            mailSender.send(msg);
        } catch(NullPointerException npe) {
            throw new MailSendException(
               "Email send error from EmailVerificationSender");
        }
    }
}

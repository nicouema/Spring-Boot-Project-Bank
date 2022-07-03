package com.nicouema.bank.ports.output.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendGridEmailService implements EmailService{

    private final SendGrid sendGridClient;
    private static final String NO_REPLY_SOMOSMAS_ORG = "no-reply@averyimportantbank.com";

    private void send(Mail mail) {
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("/mail/send");
            request.setBody(mail.build());
            this.sendGridClient.api(request);
        } catch (IOException ex) {
            log.error("Error sending email", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void sendText(String from, String to, String subject, String body) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), new Content("text/plain", body));
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        send(mail);
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), new Content("text/html", body));
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        send(mail);
    }
}

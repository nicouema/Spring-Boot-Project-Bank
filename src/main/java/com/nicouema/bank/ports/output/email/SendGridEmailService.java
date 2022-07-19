package com.nicouema.bank.ports.output.email;

import com.nicouema.bank.domain.model.Branch;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendGridEmailService implements EmailService{

    private final SendGrid sendGridClient;
    private static final String NO_REPLY_AVERYIMPORTANTBANK_COM = "no-reply@averyimportantbank.com";

    @Value("${email.sendgrid.template}")
    private String templateKey;

    @Value("${email.welcome.subject}")
    private String welcomeSubject;

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
        mail.setReplyTo(new Email(NO_REPLY_AVERYIMPORTANTBANK_COM));
        send(mail);
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), new Content("text/html", body));
        mail.setReplyTo(new Email(NO_REPLY_AVERYIMPORTANTBANK_COM));
        send(mail);
    }

    @Override
    public void sendWelcome(String to, Branch branchFrom) {
        Mail mail = new Mail();
        mail.setSubject(this.welcomeSubject);
        mail.setTemplateId(this.templateKey);

        Email from = new Email(branchFrom.getEmail());
        mail.setFrom(from);

        Email replyTO = new Email(NO_REPLY_AVERYIMPORTANTBANK_COM);
        mail.setReplyTo(replyTO);

        Email userEmail = new Email(to);

        Personalization personalization = personalization(userEmail, branchFrom);
        mail.addPersonalization(personalization);

        send(mail);
    }

    private Personalization personalization(Email userEmail, Branch branch) {
        Personalization personalization;
        personalization = new Personalization();
        personalization.addTo(userEmail);
        personalization.addDynamicTemplateData("name", branch.getName());
        personalization.addDynamicTemplateData("email", branch.getEmail());
        return personalization;

    }
}

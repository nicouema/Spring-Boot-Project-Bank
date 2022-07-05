package com.nicouema.bank.ports.output.email;

import com.nicouema.bank.domain.model.Branch;

public interface EmailService {

    void sendText(String from, String to, String subject, String body);

    void sendHTML(String from, String to, String subject, String body);

    void sendWelcome(String to, Branch branchFrom);

}

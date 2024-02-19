package com.send.email.services;

import com.send.email.services.models.CorreoRequest;

import javax.mail.MessagingException;

public interface IEmailService {

    //send standar email
    void sendEmail(CorreoRequest correoRequest) throws MessagingException;
    void confirmationEmail() throws MessagingException;
    void recoveryAccountData() throws  MessagingException;


}

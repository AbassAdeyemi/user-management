package com.assessment.demo.usermanagement.service;

import com.assessment.demo.usermanagement.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendSimpleMessage(String from, String subject, String text);
    void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException;
    void sendDeactivationMail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException;
}

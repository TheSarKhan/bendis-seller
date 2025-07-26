package com.sarkhan.backend.bendisseller.email.service;


import com.sarkhan.backend.bendisseller.email.dto.AppealEmail;
import com.sarkhan.backend.bendisseller.email.dto.ConsultationEmail;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendConsultation(ConsultationEmail consultationEmail) throws MessagingException;
    void sendAppeal(AppealEmail appealEmail) throws MessagingException;
}
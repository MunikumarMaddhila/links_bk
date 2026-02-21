package com.mylinks.auth.service;

import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    public void send(String phone, String message) {
        // Call WhatsApp API (Twilio / Meta)
        System.out.println("WhatsApp → " + phone + ": " + message);
    }
}


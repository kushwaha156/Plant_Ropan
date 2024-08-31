package com.plant.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSService {
	 @Value("${twilio.account.sid}")
	    private String twilioSid;

	    @Value("${twilio.auth.token}")
	    private String twilioAuthToken;

	    @Value("${twilio.phone.number}")
	    private String twilioPhoneNumber;

	    public void sendOTP(String to, String otp) {
	        Twilio.init(twilioSid, twilioAuthToken);
	        Message message = Message.creator(
	                new PhoneNumber(to),
	                new PhoneNumber(twilioPhoneNumber),
	                "Your OTP is: " + otp)
	            .create();

	        System.out.println("Sent message with ID: " + message.getSid());
	    }
}

package com.plant.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPService {

    private final Map<String, String> otpData = new HashMap<>();

    public String generateOTP(String mobileNumber) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(999999));
        System.out.println(" otp ----" + otp);
        otpData.put(mobileNumber, otp);
        return otp;
    }

    public boolean validateOTP(String mobileNumber, String otp) {
        return otp.equals(otpData.get(mobileNumber));
    }

//    public void sendOTP(String mobileNumber, String otp) {
//        // Code to integrate with SMS API (e.g., Twilio)
//        // Twilio SMS sending example:
//       // Message.creator(
//       //     new PhoneNumber(mobileNumber),  // To number
//      //      new PhoneNumber("YourTwilioNumber"),  // From number
//     //       "Your OTP is: " + otp  // Message content
//     //   ).create();
//    }
}

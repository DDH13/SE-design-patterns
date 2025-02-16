package otp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OTPService {
    private List<OTPObserver> observers = new ArrayList<>();
    private String latestOTP;

    public void addObserver(OTPObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OTPObserver observer) {
        observers.remove(observer);
    }

    public void generateOTP() {
        latestOTP = String.valueOf(new Random().nextInt(900000) + 100000); // Generate a 6-digit OTP
        System.out.println("OTP Generated: " + latestOTP);
        notifyObservers();
    }

    private void notifyObservers() {
        for (OTPObserver observer : observers) {
            observer.updateOTP(latestOTP);
        }
    }

    public boolean validateOTP(String otp) {
        boolean result = otp.equals(latestOTP);
        if (result) {
            System.out.println("OTP is valid.");
        } else {
            System.out.println("OTP is invalid.");
        }
        return result;
    }
}

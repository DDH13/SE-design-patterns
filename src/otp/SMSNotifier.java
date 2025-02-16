package otp;

public class SMSNotifier implements OTPObserver {
    @Override
    public void updateOTP(String otp) {
        System.out.println("Sending OTP via SMS: " + otp);
    }
}
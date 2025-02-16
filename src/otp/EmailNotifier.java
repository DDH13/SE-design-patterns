package otp;

public class EmailNotifier implements OTPObserver {
    @Override
    public void updateOTP(String otp) {
        System.out.println("Sending OTP via Email: " + otp);
    }
}
package otp;

public class AuthenticatorAppNotifier implements OTPObserver {
    @Override
    public void updateOTP(String otp) {
        System.out.println("Updating OTP in Authenticator App: " + otp);
    }
}

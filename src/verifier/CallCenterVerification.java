package verifier;

class CallCenterVerification implements VerificationStrategy {
    @Override
    public void verify() {
        System.out.println("Verifying identity via Call Center...");
    }
}

package verifier;

public class VerificationContext {
    private VerificationStrategy strategy;

    public void setStrategy(VerificationStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeVerification() {
        if (strategy == null) {
            System.out.println("No verification method selected!");
        } else {
            strategy.verify();
        }
    }
}

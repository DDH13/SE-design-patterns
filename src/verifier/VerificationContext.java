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


// // Client Code
// public class StrategyPatternExample {
//     public static void main(String[] args) {
//         VerificationContext context = new VerificationContext();

//         // Customer chooses Call Center verification
//         context.setStrategy(new CallCenterVerification());
//         context.executeVerification();

//         // Customer switches to Branch verification
//         context.setStrategy(new BranchVerification());
//         context.executeVerification();
//     }
// }
package user;
public class Customer extends User {
    private String NIC;
    private String passport;
    private String CASAAccountNumber;

    public Customer(String username, String password) {
        super(username, password);
    }
    public Customer() {
        super("default", "default");
    }

    @Override
    public void displayRole() {
        System.out.println("I am a bank customer.");
    }

    public void onboardCustomer() {
    }

}

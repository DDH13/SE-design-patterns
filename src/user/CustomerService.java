package user;

public class CustomerService {
    public void saveCustomer(Customer customer) {
        System.out.println("Customer saved  in DB successfully!");
    }
    public boolean doesUsernameExist(String username) {
        System.out.println("Checking if username exists in DB...");
        return false;
    }
    
}

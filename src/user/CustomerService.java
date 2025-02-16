package user;

import login.LoginService;

public class CustomerService {
    public void saveCustomer(Customer customer) {
        System.out.println("Customer saved  in DB successfully!");
    }
    public boolean doesUsernameExist(String username) {
        System.out.println("Checking if username exists in DB...");
        return false;
    }
    public boolean login(String username, String password, LoginService loginService) {
        System.out.println("Validating user credentials...");
        return loginService.login(username, password);
    }
    
}

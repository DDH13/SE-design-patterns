package user;

import java.sql.Date;

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
    public void lockCustomerAccount(String username) {
        System.out.println("Locking customer account in DB...");
    }
    public void unlockCustomerAccount(String username) {
        System.out.println("Unlocking customer account in DB...");
    }
    public boolean isAccountLocked(String username) {
        System.out.println("Checking if account is locked in DB...");
        return false;
    }
    public Date getLockedTime(String username) {
        System.out.println("Getting locked time from DB...");
        return Date.valueOf("2021-01-01");
    }
    
}

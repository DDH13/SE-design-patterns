package user;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayRole() {
        System.out.println("I am a bank administrator.");
    }
}
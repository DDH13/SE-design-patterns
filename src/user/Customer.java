package user;
public class Customer {
    private String displayName;
    private String username;
    private String password;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}

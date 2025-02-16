package login;

public class RealLoginService implements LoginService {
    public boolean login(String username, String password) {
        System.out.println("User " + username + " logged in successfully.");
        return true;
    }
}

package login;

public class RealLoginService implements LoginService {
    public boolean login(String username, String password) {
        System.out.println("Username and password are valid.");
        return true;
    }
}

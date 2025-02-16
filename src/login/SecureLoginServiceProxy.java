package login;

public class SecureLoginServiceProxy implements LoginService {
    private RealLoginService realLoginService;

    public SecureLoginServiceProxy() {
        this.realLoginService = new RealLoginService();
    }

    public boolean login(String username, String password) {
        // Implement HTTPS check
        if (isSecureConnection()) {
            return realLoginService.login(username, password);
        } else {
            System.out.println("Insecure connection. Login denied.");
            return false;
        }
    }

    private boolean isSecureConnection() {
        return true;  
    }
}

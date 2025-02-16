public class SessionManager {
    private static SessionManager instance;

    SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void createSession(String username) {
        System.out.println("Session created for " + username);
    }

    public void endSession() {
        System.out.println("Session ended.");
    }
}

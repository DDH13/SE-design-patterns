package session;
public class SessionManager {
    private static SessionManager instance;

    private SessionManager() {
        // Private constructor to prevent instantiation
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    private static ThreadLocal<Session> currentSession = new ThreadLocal<>();

    public void createSession(String userId) {
        currentSession.set(new Session(userId));
    }

    public Session getSession() {
        return currentSession.get();
    }

    public void removeSession() {
        currentSession.remove();
    }
}

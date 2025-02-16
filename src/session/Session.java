package session;

public class Session {
    private String userId;
    private long loginTime;

    public Session(String userId) {
        this.userId = userId;
        this.loginTime = System.currentTimeMillis();
    }

    public String getUserId() {
        return userId;
    }

    public long getLoginTime() {
        return loginTime;
    }
}

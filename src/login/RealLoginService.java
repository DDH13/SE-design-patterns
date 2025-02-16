package login;

import logger.MyLogger;

public class RealLoginService implements LoginService {
    private MyLogger logger;

    public RealLoginService() {
        logger = MyLogger.getInstance("file");
    }

    public boolean login(String username, String password) {
        System.out.println("Username and password are valid.");
        logger.logInfo("User " + username + " entered password successfully.");
        return true;
    }
}

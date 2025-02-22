import java.util.Scanner;

import logger.MyLogger;
import login.SecureLoginServiceProxy;
import otp.AuthenticatorAppNotifier;
import otp.EmailNotifier;
import otp.OTPObserver;
import otp.OTPService;
import otp.SMSNotifier;
import session.SessionManager;
import user.CustomerService;

public class LoginFacade {

    private Scanner scanner;
    private CustomerService customerService;
    private OTPService otpService;
    private int otpAttempts;
    private static final int MAX_OTP_ATTEMPTS = 3;
    private MyLogger logger;
    private SessionManager sessionManager;

    public LoginFacade() {
        scanner = new Scanner(System.in);
        customerService = new CustomerService();
        otpService = new OTPService();
        otpAttempts = 0;
        logger = MyLogger.getInstance("file");
        sessionManager = SessionManager.getInstance();
    }

    public boolean loginUser() {
        try {
            // Step 1: Enter username and password
            String username = promptForUsername();
            String password = promptForPassword();

            // Step 2: Validate username and password
            if (!customerService.login(username, password, new SecureLoginServiceProxy())) {
                System.out.println("Invalid username or password. Please try again.");
                return false;
            }

            // Step 3: Trigger 2FA and OTP generation
            System.out.println("Username and password are valid. Proceeding to 2FA...");
            generateAndSendOTP();

            // Step 4: User enters OTP

            // Check if account is locked
            boolean locked = customerService.isAccountLocked(username);
            if (locked && customerService.getLockedTime(username).getTime() > System.currentTimeMillis() - 300000) {
                System.out.println("Account is locked for " + (System.currentTimeMillis() - customerService.getLockedTime(username).getTime()) / 1000
                        + " seconds. Please try again later.");
                return false;
            } else if (locked) {
                customerService.unlockCustomerAccount(username);
            }

            while (otpAttempts < MAX_OTP_ATTEMPTS) {
                String enteredOTP = promptForOTP();
                if (otpService.validateOTP(enteredOTP)) {
                    logger.logInfo("User " + username + " logged in successfully.");
                    sessionManager.createSession(username);
                    System.out.println("Session started for user: " + username);
                    return true;
                } else {
                    logger.logWarn("User " + username + " entered invalid OTP.");
                    otpAttempts++;
                    System.out.println("Invalid OTP. You have " + (MAX_OTP_ATTEMPTS - otpAttempts) + " attempts left.");
                }
            }

            // If max attempts are reached
            System.out.println("Maximum OTP attempts reached. Login failed.");
            customerService.lockCustomerAccount(username);
            logger.logError("User " + username + " exceeded maximum OTP attempts.");
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
        return false;
    }

    private String promptForUsername() {
        System.out.print("Please enter your username: ");
        return scanner.nextLine();
    }

    private String promptForPassword() {
        System.out.print("Please enter your password: ");
        return scanner.nextLine();
    }

    private void generateAndSendOTP() throws Exception {
        System.out.println("Please select OTP delivery method: \n1. SMS\n2. Email\n3. Authenticator App");
        int otpOption = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        if (otpOption < 1 || otpOption > 3) {
            throw new Exception("Invalid OTP option.");
        }

        OTPObserver otpNotifier = createOTPObserver(otpOption);
        otpService.addObserver(otpNotifier);
        otpService.generateOTP();

    }

    private OTPObserver createOTPObserver(int otpOption) {
        if (otpOption == 1)
            return new SMSNotifier();
        else if (otpOption == 2)
            return new EmailNotifier();
        else
            return new AuthenticatorAppNotifier();
    }

    private String promptForOTP() {
        System.out.print("Please enter the OTP: ");
        return scanner.nextLine();
    }

    public void showDashboard() {
        System.out.println("\nWelcome to your dashboard!");
        System.out.println("Account Balances: $10,000");
        System.out.println("Loans: $2,000");
        System.out.println("Credit Cards: $5,000 limit\n\n");
    }
}

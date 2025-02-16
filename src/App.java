import java.util.Scanner;
import otp.OTPService;
import otp.OTPObserver;
import otp.SMSNotifier;
import otp.EmailNotifier;
import otp.AuthenticatorAppNotifier;
import validator.Validator;
import validator.ValidatorFactory;
import verifier.VerificationContext;
import user.Customer;
import user.CustomerService;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Serendib Digital Banking App!\n\n");

        // Take user input for language preference via scanner
        System.out.println("Please enter your preferred language: ");
        String languagePreference = scanner.nextLine();
        System.out.println("You have selected: " + languagePreference);

        /*
         * Identifier Selection
         */
        try {
            // Take user input for NIC or Passport
            System.out.println("Please select the type of identifier you will be using: \n1. NIC\n2. Passport");
            int idType = scanner.nextInt();

            String identifier = "";
            ValidatorFactory validatorFactory = new ValidatorFactory();
            Validator validator = null;

            if (idType == 1) {
                System.out.println("Please enter your NIC (12 digits): ");
                identifier = scanner.next();
                validator = validatorFactory.getValidator("NIC");
                if (!validator.validate(identifier)) {
                    throw new Exception("NIC is invalid.");
                }

            } else if (idType == 2) {
                System.out.println("Please enter your Passport (9 digits): ");
                identifier = scanner.next();
                validator = validatorFactory.getValidator("Passport");
                if (!validator.validate(identifier)) {
                    throw new Exception("Passport is invalid.");
                }
            } else {
                throw new Exception("Invalid identifier type.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred in the identifier selection process: " + e.getMessage());
        }

        System.out.println("Would you like to select Serendib Account as the boarding option? (Y/N): ");
        String boardingOption = scanner.next();
        if (!boardingOption.equalsIgnoreCase("Y")) {
            System.out.println("Thank you for using the Serendib Digital Banking App!");
            System.exit(0);
        }

        /**
         * CASA Account Number Validation
         */
        try {
            System.out.println("Enter your CASA Account Number (10 digits): ");
            String casaAccountNumber = scanner.next();
            ValidatorFactory validatorFactory = new ValidatorFactory();
            Validator validator = validatorFactory.getValidator("CASAAccountNumber");
            if (!validator.validate(casaAccountNumber)) {
                throw new Exception("CASA Account Number is invalid.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred in the CASA Account Number process: " + e.getMessage());
        }

        /**
         * OTP Generation and Notification
         */
        try {
            System.out.println("How would you like to receive the OTP? \n1. SMS\n2. Email\n3. Authenticator App");
            int otpOption = scanner.nextInt();

            if (otpOption < 1 || otpOption > 3) {
                throw new Exception("Invalid OTP option.");
            }

            OTPService otpService = new OTPService();
            if (otpOption == 1) {
                OTPObserver smsNotifier = new SMSNotifier();
                otpService.addObserver(smsNotifier);
            } else if (otpOption == 2) {
                OTPObserver emailNotifier = new EmailNotifier();
                otpService.addObserver(emailNotifier);
            } else if (otpOption == 3) {
                OTPObserver appNotifier = new AuthenticatorAppNotifier();
                otpService.addObserver(appNotifier);
            }

            otpService.generateOTP(); // Generates an OTP and notifies all observers

            System.out.println("Please enter the OTP you received: ");
            String otp = scanner.next();
            otpService.validateOTP(otp); // Validates the OTP entered by the user

        } catch (Exception e) {
            System.out.println("An error occurred in the OTP process: " + e.getMessage());
        }

        /**
         * Verification Method Selection
         */
        try {
            System.out.println("Enter Verification Method: \n1. Contact Call Centre\n2. At a Serendib Branch");
            int verificationMethod = scanner.nextInt();

            VerificationContext context = new VerificationContext();

            if (verificationMethod == 1) {
                context.setStrategy(new verifier.CallCenterVerification());
                context.executeVerification();
            } else if (verificationMethod == 2) {
                context.setStrategy(new verifier.BranchVerification());
                context.executeVerification();
            } else {
                throw new Exception("Invalid verification method.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred in the verification process: " + e.getMessage());
        }

        /**
         * Customer Registration
         */
        try {
            // Validate username
            System.out.println("Input your username: ");
            String username = scanner.next();
            CustomerService customerService = new CustomerService();
            if (customerService.doesUsernameExist(username)) {
                throw new Exception("Username already exists.");
            }

            // Validate password
            System.out.println("Input your password: ");
            String password = scanner.next();
            ValidatorFactory validatorFactory = new ValidatorFactory();
            Validator validator = validatorFactory.getValidator("Password");
            if (!validator.validate(password)) {
                throw new Exception("Password is invalid.");
            }

            // Request display name from user
            System.out.println("Please enter your display name: ");
            String displayName = scanner.next();
            Customer customer = new Customer(username, password);
            customer.setDisplayName(displayName);

            // Save customer
            customerService.saveCustomer(customer);
        } catch (Exception e) {
            System.out.println("An error occurred in the customer registration process: " + e.getMessage());
        }

        System.out.println("Thank you for using the Serendib Digital Banking App!");
        scanner.close();

    }
}

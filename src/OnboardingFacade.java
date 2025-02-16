import java.util.Scanner;

import otp.AuthenticatorAppNotifier;
import otp.EmailNotifier;
import otp.OTPObserver;
import otp.OTPService;
import otp.SMSNotifier;
import user.Customer;
import user.CustomerService;
import validator.Validator;
import validator.ValidatorFactory;
import verifier.VerificationContext;

public class OnboardingFacade {

    private Scanner scanner;
    private ValidatorFactory validatorFactory;
    private OTPService otpService;
    private CustomerService customerService;
    private VerificationContext verificationContext;

    public OnboardingFacade() {
        scanner = new Scanner(System.in);
        validatorFactory = new ValidatorFactory();
        otpService = new OTPService();
        customerService = new CustomerService();
        verificationContext = new VerificationContext();
    }

    public void registerCustomer() throws Exception {
        // Handle language preference, identifier selection, OTP generation, and customer registration
        selectLanguage();
        handleIdentifierSelection();
        selectCASAAccountNumber();
        handleOTPGeneration();
        selectVerificationMethod();
        createCustomer();
    }

    private void selectLanguage() {
        System.out.print("Please enter your preferred language: ");
        String languagePreference = scanner.nextLine();
        System.out.println("You have selected: " + languagePreference);
    }

    private String handleIdentifierSelection() throws Exception {
        System.out.print("Please select the type of identifier you will be using: \n1. NIC\n2. Passport\nChoice: ");
        int idType = scanner.nextInt();
        String identifier = "";
        Validator validator;

        if (idType == 1) {
            System.out.print("Please enter your NIC (12 digits): ");
            identifier = scanner.next();
            validator = validatorFactory.getValidator("NIC");
            if (!validator.validate(identifier)) {
                throw new Exception("NIC is invalid.");
            }
        } else if (idType == 2) {
            System.out.print("Please enter your Passport (9 digits): ");
            identifier = scanner.next();
            validator = validatorFactory.getValidator("Passport");
            if (!validator.validate(identifier)) {
                throw new Exception("Passport is invalid.");
            }
        } else {
            throw new Exception("Invalid identifier type.");
        }
        return identifier;
    }

    private void selectCASAAccountNumber() throws Exception {
        System.out.print("Enter your CASA Account Number (10 digits): ");
        String casaAccountNumber = scanner.next();
        Validator validator = validatorFactory.getValidator("CASAAccountNumber");
        if (!validator.validate(casaAccountNumber)) {
            throw new Exception("CASA Account Number is invalid.");
        }
    }

    private void handleOTPGeneration() throws Exception {
        System.out.print("How would you like to receive the OTP? \n1. SMS\n2. Email\n3. Authenticator App\nChoice: ");
        int otpOption = scanner.nextInt();

        if (otpOption < 1 || otpOption > 3) {
            throw new Exception("Invalid OTP option.");
        }

        OTPObserver otpNotifier = createOTPObserver(otpOption);
        otpService.addObserver(otpNotifier);
        otpService.generateOTP();
        System.out.print("Please enter the OTP you received: ");
        String otp = scanner.next();
        otpService.validateOTP(otp);
    }

    private OTPObserver createOTPObserver(int otpOption) {
        if (otpOption == 1) return new SMSNotifier();
        else if (otpOption == 2) return new EmailNotifier();
        else return new AuthenticatorAppNotifier();
    }

    private void selectVerificationMethod() throws Exception {
        System.out.print("Enter Verification Method: \n1. Contact Call Centre\n2. At a Serendib Branch\nChoice: ");
        int verificationMethod = scanner.nextInt();

        if (verificationMethod == 1) {
            verificationContext.setStrategy(new verifier.CallCenterVerification());
            verificationContext.executeVerification();
        } else if (verificationMethod == 2) {
            verificationContext.setStrategy(new verifier.BranchVerification());
            verificationContext.executeVerification();
        } else {
            throw new Exception("Invalid verification method.");
        }
    }

    private void createCustomer() throws Exception {
        System.out.print("Input your username: ");
        String username = scanner.next();
        if (customerService.doesUsernameExist(username)) {
            throw new Exception("Username already exists.");
        }

        System.out.print("Input your password: ");
        String password = scanner.next();
        Validator validator = validatorFactory.getValidator("Password");
        if (!validator.validate(password)) {
            throw new Exception("Password is invalid.");
        }

        System.out.print("Please enter your display name: ");
        String displayName = scanner.next();
        Customer customer = new Customer(username, password);
        customer.setDisplayName(displayName);

        customerService.saveCustomer(customer);
    }
}

import java.util.Scanner;
import otp.OTPService;
import otp.OTPObserver;
import otp.SMSNotifier;
import otp.EmailNotifier;
import otp.AuthenticatorAppNotifier;
import validator.Validator;
import validator.ValidatorFactory;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Serendib Digital Banking App!\n\n");

        // Take user input for language preference via scanner
        System.out.println("Please enter your preferred language: ");

        String languagePreference = scanner.nextLine();
        System.out.println("You have selected: " + languagePreference);

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
            throw new Exception("Invalid input.");
        }

        System.out.println("Would you like to select Serendib Account as the boarding option? (Y/N): ");
        String boardingOption = scanner.next();
        if (!boardingOption.equalsIgnoreCase("Y")) {
            System.out.println("Thank you for using the Serendib Digital Banking App!");
            System.exit(0);
        }

        System.out.println("Enter your CASA Account Number (10 digits): ");
        String casaAccountNumber = scanner.next();
        validator = validatorFactory.getValidator("CASAAccountNumber");
        if (!validator.validate(casaAccountNumber)) {
            throw new Exception("CASA Account Number is invalid.");
        }

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
        } catch (Exception e) {
            System.out.println("An error occurred in the OTP process: " + e.getMessage());
        }

    }
}

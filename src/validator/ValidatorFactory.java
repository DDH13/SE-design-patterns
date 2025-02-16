package validator;

public class ValidatorFactory {
    public Validator getValidator(String type) {
        if (type.equals("NIC")) {
            return new NICValidator();
        } else if (type.equals("Passport")) {
            return new PassportValidator();
        } else if (type.equals("CASAAccountNumber")) {
            return new CASAAccountNumberValidator();
        } else if (type.equals("Password")) {
            return new PasswordValidator();
        } else {
            return null;
        }
    }
    
}

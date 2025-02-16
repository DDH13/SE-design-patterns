package validator;

public class PasswordValidator implements Validator {
    @Override
    public boolean validate(String input) {
        int length = input.length();
        if (length < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }
        return true;
    }
    
}

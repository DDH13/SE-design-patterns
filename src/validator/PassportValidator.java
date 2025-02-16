package validator;

public class PassportValidator implements Validator {
    @Override
    public boolean validate(String input) {
        boolean result =  input.length() == 9;
        if (!result) {
            System.out.println("Invalid passport number.");
        }
        return result;
    }
    
}

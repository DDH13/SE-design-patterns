package validator;

public class CASAAccountNumberValidator implements Validator {
    @Override
    public boolean validate(String input) {
        boolean result =  input.length() == 10;
        if (!result) {
            System.out.println("Invalid CASA account number.");
        }
        return result;
    }
    
}

package validator;

public class NICValidator implements Validator {
    @Override
    public boolean validate(String input) {
        boolean result =  input.length() == 12;
        if (!result) {
            System.out.println("Invalid NIC number.");
        }
        return result;
    }
}

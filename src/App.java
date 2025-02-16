public class App {
    public static void main(String[] args) {
        try {
            OnboardingFacade facade = new OnboardingFacade();
            facade.registerCustomer();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        System.out.println("Thank you for using the Serendib Digital Banking App!");
    }
}

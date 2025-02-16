import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "\nWelcome to the Serendib Digital Banking App!\nPlease select an option:\n1. Register\n2. Login\n3. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (option == 1) {
                try {
                    OnboardingFacade facade = new OnboardingFacade();
                    facade.registerCustomer();
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            } else if (option == 2) {
                try {
                    LoginFacade facade = new LoginFacade();
                    facade.loginUser();
                    facade.showDashboard();
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            } else if (option == 3) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }

        }
        scanner.close();

        System.out.println("Thank you for using the Serendib Digital Banking App!\n");
    }
}

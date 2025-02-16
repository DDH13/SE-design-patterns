package user;

public class UserFactory {
    public static User createUser(String type, String username, String password) {
        if (type.equalsIgnoreCase("CUSTOMER")) {
            return new Customer(username, password);
        } else if (type.equalsIgnoreCase("ADMIN")) {
            return new Admin(username, password);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
    }
}
// // Client Code
// public class FactoryPatternExample {
//     public static void main(String[] args) {
//         User user1 = UserFactory.createUser("CUSTOMER", "john_doe", "password123");
//         User user2 = UserFactory.createUser("ADMIN", "admin01", "securePass");

//         user1.displayRole();
//         user2.displayRole();
//     }
// }
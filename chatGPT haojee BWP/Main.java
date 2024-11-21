import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();
        OrderService orderService = new OrderService();

        // Sample Data
        bookDAO.addBook(new Book("B001", "Java Programming", "Author A", "Education", 50.0, 10, true));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Bookworm Paradise!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Registration process
            // Collect user details and register
        } else if (choice == 2) {
            // Login process
            // Collect email and password
            // Authenticate user
            // Show options to purchase or rent books
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}

public class InputValidator {
    public static boolean isValidEmail(String email) {
        // Simple regex for email validation
        return email.matches("^(.+)@(.+)$");
    }

    public static boolean isValidPassword(String password) {
        // Password policy: at least 6 characters
        return password.length() >= 6;
    }

    // Other validation methods as needed
}

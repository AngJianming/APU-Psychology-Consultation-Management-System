public class OrderService {
    public Order createOrder(Customer customer, Book book, String orderType) {
        String orderId = generateOrderId();
        String orderDate = getCurrentDate();
        double totalAmount = calculateTotalAmount(book.getPrice(), customer.getMembershipType());
        Order order = new Order(orderId, customer, book, orderType, orderDate, totalAmount);
        // Save order to database or order list
        return order;
    }

    private String generateOrderId() {
        // Generate unique order ID
        return "ORD" + System.currentTimeMillis();
    }

    private String getCurrentDate() {
        // Return current date as String
        return java.time.LocalDate.now().toString();
    }

    private double calculateTotalAmount(double price, String membershipType) {
        double discount = getDiscountPercentage(membershipType);
        return price - (price * discount / 100);
    }

    private double getDiscountPercentage(String membershipType) {
        switch (membershipType) {
            case "Premium":
                return 20.0;
            case "Gold":
                return 10.0;
            default:
                return 0.0;
        }
    }
}

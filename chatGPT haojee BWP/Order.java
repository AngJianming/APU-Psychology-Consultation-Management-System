public class Order {
    private String orderId;
    private Customer customer;
    private Book book;
    private String orderType; // Purchase or Rental
    private String orderDate;
    private double totalAmount;

    // Constructors, Getters, and Setters

    public Order(String orderId, Customer customer, Book book, String orderType, String orderDate, double totalAmount) {
        this.orderId = orderId;
        this.customer = customer;
        this.book = book;
        this.orderType = orderType;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
}

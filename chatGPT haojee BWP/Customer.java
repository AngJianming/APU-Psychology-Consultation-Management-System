public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String password;
    private String membershipType;
    private String address;
    private String contactNumber;

    // Constructors, Getters, and Setters

    public Customer(String customerId, String name, String email, String password, String membershipType, String address, String contactNumber) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.membershipType = membershipType;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
}

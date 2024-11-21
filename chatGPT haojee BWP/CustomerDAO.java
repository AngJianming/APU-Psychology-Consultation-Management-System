import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private List<Customer> customers = new ArrayList<>();

    public void registerCustomer(Customer customer) {
        customers.add(customer);
        // Code to save customer to a database can be added here
    }

    public Customer login(String email, String password) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null; // Login failed
    }

    // Other methods as needed
}

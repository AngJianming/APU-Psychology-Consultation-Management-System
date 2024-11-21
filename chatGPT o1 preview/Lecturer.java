// Lecturer.java
public class Lecturer extends User {
    private String lecturerID;
    private List<ConsultationSlot> availableSlots;

    public Lecturer(String username, String password, String name, String email, String lecturerID) {
        super(username, password, name, email);
        this.lecturerID = lecturerID;
        this.availableSlots = new ArrayList<>();
    }

    // Implement abstract methods
    @Override
    public void register() {
        // Registration logic for lecturer
    }

    @Override
    public boolean login() {
        // Login logic for lecturer
        return true;
    }

    // Additional methods
    public void manageSlots() {
        // Logic to manage consultation slots
    }
}

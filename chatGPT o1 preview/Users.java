// Users.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// Base User class
abstract class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name     = name;
        this.email    = email;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }

    // Abstract method to get role
    public abstract String getRole();
}

// Student class
class Student extends User {
    private String studentID;
    private List<Appointment> appointments;

    public Student(String username, String password, String name, String email, String studentID) {
        super(username, password, name, email);
        this.studentID    = studentID;
        this.appointments = new ArrayList<>();
    }

    public String getStudentID() { return studentID; }

    public String getRole() { return "Student"; }

    public List<Appointment> getAppointments() { return appointments; }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
}

// Lecturer class
class Lecturer extends User {
    private String lecturerID;
    private List<ConsultationSlot> availableSlots;
    private List<Appointment> appointments;
    private List<Feedback> feedbacks;

    public Lecturer(String username, String password, String name, String email, String lecturerID) {
        super(username, password, name, email);
        this.lecturerID     = lecturerID;
        this.availableSlots = new ArrayList<>();
        this.appointments   = new ArrayList<>();
        this.feedbacks      = new ArrayList<>();
    }

    public String getLecturerID() { return lecturerID; }

    public String getRole() { return "Lecturer"; }

    public List<ConsultationSlot> getAvailableSlots() { return availableSlots; }

    public List<Appointment> getAppointments() { return appointments; }

    public List<Feedback> getFeedbacks() { return feedbacks; }

    public void addAvailableSlot(ConsultationSlot slot) {
        availableSlots.add(slot);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }
}

// Appointment class
class Appointment {
    private String appointmentID;
    private String studentUsername;
    private String lecturerUsername;
    private String dateTime;
    private String status; // e.g., Scheduled, Completed, Cancelled

    public Appointment(String appointmentID, String studentUsername, String lecturerUsername, String dateTime, String status) {
        this.appointmentID     = appointmentID;
        this.studentUsername   = studentUsername;
        this.lecturerUsername  = lecturerUsername;
        this.dateTime          = dateTime;
        this.status            = status;
    }

    // Getters and setters
    public String getAppointmentID()    { return appointmentID; }
    public String getStudentUsername()  { return studentUsername; }
    public String getLecturerUsername() { return lecturerUsername; }
    public String getDateTime()         { return dateTime; }
    public String getStatus()           { return status; }

    public void setStatus(String status) { this.status = status; }
}

// ConsultationSlot class
class ConsultationSlot {
    private String slotID;
    private String lecturerUsername;
    private String dateTime;
    private boolean isBooked;

    public ConsultationSlot(String slotID, String lecturerUsername, String dateTime, boolean isBooked) {
        this.slotID            = slotID;
        this.lecturerUsername  = lecturerUsername;
        this.dateTime          = dateTime;
        this.isBooked          = isBooked;
    }

    // Getters and setters
    public String getSlotID()           { return slotID; }
    public String getLecturerUsername() { return lecturerUsername; }
    public String getDateTime()         { return dateTime; }
    public boolean getIsBooked()        { return isBooked; }

    public void setIsBooked(boolean isBooked) { this.isBooked = isBooked; }
}

// Feedback class
class Feedback {
    private String feedbackID;
    private String appointmentID;
    private String studentUsername;
    private String lecturerUsername;
    private int rating;
    private String comments;

    public Feedback(String feedbackID, String appointmentID, String studentUsername, String lecturerUsername, int rating, String comments) {
        this.feedbackID        = feedbackID;
        this.appointmentID     = appointmentID;
        this.studentUsername   = studentUsername;
        this.lecturerUsername  = lecturerUsername;
        this.rating            = rating;
        this.comments          = comments;
    }

    // Getters and setters
    public String getFeedbackID()       { return feedbackID; }
    public String getAppointmentID()    { return appointmentID; }
    public String getStudentUsername()  { return studentUsername; }
    public String getLecturerUsername() { return lecturerUsername; }
    public int getRating()              { return rating; }
    public String getComments()         { return comments; }
}

// Main class to run the application
public class Users {
    // List to store users
    static List<User> userList = new ArrayList<>();
    // List to store appointments
    static List<Appointment> appointmentList = new ArrayList<>();
    // List to store consultation slots
    static List<ConsultationSlot> slotList = new ArrayList<>();
    // List to store feedbacks
    static List<Feedback> feedbackList = new ArrayList<>();

    public static void main(String[] args) {
        // Load data from files
        loadUsers();
        loadConsultationSlots();
        loadAppointments();
        loadFeedbacks();

        // Start the login screen
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen();
            }
        });
    }

    // Method to load users from file
    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String role = data[4];
                if (role.equals("Student")) {
                    Student student = new Student(data[0], data[1], data[2], data[3], data[5]);
                    userList.add(student);
                } else if (role.equals("Lecturer")) {
                    Lecturer lecturer = new Lecturer(data[0], data[1], data[2], data[3], data[5]);
                    userList.add(lecturer);
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, create sample users
            createSampleUsers();
            saveUsers();
        }
    }

    // Method to save users to file
    public static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : userList) {
                String role = user.getRole();
                String userID = "";
                if (user instanceof Student) {
                    userID = ((Student) user).getStudentID();
                } else if (user instanceof Lecturer) {
                    userID = ((Lecturer) user).getLecturerID();
                }
                String data = user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getEmail() + "," + role + "," + userID;
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create sample users
    private static void createSampleUsers() {
        userList.add(new Student("student1", "pass1", "Alice", "alice@example.com", "S001"));
        userList.add(new Lecturer("lecturer1", "pass2", "Dr. Smith", "smith@example.com", "L001"));
    }

    // Method to load consultation slots from file
    private static void loadConsultationSlots() {
        try (BufferedReader reader = new BufferedReader(new FileReader("slots.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                ConsultationSlot slot = new ConsultationSlot(data[0], data[1], data[2], Boolean.parseBoolean(data[3]));
                slotList.add(slot);

                // Add slot to lecturer's available slots
                Lecturer lecturer = (Lecturer) getUserByUsername(data[1]);
                if (lecturer != null) {
                    lecturer.addAvailableSlot(slot);
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, create sample slots
            createSampleSlots();
            saveConsultationSlots();
        }
    }

    // Method to save consultation slots to file
    public static void saveConsultationSlots() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("slots.txt"))) {
            for (ConsultationSlot slot : slotList) {
                String data = slot.getSlotID() + "," + slot.getLecturerUsername() + "," + slot.getDateTime() + "," + slot.getIsBooked();
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create sample slots
    private static void createSampleSlots() {
        ConsultationSlot slot1 = new ConsultationSlot("CS001", "lecturer1", "2023-12-01 10:00", false);
        ConsultationSlot slot2 = new ConsultationSlot("CS002", "lecturer1", "2023-12-01 11:00", false);
        slotList.add(slot1);
        slotList.add(slot2);

        Lecturer lecturer = (Lecturer) getUserByUsername("lecturer1");
        if (lecturer != null) {
            lecturer.addAvailableSlot(slot1);
            lecturer.addAvailableSlot(slot2);
        }
    }

    // Method to load appointments from file
    private static void loadAppointments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Appointment appointment = new Appointment(data[0], data[1], data[2], data[3], data[4]);
                appointmentList.add(appointment);

                // Add appointment to student and lecturer
                Student student = (Student) getUserByUsername(data[1]);
                Lecturer lecturer = (Lecturer) getUserByUsername(data[2]);
                if (student != null) {
                    student.addAppointment(appointment);
                }
                if (lecturer != null) {
                    lecturer.addAppointment(appointment);
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, no appointments yet
        }
    }

    // Method to save appointments to file
    public static void saveAppointments() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointments.txt"))) {
            for (Appointment appointment : appointmentList) {
                String data = appointment.getAppointmentID() + "," + appointment.getStudentUsername() + "," + appointment.getLecturerUsername() + "," + appointment.getDateTime() + "," + appointment.getStatus();
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load feedbacks from file
    private static void loadFeedbacks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("feedbacks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Feedback feedback = new Feedback(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5]);
                feedbackList.add(feedback);

                // Add feedback to lecturer
                Lecturer lecturer = (Lecturer) getUserByUsername(data[3]);
                if (lecturer != null) {
                    lecturer.addFeedback(feedback);
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, no feedbacks yet
        }
    }

    // Method to save feedbacks to file
    public static void saveFeedbacks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedbacks.txt"))) {
            for (Feedback feedback : feedbackList) {
                String data = feedback.getFeedbackID() + "," + feedback.getAppointmentID() + "," + feedback.getStudentUsername() + "," + feedback.getLecturerUsername() + "," + feedback.getRating() + "," + feedback.getComments();
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get user by username
    public static User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    // Helper method to generate unique IDs
    public static String generateID(String prefix) {
        return prefix + System.currentTimeMillis();
    }
}

// GUI Classes

// LoginScreen class with modern GUI
class LoginScreen extends JFrame implements ActionListener {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton, registerButton;
    private JCheckBox showPasswordCheckBox;

    public LoginScreen() {
        setTitle("Consultation Management System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Apply a modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Set up the GUI components
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Welcome to the Consultation System");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setBounds(50, 20, 400, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBounds(50, 100, 80, 25);
        panel.add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(150, 100, 300, 25);
        panel.add(userTextField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(50, 150, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 300, 25);
        panel.add(passwordField);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(150, 180, 150, 25);
        showPasswordCheckBox.setBackground(new Color(245, 245, 245));
        showPasswordCheckBox.addActionListener(this);
        panel.add(showPasswordCheckBox);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setBounds(50, 220, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"Student", "Lecturer"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(150, 220, 300, 25);
        panel.add(roleComboBox);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(100, 280, 120, 40);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBounds(280, 280, 120, 40);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            String role     = (String) roleComboBox.getSelectedItem();

            // Authentication logic
            User user = authenticate(username, password, role);

            if (user != null) {
                // Login successful
                JOptionPane.showMessageDialog(this, "Login successful as " + role);
                dispose(); // Close login window
                new Dashboard(user); // Open dashboard
            } else {
                // Login failed
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        } else if (e.getSource() == registerButton) {
            // Open registration screen
            dispose();
            new RegistrationScreen();
        } else if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('•'); // Hide password
            }
        }
    }

    // Authentication method
    private User authenticate(String username, String password, String role) {
        for (User user : Users.userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (role.equals("Student") && user instanceof Student) {
                    return user;
                } else if (role.equals("Lecturer") && user instanceof Lecturer) {
                    return user;
                }
            }
        }
        return null; // Authentication failed
    }
}

// RegistrationScreen class
class RegistrationScreen extends JFrame implements ActionListener {
    private JTextField usernameField, nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton, backButton;

    public RegistrationScreen() {
        setTitle("Consultation Management System - Registration");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Apply a modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setBounds(50, 20, 400, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 100, 80, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 300, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 300, 25);
        panel.add(passwordField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 200, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 200, 300, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 250, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 250, 300, 25);
        panel.add(emailField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 300, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"Student", "Lecturer"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(150, 300, 300, 25);
        panel.add(roleComboBox);

        registerButton = new JButton("Register");
        registerButton.setBounds(100, 380, 120, 40);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(280, 380, 120, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // Get user input
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String name     = nameField.getText();
            String email    = emailField.getText();
            String role     = (String) roleComboBox.getSelectedItem();

            // Validate input
            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            // Check if username already exists
            for (User user : Users.userList) {
                if (user.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists.");
                    return;
                }
            }

            // Create new user
            User newUser;
            if (role.equals("Student")) {
                String studentID = "S" + (Users.userList.size() + 1);
                newUser = new Student(username, password, name, email, studentID);
            } else {
                String lecturerID = "L" + (Users.userList.size() + 1);
                newUser = new Lecturer(username, password, name, email, lecturerID);
            }

            // Add user to the list
            Users.userList.add(newUser);
            // Save users to file
            Users.saveUsers();

            JOptionPane.showMessageDialog(this, "Registration successful!");

            // Redirect to login screen
            dispose();
            new LoginScreen();
        } else if (e.getSource() == backButton) {
            // Go back to login screen
            dispose();
            new LoginScreen();
        }
    }
}

// Dashboard class
class Dashboard extends JFrame implements ActionListener {
    private User user;
    private JLabel welcomeLabel;
    private JButton logoutButton;
    private JPanel mainPanel;

    public Dashboard(User user) {
        this.user = user;
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Apply a modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));

        welcomeLabel = new JLabel("Welcome, " + user.getName());
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setBounds(20, 10, 760, 40);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(700, 10, 80, 25);
        logoutButton.addActionListener(this);
        mainPanel.add(logoutButton);

        // Depending on the user role, display different options
        if (user instanceof Student) {
            displayStudentOptions();
        } else if (user instanceof Lecturer) {
            displayLecturerOptions();
        }

        add(mainPanel);
        setVisible(true);
    }

    // Display options for students
    private void displayStudentOptions() {
        JButton viewLecturersButton = new JButton("View Lecturers");
        viewLecturersButton.setBounds(300, 100, 200, 40);
        viewLecturersButton.addActionListener(this);
        mainPanel.add(viewLecturersButton);

        JButton viewAppointmentsButton = new JButton("View Appointments");
        viewAppointmentsButton.setBounds(300, 160, 200, 40);
        viewAppointmentsButton.addActionListener(this);
        mainPanel.add(viewAppointmentsButton);
    }

    // Display options for lecturers
    private void displayLecturerOptions() {
        JButton manageSlotsButton = new JButton("Manage Consultation Slots");
        manageSlotsButton.setBounds(300, 100, 200, 40);
        manageSlotsButton.addActionListener(this);
        mainPanel.add(manageSlotsButton);

        JButton viewAppointmentsButton = new JButton("View Appointments");
        viewAppointmentsButton.setBounds(300, 160, 200, 40);
        viewAppointmentsButton.addActionListener(this);
        mainPanel.add(viewAppointmentsButton);

        JButton viewFeedbackButton = new JButton("View Student Feedback");
        viewFeedbackButton.setBounds(300, 220, 200, 40);
        viewFeedbackButton.addActionListener(this);
        mainPanel.add(viewFeedbackButton);
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Logout")) {
            dispose();
            new LoginScreen();
        } else if (action.equals("View Lecturers")) {
            new ViewLecturersScreen((Student) user);
        } else if (action.equals("View Appointments")) {
            new ViewAppointmentsScreen(user);
        } else if (action.equals("Manage Consultation Slots")) {
            new ManageSlotsScreen((Lecturer) user);
        } else if (action.equals("View Student Feedback")) {
            new ViewFeedbackScreen((Lecturer) user);
        }
    }
}

// ViewLecturersScreen class for students to view and book lecturers
class ViewLecturersScreen extends JFrame implements ActionListener {
    private Student student;
    private JTable lecturerTable;
    private DefaultTableModel model;
    private JButton bookButton, backButton;

    public ViewLecturersScreen(Student student) {
        this.student = student;
        setTitle("View Lecturers");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Table to display lecturers
        String[] columnNames = { "Lecturer Username", "Name", "Email" };
        model = new DefaultTableModel(columnNames, 0);

        for (User user : Users.userList) {
            if (user instanceof Lecturer) {
                model.addRow(new Object[] { user.getUsername(), user.getName(), user.getEmail() });
            }
        }

        lecturerTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(lecturerTable);
        scrollPane.setBounds(50, 50, 700, 400);
        panel.add(scrollPane);

        bookButton = new JButton("Book Consultation");
        bookButton.setBounds(200, 470, 150, 40);
        bookButton.addActionListener(this);
        panel.add(bookButton);

        backButton = new JButton("Back");
        backButton.setBounds(450, 470, 150, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            int selectedRow = lecturerTable.getSelectedRow();
            if (selectedRow >= 0) {
                String lecturerUsername = (String) lecturerTable.getValueAt(selectedRow, 0);
                Lecturer lecturer = (Lecturer) Users.getUserByUsername(lecturerUsername);
                new BookConsultationScreen(student, lecturer);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a lecturer.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}

// BookConsultationScreen class for students to book a consultation slot
class BookConsultationScreen extends JFrame implements ActionListener {
    private Student student;
    private Lecturer lecturer;
    private JTable slotTable;
    private DefaultTableModel model;
    private JButton bookButton, backButton;

    public BookConsultationScreen(Student student, Lecturer lecturer) {
        this.student = student;
        this.lecturer = lecturer;
        setTitle("Book Consultation with " + lecturer.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Table to display available slots
        String[] columnNames = { "Slot ID", "DateTime", "Is Booked" };
        model = new DefaultTableModel(columnNames, 0);

        for (ConsultationSlot slot : lecturer.getAvailableSlots()) {
            model.addRow(new Object[] { slot.getSlotID(), slot.getDateTime(), slot.getIsBooked() ? "Yes" : "No" });
        }

        slotTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(slotTable);
        scrollPane.setBounds(50, 50, 700, 400);
        panel.add(scrollPane);

        bookButton = new JButton("Book Slot");
        bookButton.setBounds(200, 470, 150, 40);
        bookButton.addActionListener(this);
        panel.add(bookButton);

        backButton = new JButton("Back");
        backButton.setBounds(450, 470, 150, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            int selectedRow = slotTable.getSelectedRow();
            if (selectedRow >= 0) {
                String slotID = (String) slotTable.getValueAt(selectedRow, 0);
                ConsultationSlot slot = null;
                for (ConsultationSlot s : lecturer.getAvailableSlots()) {
                    if (s.getSlotID().equals(slotID)) {
                        slot = s;
                        break;
                    }
                }
                if (slot != null && !slot.getIsBooked()) {
                    // Create appointment
                    String appointmentID = Users.generateID("A");
                    Appointment appointment = new Appointment(appointmentID, student.getUsername(), lecturer.getUsername(), slot.getDateTime(), "Scheduled");
                    student.addAppointment(appointment);
                    lecturer.addAppointment(appointment);
                    Users.appointmentList.add(appointment);
                    Users.saveAppointments();

                    // Mark slot as booked
                    slot.setIsBooked(true);
                    Users.saveConsultationSlots();

                    JOptionPane.showMessageDialog(this, "Consultation booked successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Slot is already booked.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a slot.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}

// ViewAppointmentsScreen class for users to view their appointments
class ViewAppointmentsScreen extends JFrame implements ActionListener {
    private User user;
    private JTable appointmentTable;
    private DefaultTableModel model;
    private JButton cancelButton, feedbackButton, backButton;

    public ViewAppointmentsScreen(User user) {
        this.user = user;
        setTitle("View Appointments");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Table to display appointments
        String[] columnNames = { "Appointment ID", "Lecturer", "Student", "DateTime", "Status" };
        model = new DefaultTableModel(columnNames, 0);

        List<Appointment> appointments = new ArrayList<>();
        if (user instanceof Student) {
            appointments = ((Student) user).getAppointments();
        } else if (user instanceof Lecturer) {
            appointments = ((Lecturer) user).getAppointments();
        }

        for (Appointment appointment : appointments) {
            model.addRow(new Object[] {
                appointment.getAppointmentID(),
                appointment.getLecturerUsername(),
                appointment.getStudentUsername(),
                appointment.getDateTime(),
                appointment.getStatus()
            });
        }

        appointmentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setBounds(50, 50, 700, 400);
        panel.add(scrollPane);

        cancelButton = new JButton("Cancel Appointment");
        cancelButton.setBounds(50, 470, 150, 40);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        feedbackButton = new JButton("Provide Feedback");
        feedbackButton.setBounds(300, 470, 150, 40);
        feedbackButton.addActionListener(this);
        if (user instanceof Lecturer) {
            feedbackButton.setEnabled(false); // Lecturers don't provide feedback
        }
        panel.add(feedbackButton);

        backButton = new JButton("Back");
        backButton.setBounds(550, 470, 150, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            int selectedRow = appointmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                String appointmentID = (String) appointmentTable.getValueAt(selectedRow, 0);
                Appointment appointment = null;
                for (Appointment a : Users.appointmentList) {
                    if (a.getAppointmentID().equals(appointmentID)) {
                        appointment = a;
                        break;
                    }
                }
                if (appointment != null) {
                    appointment.setStatus("Cancelled");
                    Users.saveAppointments();
                    JOptionPane.showMessageDialog(this, "Appointment cancelled.");
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment.");
            }
        } else if (e.getSource() == feedbackButton) {
            int selectedRow = appointmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                String appointmentID = (String) appointmentTable.getValueAt(selectedRow, 0);
                Appointment appointment = null;
                for (Appointment a : Users.appointmentList) {
                    if (a.getAppointmentID().equals(appointmentID)) {
                        appointment = a;
                        break;
                    }
                }
                if (appointment != null && appointment.getStatus().equals("Completed")) {
                    new FeedbackScreen((Student) user, appointment);
                } else {
                    JOptionPane.showMessageDialog(this, "Feedback can only be provided for completed appointments.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}

// FeedbackScreen class for students to provide feedback
class FeedbackScreen extends JFrame implements ActionListener {
    private Student student;
    private Appointment appointment;
    private JTextArea commentsArea;
    private JComboBox<Integer> ratingComboBox;
    private JButton submitButton, backButton;

    public FeedbackScreen(Student student, Appointment appointment) {
        this.student = student;
        this.appointment = appointment;
        setTitle("Provide Feedback");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel ratingLabel = new JLabel("Rating (1-5):");
        ratingLabel.setBounds(50, 50, 100, 25);
        panel.add(ratingLabel);

        Integer[] ratings = {1, 2, 3, 4, 5};
        ratingComboBox = new JComboBox<>(ratings);
        ratingComboBox.setBounds(150, 50, 200, 25);
        panel.add(ratingComboBox);

        JLabel commentsLabel = new JLabel("Comments:");
        commentsLabel.setBounds(50, 100, 100, 25);
        panel.add(commentsLabel);

        commentsArea = new JTextArea();
        commentsArea.setBounds(50, 130, 300, 150);
        panel.add(commentsArea);

        submitButton = new JButton("Submit");
        submitButton.setBounds(80, 300, 100, 40);
        submitButton.addActionListener(this);
        panel.add(submitButton);

        backButton = new JButton("Back");
        backButton.setBounds(220, 300, 100, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            int rating = (int) ratingComboBox.getSelectedItem();
            String comments = commentsArea.getText();

            String feedbackID = Users.generateID("F");
            Feedback feedback = new Feedback(feedbackID, appointment.getAppointmentID(), student.getUsername(), appointment.getLecturerUsername(), rating, comments);
            Users.feedbackList.add(feedback);
            Users.saveFeedbacks();

            Lecturer lecturer = (Lecturer) Users.getUserByUsername(appointment.getLecturerUsername());
            if (lecturer != null) {
                lecturer.addFeedback(feedback);
            }

            JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");
            dispose();
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}

// ManageSlotsScreen class for lecturers to manage consultation slots
class ManageSlotsScreen extends JFrame implements ActionListener {
    private Lecturer lecturer;
    private JTable slotTable;
    private DefaultTableModel model;
    private JButton addButton, removeButton, backButton;

    public ManageSlotsScreen(Lecturer lecturer) {
        this.lecturer = lecturer;
        setTitle("Manage Consultation Slots");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Table to display slots
        String[] columnNames = { "Slot ID", "DateTime", "Is Booked" };
        model = new DefaultTableModel(columnNames, 0);

        for (ConsultationSlot slot : lecturer.getAvailableSlots()) {
            model.addRow(new Object[] { slot.getSlotID(), slot.getDateTime(), slot.getIsBooked() ? "Yes" : "No" });
        }

        slotTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(slotTable);
        scrollPane.setBounds(50, 50, 700, 400);
        panel.add(scrollPane);

        addButton = new JButton("Add Slot");
        addButton.setBounds(100, 470, 150, 40);
        addButton.addActionListener(this);
        panel.add(addButton);

        removeButton = new JButton("Remove Slot");
        removeButton.setBounds(300, 470, 150, 40);
        removeButton.addActionListener(this);
        panel.add(removeButton);

        backButton = new JButton("Back");
        backButton.setBounds(500, 470, 150, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String dateTime = JOptionPane.showInputDialog(this, "Enter date and time (YYYY-MM-DD HH:MM):");
            if (dateTime != null && !dateTime.isEmpty()) {
                String slotID = Users.generateID("CS");
                ConsultationSlot slot = new ConsultationSlot(slotID, lecturer.getUsername(), dateTime, false);
                lecturer.addAvailableSlot(slot);
                Users.slotList.add(slot);
                Users.saveConsultationSlots();
                model.addRow(new Object[] { slot.getSlotID(), slot.getDateTime(), "No" });
            }
        } else if (e.getSource() == removeButton) {
            int selectedRow = slotTable.getSelectedRow();
            if (selectedRow >= 0) {
                String slotID = (String) slotTable.getValueAt(selectedRow, 0);
                ConsultationSlot slot = null;
                for (ConsultationSlot s : lecturer.getAvailableSlots()) {
                    if (s.getSlotID().equals(slotID)) {
                        slot = s;
                        break;
                    }
                }
                if (slot != null && !slot.getIsBooked()) {
                    lecturer.getAvailableSlots().remove(slot);
                    Users.slotList.remove(slot);
                    Users.saveConsultationSlots();
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot remove a booked slot.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a slot.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}

// ViewFeedbackScreen class for lecturers to view feedback from students
class ViewFeedbackScreen extends JFrame implements ActionListener {
    private Lecturer lecturer;
    private JTable feedbackTable;
    private DefaultTableModel model;
    private JButton backButton;

    public ViewFeedbackScreen(Lecturer lecturer) {
        this.lecturer = lecturer;
        setTitle("View Student Feedback");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Table to display feedback
        String[] columnNames = { "Feedback ID", "Student Username", "Rating", "Comments" };
        model = new DefaultTableModel(columnNames, 0);

        for (Feedback feedback : lecturer.getFeedbacks()) {
            model.addRow(new Object[] {
                feedback.getFeedbackID(),
                feedback.getStudentUsername(),
                feedback.getRating(),
                feedback.getComments()
            });
        }

        feedbackTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setBounds(50, 50, 700, 400);
        panel.add(scrollPane);

        backButton = new JButton("Back");
        backButton.setBounds(350, 470, 100, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
        }
    }
}

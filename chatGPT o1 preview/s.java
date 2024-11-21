// Login.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    // Components of the Form
    private Container       container;
    private JLabel          titleLabel;
    private JLabel          userLabel;
    private JTextField      userTextField;
    private JLabel          passwordLabel;
    private JPasswordField  passwordField;
    private JButton         loginButton;
    private JButton         registerButton;
    private JLabel          roleLabel;
    private JComboBox<String> roleComboBox;
    private JCheckBox       showPassword;

    // Constructor
    public Login() {
        setTitle("Consultation Management System - Login");
        setBounds(300, 90, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container      = getContentPane();
        container.setLayout(null);

        titleLabel = new JLabel("Welcome to Consultation System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setSize(400, 30);
        titleLabel.setLocation(50, 30);
        container.add(titleLabel);

        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        userLabel.setSize(100, 20);
        userLabel.setLocation(50, 100);
        container.add(userLabel);

        userTextField = new JTextField();
        userTextField.setFont(new Font("Arial", Font.PLAIN, 15));
        userTextField.setSize(190, 20);
        userTextField.setLocation(150, 100);
        container.add(userTextField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setSize(100, 20);
        passwordLabel.setLocation(50, 150);
        container.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setSize(190, 20);
        passwordField.setLocation(150, 150);
        container.add(passwordField);

        showPassword = new JCheckBox("Show Password");
        showPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        showPassword.setSize(150, 20);
        showPassword.setLocation(150, 180);
        showPassword.addActionListener(this);
        container.add(showPassword);

        roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        roleLabel.setSize(100, 20);
        roleLabel.setLocation(50, 220);
        container.add(roleLabel);

        String roles[] = { "Student", "Lecturer" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        roleComboBox.setSize(190, 20);
        roleComboBox.setLocation(150, 220);
        container.add(roleComboBox);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setSize(100, 30);
        loginButton.setLocation(50, 280);
        loginButton.addActionListener(this);
        container.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setSize(120, 30);
        registerButton.setLocation(200, 280);
        registerButton.addActionListener(this);
        container.add(registerButton);

        setVisible(true);
    }

    // Action listener method
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String pwdText  = new String(passwordField.getPassword());
            String role     = (String) roleComboBox.getSelectedItem();

            // Simple validation (you can add more complex validation and authentication)
            if (userText.isEmpty() || pwdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
            } else {
                // Perform login logic here (e.g., check against stored credentials)
                JOptionPane.showMessageDialog(this, "Login successful as " + role);

                // Open the Dashboard
                dispose(); // Close the login window
                new Dashboard(role);
            }
        } else if (e.getSource() == registerButton) {
            // Open registration form
            JOptionPane.showMessageDialog(this, "Redirecting to registration form...");
        } else if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0); // Reveal password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        }
    }

    // Main method to run the Login form
    public static void main(String[] args) {
        new Login();
    }
    
    public static void main(String[] args) {
        // Inside Dashboard.java constructor after table creation

        // Enable row selection
        dataTable.setRowSelectionAllowed(true);

        // Add mouse listener to the table
        dataTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = dataTable.getSelectedRow();
                int col = dataTable.getSelectedColumn();
                // Get data from the selected row
                String id = dataTable.getValueAt(row, 0).toString();
                String name = dataTable.getValueAt(row, 1).toString();
                // Display details or perform actions based on selection
                JOptionPane.showMessageDialog(null, "Selected Consultation:\nID: " + id + "\nName: " + name);
            }
        });

    }
}


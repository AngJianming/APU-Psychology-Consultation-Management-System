// AddUserDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddUserDialog extends JDialog implements ActionListener {
    private JLabel usernameLabel, passwordLabel, nameLabel, emailLabel, roleLabel;
    private JTextField usernameField, nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton addButton, cancelButton;

    private AdminDashboard adminDashboard;

    public AddUserDialog(AdminDashboard parent) {
        super(parent, "Add New User", true);
        this.adminDashboard = parent;

        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        roleLabel = new JLabel("Role:");
        String[] roles = { "Student", "Lecturer" };
        roleComboBox = new JComboBox<>(roles);

        addButton = new JButton("Add User");
        addButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        // Adding components to dialog
        add(usernameLabel);
        add(usernameField);

        add(passwordLabel);
        add(passwordField);

        add(nameLabel);
        add(nameField);

        add(emailLabel);
        add(emailField);

        add(roleLabel);
        add(roleComboBox);

        add(addButton);
        add(cancelButton);
    }

    // Action listener method
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String name     = nameField.getText();
        String email    = emailField.getText();
        String role     = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        } else {
            User newUser;
            if (role.equals("Student")) {
                newUser = new Student(username, password, name, email, "S" + (adminDashboard.userList.size() + 1));
            } else {
                newUser = new Lecturer(username, password, name, email, "L" + (adminDashboard.userList.size() + 1));
            }
            adminDashboard.addUser(newUser);
            JOptionPane.showMessageDialog(this, "User added successfully.");
            dispose();
        }
    }
}

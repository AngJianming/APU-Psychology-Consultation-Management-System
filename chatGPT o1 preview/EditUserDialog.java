// EditUserDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditUserDialog extends JDialog implements ActionListener {
    private JLabel usernameLabel, passwordLabel, nameLabel, emailLabel, roleLabel;
    private JTextField usernameField, nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton, cancelButton;

    private AdminDashboard adminDashboard;
    private User user;
    private int userIndex;

    public EditUserDialog(AdminDashboard parent, User user, int index) {
        super(parent, "Edit User", true);
        this.adminDashboard = parent;
        this.user = user;
        this.userIndex = index;

        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(user.getUsername());
        usernameField.setEditable(false);

        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(user.getPassword());

        nameLabel = new JLabel("Name:");
        nameField = new JTextField(user.getName());

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(user.getEmail());

        roleLabel = new JLabel("Role:");
        String[] roles = { "Student", "Lecturer" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setSelectedItem(user instanceof Student ? "Student" : "Lecturer");
        roleComboBox.setEnabled(false);

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this);

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

        add(saveButton);
        add(cancelButton);
    }

    // Action listener method
    public void actionPerformed(ActionEvent e) {
        String password = new String(passwordField.getPassword());
        String name     = nameField.getText();
        String email    = emailField.getText();

        if (password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        } else {
            user.setPassword(password);
            user.setName(name);
            user.setEmail(email);

            adminDashboard.updateUser(user, userIndex);
            JOptionPane.showMessageDialog(this, "User updated successfully.");
            dispose();
        }
    }
}

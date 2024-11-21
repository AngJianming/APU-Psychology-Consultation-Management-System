// AdminDashboard.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminDashboard extends JFrame implements ActionListener {
    private Container container;
    private JLabel titleLabel;
    private JTable userTable;
    private JScrollPane scrollPane;
    private JButton addButton, removeButton, editButton, refreshButton;
    private DefaultTableModel model;

    // Sample data storage (you can replace this with file I/O)
    private ArrayList<User> userList;

    // Constructor
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setBounds(300, 90, 800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        titleLabel = new JLabel("Admin Dashboard - Manage Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setSize(400, 30);
        titleLabel.setLocation(250, 20);
        container.add(titleLabel);

        // Initialize user list
        userList = new ArrayList<>();
        loadSampleData();

        // Table columns
        String[] columnNames = { "Username", "Name", "Email", "Role" };
        model = new DefaultTableModel(columnNames, 0);
        loadUserData();

        // Create table and add to scroll pane
        userTable = new JTable(model);
        scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(50, 70, 700, 300);
        container.add(scrollPane);

        // Buttons
        addButton = new JButton("Add User");
        addButton.setFont(new Font("Arial", Font.PLAIN, 15));
        addButton.setBounds(50, 400, 120, 30);
        addButton.addActionListener(this);
        container.add(addButton);

        removeButton = new JButton("Remove User");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 15));
        removeButton.setBounds(200, 400, 140, 30);
        removeButton.addActionListener(this);
        container.add(removeButton);

        editButton = new JButton("Edit User");
        editButton.setFont(new Font("Arial", Font.PLAIN, 15));
        editButton.setBounds(370, 400, 120, 30);
        editButton.addActionListener(this);
        container.add(editButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 15));
        refreshButton.setBounds(520, 400, 100, 30);
        refreshButton.addActionListener(this);
        container.add(refreshButton);

        setVisible(true);
    }

    // Load sample data into userList
    private void loadSampleData() {
        // Add sample students
        userList.add(new Student("student1", "pass1", "Alice", "alice@example.com", "S001"));
        userList.add(new Student("student2", "pass2", "Bob", "bob@example.com", "S002"));

        // Add sample lecturers
        userList.add(new Lecturer("lecturer1", "pass3", "Dr. Smith", "smith@example.com", "L001"));
        userList.add(new Lecturer("lecturer2", "pass4", "Dr. Jones", "jones@example.com", "L002"));
    }

    // Load data from userList into the table model
    private void loadUserData() {
        model.setRowCount(0); // Clear existing data
        for (User user : userList) {
            String role = user instanceof Student ? "Student" : "Lecturer";
            model.addRow(new Object[] { user.getUsername(), user.getName(), user.getEmail(), role });
        }
    }

    // Action listener method
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Open Add User dialog
            openAddUserDialog();
        } else if (e.getSource() == removeButton) {
            // Remove selected user
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                userList.remove(selectedRow);
                loadUserData();
                JOptionPane.showMessageDialog(this, "User removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to remove.");
            }
        } else if (e.getSource() == editButton) {
            // Open Edit User dialog
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                User selectedUser = userList.get(selectedRow);
                openEditUserDialog(selectedUser, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            }
        } else if (e.getSource() == refreshButton) {
            // Refresh table data
            loadUserData();
        }
    }

    // Method to open Add User dialog
    private void openAddUserDialog() {
        AddUserDialog addUserDialog = new AddUserDialog(this);
        addUserDialog.setVisible(true);
    }

    // Method to open Edit User dialog
    private void openEditUserDialog(User user, int index) {
        EditUserDialog editUserDialog = new EditUserDialog(this, user, index);
        editUserDialog.setVisible(true);
    }

    // Method to update user in the list
    public void updateUser(User user, int index) {
        userList.set(index, user);
        loadUserData();
    }

    // Method to add user to the list
    public void addUser(User user) {
        userList.add(user);
        loadUserData();
    }

    // Main method for testing
    public static void main(String[] args) {
        new AdminDashboard();
    }
}

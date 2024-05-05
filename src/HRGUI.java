package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HRGUI extends JFrame {
    private JPanel mainPanel;
    private User user; // HR user's details

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JTextField departmentField;
    private JTextField jobTitleField;

    private JTable employeesTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public HRGUI(User user) {
        this.user = user;

        setTitle("HR Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        setupUI();
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    private void setupUI() {
        // Create panels
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Role:"));
        String[] roles = {"Employee", "Manager", "HR"};
        roleComboBox = new JComboBox<>(roles);
        formPanel.add(roleComboBox);

        formPanel.add(new JLabel("Department:"));
        departmentField = new JTextField(20);
        formPanel.add(departmentField);

        formPanel.add(new JLabel("Job Title:"));
        jobTitleField = new JTextField(20);
        formPanel.add(jobTitleField);

        addButton = new JButton("Add Employee");
        addButton.addActionListener(this::addEmployee);
        updateButton = new JButton("Update Employee");
        updateButton.addActionListener(this::updateEmployee);
        deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(this::deleteEmployee);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        String[] columnNames = {"Username", "First Name", "Last Name", "Email", "Role", "Department", "Job Title"};
        Object[][] data = {}; // This should be dynamically loaded from database
        employeesTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(employeesTable);
        employeesTable.setFillsViewportHeight(true);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
    }

    private void addEmployee(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String role = (String) roleComboBox.getSelectedItem();
        String department = departmentField.getText();
        String jobTitle = jobTitleField.getText();

        if (Database.addUser(username, password, role, firstName, lastName, department, jobTitle, email)) {
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add employee. Username might be taken or fields are incomplete.");
        }
    }

    private void updateEmployee(ActionEvent e) {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String role = (String) roleComboBox.getSelectedItem();
        String department = departmentField.getText();
        String jobTitle = jobTitleField.getText();

        boolean success = Database.updateEmployee(username, firstName, lastName, role, department, jobTitle, email);
        if (success) {
            JOptionPane.showMessageDialog(this, "Employee details updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update employee details.");
        }
    }

    private void deleteEmployee(ActionEvent e) {
        String username = usernameField.getText();
        if (Database.deleteUser(username)) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete employee.");
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        departmentField.setText("");
        jobTitleField.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        User user = new User("admin", "password", "HR", "Admin", "User", "HR Department", "HR Manager", "admin@example.com");
        new HRGUI(user);
    }
}
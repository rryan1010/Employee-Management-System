package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HRGUI extends JFrame {
    private JPanel mainPanel;
    private User user;

    // Panels for different sections
    private JPanel profilePanel, taskCreationPanel, employeeActionPanel;

    // User Details Components
    private JLabel nameLabel, roleLabel, departmentLabel, jobTitleLabel;

    // Task Creation Components
    private JTextField titleField, descriptionField, statusField, feedbackField;
    private JComboBox<String> assignedToDropdown, managerDropdown;
    private JButton createTaskButton;

    // Employee Action Components
    private JComboBox<String> usernameDropdown;
    private JButton deleteEmployeeButton, editEmployeeButton;

    // Task List Components
    private JTable tasksTable, employeesTable;
    private JScrollPane scrollPane;

    public HRGUI(User user) {
        this.user = user;
        setTitle("HR Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));

        setupProfilePanel();
        setupEmployeeActionPanel();
        setupTaskCreationPanel();

        populateUserDropdowns();

        setupTaskListPanel();
        setupEmployeeTable();
        refreshEmployeeTable();
        refreshTaskTable();

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void setupProfilePanel() {
        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayout(0, 1));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile Details"));

        nameLabel = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align to the left
        roleLabel = new JLabel("Role: " + user.getRole());
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align to the left
        departmentLabel = new JLabel("Department: " + user.getDepartment());
        departmentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align to the left
        jobTitleLabel = new JLabel("Job Title: " + user.getJobTitle());
        jobTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align to the left

        profilePanel.add(nameLabel);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(roleLabel);
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(departmentLabel);
        departmentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(jobTitleLabel);
        jobTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton goBackButton = new JButton("Log Out");
        goBackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        goBackButton.addActionListener(e -> {
            dispose();
            new LoginGUI();
        });

        profilePanel.add(goBackButton);
        profilePanel.setPreferredSize(new Dimension(300, mainPanel.getHeight()));
        profilePanel.setPreferredSize(new Dimension(getWidth() / 4, getHeight()));
        mainPanel.add(profilePanel, BorderLayout.WEST);
    }

    private void setupTaskCreationPanel() {
        taskCreationPanel = new JPanel();
        taskCreationPanel.setLayout(new GridLayout(0, 2, 5, 5));
        taskCreationPanel.setBorder(BorderFactory.createTitledBorder("Create Task"));

        titleField = new JTextField(20);
        descriptionField = new JTextField(20);
        statusField = new JTextField(20);
        feedbackField = new JTextField(20);
        createTaskButton = new JButton("Create Task");
        createTaskButton.addActionListener(this::createTask);

        assignedToDropdown = new JComboBox<>();
        managerDropdown = new JComboBox<>();

        taskCreationPanel.add(new JLabel("Title:"));
        taskCreationPanel.add(titleField);
        taskCreationPanel.add(new JLabel("Description:"));
        taskCreationPanel.add(descriptionField);
        taskCreationPanel.add(new JLabel("Assigned To:"));
        taskCreationPanel.add(assignedToDropdown);
        taskCreationPanel.add(new JLabel("Manager:"));
        taskCreationPanel.add(managerDropdown);
        taskCreationPanel.add(new JLabel("Feedback:"));
        taskCreationPanel.add(feedbackField);
        taskCreationPanel.add(createTaskButton);

        profilePanel.add(taskCreationPanel);
    }
    /*

    User/task table - jlabels 

    create task - jtextarea for title, description, and feedback

    Add promote/demote in employee actions 

    */

    private void setupEmployeeActionPanel() {
        employeeActionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 5, 1, 5);

        employeeActionPanel.setBorder(BorderFactory.createTitledBorder("Employee Actions"));

        usernameDropdown = new JComboBox<>();

        JLabel userLabel = new JLabel("Select Employee's Username:");
        userLabel.setPreferredSize(new Dimension(200, userLabel.getPreferredSize().height));  // Setting a preferred width
        gbc.weightx = 1.0; 
        employeeActionPanel.add(userLabel, gbc);

        usernameDropdown = new JComboBox<>();
        usernameDropdown.setMaximumSize(new Dimension(200, 25));  // Limiting the maximum size
        employeeActionPanel.add(usernameDropdown, gbc);

        deleteEmployeeButton = new JButton("Delete Employee");
        deleteEmployeeButton.setPreferredSize(new Dimension(200, 25));  // Setting a preferred height
        employeeActionPanel.add(deleteEmployeeButton, gbc);

        editEmployeeButton = new JButton("Edit Employee Details");
        editEmployeeButton.setPreferredSize(new Dimension(200, 25));  // Setting a preferred height
        employeeActionPanel.add(editEmployeeButton, gbc);

        profilePanel.add(employeeActionPanel);
    }

    private void setupTaskListPanel() {
        tasksTable = new JTable();
        scrollPane = new JScrollPane(tasksTable);
        tasksTable.setFillsViewportHeight(true);

        mainPanel.add(scrollPane, BorderLayout.EAST);
    }

    private void setupEmployeeTable() {
        String[] columnNames = { "Username", "First Name", "Last Name", "Email", "Role", "Department", "Job Title" }; // These
                                                                                                                      // are
                                                                                                                      // the
                                                                                                                      // column
                                                                                                                      // headers
        Object[][] data = {}; // Initial empty data

        // Initialize the table with data and column names
        employeesTable = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(employeesTable); // Enable scrolling
        employeesTable.setFillsViewportHeight(true);
    
        // You can add the scrollPane to a panel or directly to the mainPanel, depending on your layout
        mainPanel.add(scrollPane, BorderLayout.CENTER);  // Adjust layout as necessary
    }

    private void createTask(ActionEvent e) {
        if (titleField.getText().isEmpty() ||
                descriptionField.getText().isEmpty() ||
                assignedToDropdown.getSelectedItem() == null ||
                managerDropdown.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = Database.createTaskDB(
                titleField.getText(),
                descriptionField.getText(),
                "Assigned",
                assignedToDropdown.getSelectedItem().toString(),
                managerDropdown.getSelectedItem().toString(),
                feedbackField.getText());

        if (success) {
            JOptionPane.showMessageDialog(this, "Task created successfully!");
            refreshTaskTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create task.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee(ActionEvent e) {
        String username = (String) usernameDropdown.getSelectedItem();
        if (username != null && Database.deleteUser(username)) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            refreshEmployeeTable(); // Refresh the display table or list of employees
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editEmployeeDetails(ActionEvent e) {
        String username = (String) usernameDropdown.getSelectedItem();
        if (username != null) {
            User user = Database.getUserHR(username); // Assuming getUser method fetches the user details
            if (user != null) {
                // Logic to open a dialog or another panel for editing
                // For simplicity, assume we're using a JOptionPane for input
                String newRole = JOptionPane.showInputDialog("Enter new role for " + username);
                if (newRole != null && !newRole.isEmpty()) {
                    boolean success = Database.updateEmployee(
                            username, user.getFirstName(), user.getLastName(), newRole,
                            user.getDepartment(), user.getJobTitle(), user.getEmail());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Employee details updated successfully.");
                        refreshEmployeeTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update employee details.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void populateUserDropdowns() {
        List<String> usernames = Database.getEmployeeUsernames(); // Fetch usernames from database
        assignedToDropdown.setModel(new DefaultComboBoxModel<>(usernames.toArray(new String[0])));
        managerDropdown.setModel(new DefaultComboBoxModel<>(usernames.toArray(new String[0])));
        usernameDropdown.setModel(new DefaultComboBoxModel<>(usernames.toArray(new String[0])));
    }

    private void refreshTaskTable() {
        Object[][] data = Database.getAllTasks();
        DefaultTableModel model = new DefaultTableModel(data,
                new String[] { "Task ID", "Title", "Description", "Status", "Assigned To", "Manager" });
        tasksTable.setModel(model);
        tasksTable.revalidate();
    }

    private void refreshEmployeeTable() {
        // Fetch the latest employee data from the database
        Object[][] data = Database.getAllEmployees(); // You need to implement this method in the Database class
        String[] columnNames = { "Username", "First Name", "Last Name", "Email", "Role", "Department", "Job Title" };
        // Create a new table model with the fetched data
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        employeesTable.setModel(model);
        employeesTable.revalidate();
    }

    public static void main(String[] args) {
        User user = new User("Taiwo Oso", "password", "Manager", "Taiwo", "Oso", "CS Department", "Software Engineer",
                "taiwo@example.com");
        new HRGUI(user);
    }
}

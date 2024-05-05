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
    private JTable tasksTable;
    private JScrollPane scrollPane;

    public HRGUI(User user) {
        this.user = user;
        setTitle("HR Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        setupProfilePanel();
        setupTaskCreationPanel();
        setupEmployeeActionPanel();
        setupTaskListPanel();

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void setupProfilePanel() {
        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile Details"));

        nameLabel = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
        roleLabel = new JLabel("Role: " + user.getRole());
        departmentLabel = new JLabel("Department: " + user.getDepartment());
        jobTitleLabel = new JLabel("Job Title: " + user.getJobTitle());

        profilePanel.add(nameLabel);
        profilePanel.add(roleLabel);
        profilePanel.add(departmentLabel);
        profilePanel.add(jobTitleLabel);

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

        populateUserDropdowns();

        taskCreationPanel.add(new JLabel("Title:"));
        taskCreationPanel.add(titleField);
        taskCreationPanel.add(new JLabel("Description:"));
        taskCreationPanel.add(descriptionField);
        taskCreationPanel.add(new JLabel("Status:"));
        taskCreationPanel.add(statusField);
        taskCreationPanel.add(new JLabel("Assigned To:"));
        taskCreationPanel.add(assignedToDropdown);
        taskCreationPanel.add(new JLabel("Manager:"));
        taskCreationPanel.add(managerDropdown);
        taskCreationPanel.add(new JLabel("Feedback:"));
        taskCreationPanel.add(feedbackField);
        taskCreationPanel.add(createTaskButton);

        profilePanel.add(taskCreationPanel);
    }

    private void setupEmployeeActionPanel() {
        employeeActionPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        employeeActionPanel.setBorder(BorderFactory.createTitledBorder("Employee Actions"));

        usernameDropdown = new JComboBox<>();
        deleteEmployeeButton = new JButton("Delete Employee");
        editEmployeeButton = new JButton("Edit Employee Details");

        populateUserDropdowns();

        employeeActionPanel.add(new JLabel("Username:"));
        employeeActionPanel.add(usernameDropdown);
        employeeActionPanel.add(deleteEmployeeButton);
        employeeActionPanel.add(editEmployeeButton);

        profilePanel.add(employeeActionPanel);
    }

    private void setupTaskListPanel() {
        tasksTable = new JTable();
        scrollPane = new JScrollPane(tasksTable);
        tasksTable.setFillsViewportHeight(true);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void populateUserDropdowns() {
        List<String> usernames = Database.getEmployeeUsernames(); // Implement this method in Database
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(usernames.toArray(new String[0]));
        assignedToDropdown.setModel(model);
        managerDropdown.setModel(model);
        usernameDropdown.setModel(model);
    }

    private void createTask(ActionEvent e) {
        if (titleField.getText().isEmpty() ||
                descriptionField.getText().isEmpty() ||
                statusField.getText().isEmpty() ||
                assignedToDropdown.getSelectedItem() == null ||
                managerDropdown.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = Database.createTaskDB(
                titleField.getText(),
                descriptionField.getText(),
                statusField.getText(),
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

    private void refreshTaskTable() {
        Object[][] data = Database.getAllTasks();
        DefaultTableModel model = new DefaultTableModel(data,
                new String[] { "Task ID", "Title", "Description", "Status", "Assigned To", "Manager" });
        tasksTable.setModel(model);
    }

    public static void main(String[] args) {
        User user = new User("Taiwo Oso", "password", "Manager", "Taiwo", "Oso", "CS Department", "Software Engineer",
                "taiwo@example.com");
        new HRGUI(user);
    }
}

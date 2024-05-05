package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class HRGUI extends JFrame {
    private JPanel mainPanel;
    private User user;

    // Panels for different sections
    private JPanel profilePanel, taskCreationPanel, employeeActionPanel;

    // User Details Components
    private JLabel nameLabel, roleLabel, departmentLabel, jobTitleLabel;

    // Task Creation Components
    private JTextField titleField, descriptionField, assignedToField, assignedByField, managerField, statusField, feedbackField;
    private JButton createTaskButton;

    // Employee Action Components
    private JTextField usernameField;
    private JButton deleteEmployeeButton;

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
        assignedToField = new JTextField(20);
        assignedByField = new JTextField(20);
        managerField = new JTextField(20);
        statusField = new JTextField(20);
        feedbackField = new JTextField(20);
        createTaskButton = new JButton("Create Task");

        taskCreationPanel.add(new JLabel("Title:"));
        taskCreationPanel.add(titleField);
        taskCreationPanel.add(new JLabel("Description:"));
        taskCreationPanel.add(descriptionField);
        taskCreationPanel.add(new JLabel("Assigned To:"));
        taskCreationPanel.add(assignedToField);
        taskCreationPanel.add(new JLabel("Assigned By:"));
        taskCreationPanel.add(assignedByField);
        taskCreationPanel.add(new JLabel("Manager:"));
        taskCreationPanel.add(managerField);
        taskCreationPanel.add(new JLabel("Status:"));
        taskCreationPanel.add(statusField);
        taskCreationPanel.add(new JLabel("Feedback:"));
        taskCreationPanel.add(feedbackField);
        taskCreationPanel.add(createTaskButton);

        profilePanel.add(taskCreationPanel);
    }

    private void setupEmployeeActionPanel() {
        employeeActionPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        employeeActionPanel.setBorder(BorderFactory.createTitledBorder("Employee Actions"));

        usernameField = new JTextField(20);
        deleteEmployeeButton = new JButton("Delete Employee");

        employeeActionPanel.add(new JLabel("Username:"));
        employeeActionPanel.add(usernameField);
        employeeActionPanel.add(deleteEmployeeButton);

        profilePanel.add(employeeActionPanel);
    }

    private void setupTaskListPanel() {
        tasksTable = new JTable();
        scrollPane = new JScrollPane(tasksTable);
        tasksTable.setFillsViewportHeight(true);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        User user = new User("Taiwo Oso", "password", "Manager", "Taiwo", "Oso", "CS Department", "Software Engineer", "taiwo@example.com");
        new HRGUI(user);
    }
}

package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmployeeGUI extends JFrame {

    public class Task {

    }

    private JPanel mainPanel;
    private JPanel profilePanel;
    private JPanel tasksPanel;
    
    public EmployeeGUI(String username) {
        setTitle("Employee Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Load profile information
        loadProfile(username);

        // Load tasks
        loadTasks(username);

        setVisible(true);
    }

    private void loadProfile(String username) {
        // Assuming a method getEmployeeDetails(username) that returns an Employee object
        // Employee employee = getEmployeeDetails(username);
        
        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile Details"));

        profilePanel.add(new JLabel("Name: " + "Taiwo"));
        profilePanel.add(new JLabel("Department: " + "CS Department"));
        profilePanel.add(new JLabel("Role: " + "Employee"));
        profilePanel.add(new JLabel("Job Title: " + "Software Engineer"));

        mainPanel.add(profilePanel, BorderLayout.WEST);
    }

    private void loadTasks(String username) {
        // Assuming a method getTasksForEmployee(username) that returns a list of Task objects
        

        tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));

        for (int i = 0; i < 10; i++) {
            JPanel taskPanel = new JPanel();
            taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            taskPanel.add(new JLabel("Task: " + "Description"));
            taskPanel.add(new JLabel("Status: " + "Task Status"));

            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Reject");
            JButton completeButton = new JButton("Complete");
            JButton feedbackButton = new JButton("Feedback");

            // Add action listeners for the buttons
            acceptButton.addActionListener(e -> updateTaskStatus(1, "Accepted"));
            rejectButton.addActionListener(e -> updateTaskStatus(1, "Rejected"));
            completeButton.addActionListener(e -> updateTaskStatus(1, "Completed"));
            feedbackButton.addActionListener(e -> showFeedback(1));

            taskPanel.add(acceptButton);
            taskPanel.add(rejectButton);
            taskPanel.add(completeButton);
            taskPanel.add(feedbackButton);

            tasksPanel.add(taskPanel);
        }

        mainPanel.add(new JScrollPane(tasksPanel), BorderLayout.CENTER);
    }

    private void updateTaskStatus(int taskId, String status) {
        // Implement task status update logic here
    }

    private void showFeedback(int taskId) {
        // Implement feedback viewing logic here
    }

    public static void main(String[] args) {
        new EmployeeGUI("exampleUsername");
    }
}
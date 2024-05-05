package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.ArrayList;

public class EmployeeGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel profilePanel;
    private JPanel acceptedTasksPanel;
    private JPanel incomingTasksPanel;

    public EmployeeGUI(String username) {
        setTitle("Employee Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Load profile information
        loadProfile(username);

        // Initialize task panels
        acceptedTasksPanel = new JPanel();
        acceptedTasksPanel.setLayout(new BoxLayout(acceptedTasksPanel, BoxLayout.Y_AXIS));
        acceptedTasksPanel.setBorder(BorderFactory.createTitledBorder("Accepted Tasks"));

        incomingTasksPanel = new JPanel();
        incomingTasksPanel.setLayout(new BoxLayout(incomingTasksPanel, BoxLayout.Y_AXIS));
        incomingTasksPanel.setBorder(BorderFactory.createTitledBorder("Incoming Tasks"));

        // Load tasks
        loadTasks(username);

        mainPanel.add(new JScrollPane(acceptedTasksPanel), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(incomingTasksPanel), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadProfile(String username) {
        // Mockup method to return an employee
        Employee employee = getEmployeeDetails(username);

        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile Details"));

        profilePanel.add(new JLabel("Name: " + employee.getName()));
        profilePanel.add(new JLabel("Username: " + employee.getUsername()));
        profilePanel.add(new JLabel("Department: " + employee.getDepartment()));
        profilePanel.add(new JLabel("Role: " + employee.getRole()));
        profilePanel.add(new JLabel("Job Title: " + employee.getJobTitle()));

        mainPanel.add(profilePanel, BorderLayout.WEST);
    }

    private void loadTasks(String username) {
        // Mockup method to return a list of tasks
        List<Task> tasks = getTasksForEmployee(username);

        for (Task task : tasks) {
            if (task.getStatus().equals("Accepted")) {
                addTaskToPanel(task, acceptedTasksPanel, true);
            } else {
                addTaskToPanel(task, incomingTasksPanel, false);
            }
        }
    }

    private void addTaskToPanel(Task task, JPanel panel, boolean isAccepted) {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        taskPanel.add(new JLabel("Task ID: " + task.getTaskId()));
        taskPanel.add(new JLabel("Assigned By: " + task.getAssignedBy()));

        if (isAccepted) {
            JButton completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                updateTaskStatus(task.getTaskId(), "Completed");
                panel.remove(taskPanel);
                panel.revalidate();
                panel.repaint();
            });
            JButton feedbackButton = new JButton("Feedback");
            feedbackButton.addActionListener(e -> showFeedback(task.getTaskId()));

            taskPanel.add(feedbackButton);
            taskPanel.add(completeButton);
        } else {
            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Reject");
            acceptButton.addActionListener(e -> {
                updateTaskStatus(task.getTaskId(), "Accepted");
                panel.remove(taskPanel);
                addTaskToPanel(task, acceptedTasksPanel, true);
                acceptedTasksPanel.revalidate();
                acceptedTasksPanel.repaint();
                panel.revalidate();
                panel.repaint();
            });
            rejectButton.addActionListener(e -> {
                updateTaskStatus(task.getTaskId(), "Rejected");
                panel.remove(taskPanel);
                panel.revalidate();
                panel.repaint();
            });

            taskPanel.add(acceptButton);
            taskPanel.add(rejectButton);
        }

        panel.add(taskPanel);
    }

    private void updateTaskStatus(int taskId, String status) {
        // Implement task status update logic here
    }

    private void showFeedback(int taskId) {
        // Implement feedback viewing logic here
    }

    public static void main(String[] args) {
        new EmployeeGUI("ryanPark");
    }
}

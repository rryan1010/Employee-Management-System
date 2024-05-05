package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManagerGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel profilePanel;
    private JPanel managerTasksPanel;
    private JPanel acceptedTasksPanel;
    private JPanel incomingTasksPanel;
    private User user;

    public ManagerGUI(User user) {
        this.user = user;

        setTitle("Manager Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        loadProfile();

        // Initialize task panels
        JPanel tempPanel = new JPanel(new BorderLayout());

        managerTasksPanel = new JPanel();
        managerTasksPanel.setLayout(new BoxLayout(managerTasksPanel, BoxLayout.Y_AXIS));
        managerTasksPanel.setBorder(BorderFactory.createTitledBorder("Manager"));

        acceptedTasksPanel = new JPanel();
        acceptedTasksPanel.setLayout(new BoxLayout(acceptedTasksPanel, BoxLayout.Y_AXIS));
        acceptedTasksPanel.setBorder(BorderFactory.createTitledBorder("Accepted Tasks"));

        incomingTasksPanel = new JPanel();
        incomingTasksPanel.setLayout(new BoxLayout(incomingTasksPanel, BoxLayout.Y_AXIS));
        incomingTasksPanel.setBorder(BorderFactory.createTitledBorder("Incoming Tasks"));

        // Load tasks
        loadTasks();

        tempPanel.add(new JScrollPane(managerTasksPanel), BorderLayout.NORTH);
        tempPanel.add(new JScrollPane(acceptedTasksPanel), BorderLayout.SOUTH);

        mainPanel.add(new JScrollPane(tempPanel), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(incomingTasksPanel), BorderLayout.SOUTH);

        // Setting custom logo
        ImageIcon icon = new ImageIcon("images/logo.png");
        setIconImage(icon.getImage());

        setVisible(true);
    }

    private void loadProfile() {
        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile Details"));

        profilePanel.add(new JLabel("Name: " + user.getFirstName() + " " + user.getLastName()));
        profilePanel.add(new JLabel("Department: " + user.getDepartment()));
        profilePanel.add(new JLabel("Role: " + user.getRole()));
        profilePanel.add(new JLabel("Job Title: " + user.getJobTitle()));

        mainPanel.add(profilePanel, BorderLayout.WEST);
    }

    private void loadTasks() {
        List<Task> tasks = Database.getEmployeeTasks(user.getUsername());

        for (int i = 0; i < 15; i++) {
            tasks.add(new Task(i, "title", "desc", "Assigned", "Taiwo", "Kehinde", "Feedback"));
        }
        for (int i = 0; i < 5; i++) {
            tasks.get(i).setStatus("Accepted");
        }
        for (int i = 5; i < 10; i++) {
            tasks.get(i).setManager(user.getUsername());
        }

        for (Task task : tasks) {
            if (task.getStatus().equals("Accepted")) {
                addTaskToPanel(task, acceptedTasksPanel, true);
            } else if (task.getManager().equals(user.getUsername())) {
                addTaskToPanel(task, managerTasksPanel, false);
            } else {
                addTaskToPanel(task, incomingTasksPanel, false);
            }
        }
    }

    private void addTaskToPanel(Task task, JPanel panel, boolean isAccepted) {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        taskPanel.add(new JLabel("Title: " + task.getTitle()));
        taskPanel.add(new JLabel("Assigned By: " + "HR"));

        if (isAccepted) {
            JButton completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                updateTaskStatus(task.getTaskId(), "Completed");
                panel.remove(taskPanel);
                panel.revalidate();
                panel.repaint();
            });
            JButton feedbackButton = new JButton("Feedback");
            feedbackButton.addActionListener(e -> showFeedback(task.getFeedback()));

            taskPanel.add(feedbackButton);
            taskPanel.add(completeButton);
        } else if (task.getManager().equals(user.getUsername())) {
            taskPanel.add(new JLabel("Employee: "));
            JTextField employeeName = new JTextField(20);
            JButton assignButton = new JButton("Assign");
            assignButton.addActionListener(e -> {
                if (assignTask(task, employeeName.getText())) {
                    taskPanel.remove(employeeName);
                    taskPanel.remove(assignButton);
                    taskPanel.add(new JLabel(employeeName.getText()));
                    taskPanel.revalidate();
                    taskPanel.repaint();
                }
            });
            taskPanel.add(employeeName);
            taskPanel.add(assignButton);
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
        Database.updateTaskStatus(taskId, status);
    }

    private void showFeedback(String feedback) {
        JOptionPane.showMessageDialog(this, feedback);
    }

    private boolean assignTask(Task task, String username) {
        if (username.isBlank()) {
            JOptionPane.showMessageDialog(this, "Employee cannot be empty!");
            return false;
        }

        if (Database.isEmployee(username)) {
            Database.createTask(task.getTitle(), task.getDescription(), task.getStatus(),
                    username, user.getUsername(), "");
            return true;
        }

        JOptionPane.showMessageDialog(this, "Employee does not exist!");
        return false;
    }
}

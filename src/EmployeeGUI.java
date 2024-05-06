package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel profilePanel;
    private JPanel managerTasksPanel;
    private JPanel acceptedTasksPanel;
    private JPanel incomingTasksPanel;
    private User user;
    private boolean isManager = false;

    public EmployeeGUI(User user) {
        this.user = user;

        setTitle("Employee Dashboard");
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

        if (isManager) {
            tempPanel.add(new JScrollPane(managerTasksPanel), BorderLayout.NORTH);
            tempPanel.add(new JScrollPane(acceptedTasksPanel), BorderLayout.SOUTH);
    
            mainPanel.add(new JScrollPane(tempPanel), BorderLayout.CENTER);
            mainPanel.add(new JScrollPane(incomingTasksPanel), BorderLayout.SOUTH);
        } else {
            mainPanel.add(new JScrollPane(acceptedTasksPanel), BorderLayout.CENTER);
            mainPanel.add(new JScrollPane(incomingTasksPanel), BorderLayout.SOUTH);
        }

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

        JButton goBackButton = new JButton("Log Out");
        goBackButton.addActionListener(e -> {
            dispose();
            new LoginGUI();
        });

        profilePanel.add(goBackButton);

        mainPanel.add(profilePanel, BorderLayout.WEST);
    }

    private void loadTasks() {
        List<Task> tasks = Database.getManagerTasks(user.getUsername());
        if (tasks.size() > 0) isManager = true;
        tasks.addAll(Database.getEmployeeTasks(user.getUsername()));

        for (Task task : tasks) {
            if (task.getManager().equals(user.getUsername())) {
                addTaskToPanel(task, managerTasksPanel, false);
            } else if (task.getStatus().equals("Accepted")) {
                addTaskToPanel(task, acceptedTasksPanel, true);
            } else {
                addTaskToPanel(task, incomingTasksPanel, false);
            }
        }
    }

    private void addTaskToPanel(Task task, JPanel panel, boolean isAccepted) {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        taskPanel.add(new JLabel("Title: " + task.getTitle()));
        if (task.getManager().equals(user.getUsername())) {
            taskPanel.add(new JLabel("Assigned By: " + "HR"));
        } else {
            taskPanel.add(new JLabel("Assigned By: " + task.getManager()));
        }
        

        if (isAccepted) {
            JButton completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                deleteTask(task.getTaskId());
                panel.remove(taskPanel);
                panel.revalidate();
                panel.repaint();
            });
            JButton feedbackButton = new JButton("Feedback");
            feedbackButton.addActionListener(e -> showFeedback(task.getFeedback()));

            taskPanel.add(feedbackButton);
            taskPanel.add(completeButton);
        } else if (task.getManager().equals(user.getUsername())) {
            JButton editButton = new JButton("Edit");
            editButton.addActionListener(e -> {
                editTask(task);
            });
            String assignedTo = task.getAssignedTo();
            if (assignedTo == null || assignedTo.isBlank()) {
                assignedTo = "N/A";
            }
            taskPanel.add(new JLabel("Assigned To: " + assignedTo));
            taskPanel.add(editButton);
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
                mainPanel.revalidate();
                mainPanel.repaint();
            });
            rejectButton.addActionListener(e -> {
                deleteTask(task.getTaskId());
                panel.remove(taskPanel);
                panel.revalidate();
                panel.repaint();
            });
            taskPanel.add(acceptButton);
            taskPanel.add(rejectButton);
        }

        panel.add(taskPanel);
    }

    private void deleteTask(int taskId) {
        Database.deleteTask(taskId);
    }

    private void updateTaskStatus(int taskId, String status) {
        Database.updateTaskStatus(taskId, status);
    }

    private void showFeedback(String feedback) {
        if (feedback == null || feedback.isBlank()) {
            JOptionPane.showMessageDialog(this, "N/A");
        } else {
            JOptionPane.showMessageDialog(this, feedback);
        }
    }

    private void editTask(Task task) {
        new EditTaskGUI(this, "Edit Task", true, task);
        managerTasksPanel.removeAll();
        reloadManagerTasks();
        managerTasksPanel.revalidate();
        managerTasksPanel.repaint();
    }

    private void reloadManagerTasks() {
        List<Task> tasks = Database.getManagerTasks(user.getUsername());

        for (Task task : tasks) {
            addTaskToPanel(task, managerTasksPanel, false);
        }
    }
}
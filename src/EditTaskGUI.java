package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTaskGUI extends JFrame {
    private Task task;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField assignedToField;
    private JButton saveButton;
    private JButton cancelButton;

    public EditTaskGUI(Task task) {
        this.task = task;

        setTitle("Edit Task");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(initFields(), BorderLayout.CENTER);
        add(initButtons(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel initFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Title:"));
        titleField = new JTextField(task.getTitle());
        panel.add(titleField);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(task.getDescription());
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        panel.add(scrollPane);

        panel.add(new JLabel("Assigned To:"));
        assignedToField = new JTextField(task.getAssignedTo());
        panel.add(assignedToField);

        return panel;
    }

    private JPanel initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveTask();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void saveTask() {
        // Update the task details
        task.setTitle(titleField.getText());
        task.setDescription(descriptionArea.getText());
        task.setAssignedTo(assignedToField.getText());

        // Assuming a method to update the task in the database
        Database.updateTask(task);

        JOptionPane.showMessageDialog(this, "Task updated successfully!");
        dispose(); // Close the window after saving
    }
}

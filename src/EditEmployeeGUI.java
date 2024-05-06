package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditEmployeeGUI extends JDialog {
    private User user;
    private JTextField firstNameField, lastNameField, emailField, jobTitleField, departmentField;
    private JButton saveButton;
    private JButton cancelButton;

    public EditEmployeeGUI(JFrame parent, String title, boolean modal, User user) {
        super(parent, title, modal);
        createGUI(user);
    }

    private void createGUI(User user) {
        this.user = user;

        setTitle("Edit Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(initFields(), BorderLayout.CENTER);
        add(initButtons(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel initFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(user.getFirstName());
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(user.getLastName());
        panel.add(lastNameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        panel.add(emailField);

        panel.add(new JLabel("Job Title:"));
        jobTitleField = new JTextField(user.getJobTitle());
        panel.add(jobTitleField);

        panel.add(new JLabel("Department:"));
        departmentField = new JTextField(user.getDepartment());
        panel.add(departmentField);

        return panel;
    }

    private JPanel initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
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

    private void updateEmployee() {
        // Update the employee details
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setJobTitle(jobTitleField.getText());
        user.setDepartment(departmentField.getText());

        Database.updateEmployee(user);
        dispose();
    }
}

package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignupGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> comboBox;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton signupButton;
    private JTextField departmentField;
    private JTextField jobTitleField;
    private JTextField emailField;

    public SignupGUI() {
        setTitle("User Signup");
        setSize(340, 595);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(22, 1));

        JLabel titleLabel = new JLabel("Create A New Account");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 21));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        JLabel userTypeLabel = new JLabel("Type");
        String[] options = { "Employee", "HR" };
        comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(70, 130, 180));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.addActionListener(this);

        JPanel loginPanel = new JPanel(new GridBagLayout());

        JLabel label = new JLabel("Already have an account? ");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton loginButton = new JButton("Log In");
        loginButton.setForeground(new Color(70, 130, 180));
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupGUI.this.dispose();
                new LoginGUI();
                return;
            }
        });

        loginPanel.add(label);
        loginPanel.add(loginButton);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        separator.setForeground(Color.GRAY);

        panel.add(titleLabel);
        panel.add(new JLabel());
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(userTypeLabel);
        panel.add(comboBox);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel());
        panel.add(signupButton);
        panel.add(new JLabel());
        panel.add(separator);
        panel.add(loginPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel);

        // Setting custom logo
        ImageIcon icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String departmentName = departmentField.getText();
        String jobTitle = jobTitleField.getText();
        String email = emailField.getText();

        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank() || firstName.isBlank()
                || lastName.isBlank() || email.isBlank()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
            return;
        }
        if (!(password.equals(confirmPassword))) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        boolean added = Database.addUser(username, confirmPassword, (String) comboBox.getSelectedItem(), firstName,
                lastName, departmentName, jobTitle, email);
        if (!added) {
            JOptionPane.showMessageDialog(this, "Username is taken!");
            usernameField.setText("");
            return;
        }

        JOptionPane.showMessageDialog(this, "Account Created!");
        clearFields();
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }
}
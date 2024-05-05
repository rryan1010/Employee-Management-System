package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("User Login");
        setSize(595, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(11, 1));

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 21));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);

        JPanel signupPanel = new JPanel(new GridBagLayout());

        JLabel label = new JLabel("Don't have an account already? ");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setForeground(new Color(70, 130, 180));
        signupButton.setOpaque(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setBorder(BorderFactory.createEmptyBorder());
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI.this.dispose();
                new SignupGUI();
                return;
            }
        });

        signupPanel.add(label);
        signupPanel.add(signupButton);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        separator.setForeground(Color.GRAY);

        panel.add(titleLabel);
        panel.add(new JLabel());
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(new JLabel());
        panel.add(separator);
        panel.add(signupPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel);

        // Setting custom logo
        ImageIcon icon = new ImageIcon("images/logo.png");
        setIconImage(icon.getImage());

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = Database.getUser(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            usernameField.setText("");
            passwordField.setText("");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login Successful!");
        if (user.type.equals("Employee")) {
            dispose();
            new EmployeeGUI(user);
        } else if (user.type.equals("Manager")) {
            dispose();
            new ManagerGUI(user);
        } else if (user.type.equals("HR")) {
            dispose();
            new HRGUI(user);
        }
        return;
    }
}
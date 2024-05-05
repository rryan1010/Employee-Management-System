package src;

import java.sql.*;

public class Database {
    private static String url = "jdbc:sqlite:databases/database.db";

    public static void createTables() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found!");
            e.printStackTrace();
            return;
        }
        createUserTable();
        createTaskTable();
    }

    private static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL UNIQUE,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL CHECK(role IN ('Employee', 'Manager', 'HR')),"
                + "first_name TEXT NOT NULL,"
                + "last_name TEXT NOT NULL,"
                + "department TEXT,"
                + "job_title TEXT,"
                + "email TEXT NOT NULL UNIQUE)";
        connect(sql);
    }

    private static void createTaskTable() {
        String sql = "CREATE TABLE IF NOT EXISTS task ("
                + "task_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "description TEXT NOT NULL,"
                + "status TEXT NOT NULL CHECK(status IN ('Assigned', 'Accepted', 'Rejected', 'Completed')),"
                + "assigned_to INTEGER NOT NULL,"
                + "assigned_by INTEGER NOT NULL,"
                + "feedback TEXT,"
                + "FOREIGN KEY (assigned_to) REFERENCES user (user_id),"
                + "FOREIGN KEY (assigned_by) REFERENCES user (user_id));";
        connect(sql);
    }

    private static void connect(String sql) {
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();) {
            statement.execute(sql);
            System.out.println("Table created successfully!");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public static boolean addUser(String username, String password, String role, String firstName, String lastName,
            String department, String jobTitle, String email) {
        if (userExists(username))
            return false;

        String sql = "INSERT INTO user (username, password, role, first_name, last_name, department, job_title, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.setString(6, department);
            statement.setString(7, jobTitle);
            statement.setString(8, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
            return false; // Ensure we return false when an exception is caught
        }

        return true;
    }

    public static boolean deleteUser(String username) {
        if (!userExists(username)) {
            System.out.println("User does not exist.");
            return false;
        }

        String sql = "DELETE FROM user WHERE username = ?";

        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully!");
                return true;
            } else {
                System.out.println("No user was deleted.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    public static User getUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String role = resultSet.getString("role");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String departmentName = resultSet.getString("department");
                    String jobTitle = resultSet.getString("job_title");
                    User user = new User(username, password, role, firstName, lastName, departmentName, jobTitle,
                            email);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());
        }
        return null;
    }

    private static boolean userExists(String username) {
        String sql = "SELECT * FROM user WHERE LOWER(username) = LOWER(?)";

        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());
        }
        return false;
    }


    // public static void main(String[] args) {
    // deleteUser("cjpark989");
    // }


    // Method to update task status
    public static void updateTaskStatus(int taskId, String newStatus) {
        String sql = "UPDATE task SET status = ? WHERE task_id = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Task status updated successfully!");
            } else {
                System.out.println("No task was updated.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating task status: " + e.getMessage());
        }
    }

    // Method to update feedback for a task
    public static void updateTaskFeedback(int taskId, String newFeedback) {
        String sql = "UPDATE task SET feedback = ? WHERE task_id = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newFeedback);
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Task feedback updated successfully!");
            } else {
                System.out.println("No feedback was updated.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating task feedback: " + e.getMessage());
        }
    }
}
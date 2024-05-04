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
    }

    private static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL CHECK(role IN ('Employee', 'Manager', 'HR')),"
                + "first_name TEXT NOT NULL,"
                + "last_name TEXT NOT NULL,"
                + "department TEXT,"
                + "job_title TEXT,"
                + "email TEXT NOT NULL)";
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

    public static boolean adduser(String username, String password, String type, String firstName, String lastName,
            String email) {
        if (userExists(username))
            return false;

        String sql = "INSERT INTO user (username, password, type, first_name, last_name, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, type);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.setString(6, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
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
                    String type = resultSet.getString("type");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    User user = new User(username, password, type, firstName, lastName, email);
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
}
package src;

public class User {
    private String username;
    private String password;
    public String type;
    private String firstName;
    private String lastName;
    private String email;

    public User(String username, String password, String type, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String toString() {
        return username + " " + password + " " + type + " " + firstName + " " + lastName + " " + email;
    }
}

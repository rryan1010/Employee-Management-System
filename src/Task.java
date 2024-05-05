package src;

public class Task {
    String s;

    public Task(String s) {
        this.s = s;
    }

    public String getStatus() {
        return s;
    }

    public int getTaskId() {
        return 1;
    }

    public String getAssignedBy() {
        return "Admin";
    }

}

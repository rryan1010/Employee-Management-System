package src;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private String status;
    private String assignedTo;
    private String manager;
    private String feedback;

    // Constructor
    public Task(int taskId, String title, String description, String status, String assignedTo, String manager,
            String feedback) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedTo = assignedTo;
        this.manager = manager;
        this.feedback = feedback;
    }

    public Task(String title, String description, String status, String assignedTo, String manager,
            String feedback) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedTo = assignedTo;
        this.manager = manager;
        this.feedback = feedback;
    }

    // Getters and setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", assignedTo=" + assignedTo +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}

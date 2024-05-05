package src;

public class Task {
    private int taskId;
    private String description;
    private String status;
    private int assignedTo;
    private int assignedBy;
    private String feedback;

    // Constructor
    public Task(int taskId, String description, String status, int assignedTo, int assignedBy, String feedback) {
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
        this.feedback = feedback;
    }

    // Getters and setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(int assignedBy) {
        this.assignedBy = assignedBy;
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
               ", description='" + description + '\'' +
               ", status='" + status + '\'' +
               ", assignedTo=" + assignedTo +
               ", assignedBy=" + assignedBy +
               ", feedback='" + feedback + '\'' +
               '}';
    }
}

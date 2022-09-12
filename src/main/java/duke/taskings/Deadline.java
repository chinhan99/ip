package duke.taskings;


public class Deadline extends Task {

    protected String by;

    public Deadline(String taskType,String description, boolean isDone, String by) {
        super(taskType,description, isDone);
        this.by = by;
    }

    @Override
    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "Added deadline: [D] " + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String printList() {
        return "[D] [" + super.getStatusIcon() + "] " + super.description + " (by: " + by + ")";
    }
}
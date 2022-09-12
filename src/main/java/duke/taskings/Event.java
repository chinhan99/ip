package duke.taskings;

public class Event extends Task {

    protected String at;
    protected boolean isDone;

    public Event(String taskType, String description, boolean isDone, String at) {
        super(taskType, description, isDone);
        this.at = at;
    }

    @Override
    public String getAt() {
        return at;
    }

    @Override
    public String toString() {
        return "Added new event : [E] " + super.toString() + " (at: " + at + ")";
    }

    @Override
    public String printList() {
        return "[E] [" + super.getStatusIcon() + "] " + super.description + " (at: " + at + ")";
    }
}

package duke.taskings;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String printList() {
        return "[T] [" + super.getStatusIcon() + "] " + super.description;
    }

    @Override
    public String toString() {
        return "Added Todo Task : [T] " + super.toString();
    }
}

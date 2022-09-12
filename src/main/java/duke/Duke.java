package duke;

import duke.exception.DukeException;
import duke.taskings.Deadline;
import duke.taskings.Event;
import duke.taskings.Task;
import duke.taskings.Todo;
import duke.FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    /**
     * Returns the number of words within a string.
     *
     * @param input the string input from the user
     * @return the number of words in the string
     */

    public static int getNumOfWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        String[] words = input.split("\\s+");
        return words.length;
    }

    /**
     * Prints the correct number of task message when called.
     *
     * @param numOfTasks the current count of tasking
     */
    public static void printNumOfTasks(int numOfTasks) {
        if (numOfTasks == 0) {
            System.out.println("There are no tasks");
        } else if (numOfTasks == 1) {
            System.out.println("There is only 1 task!");
        } else {
            System.out.println("There are " + numOfTasks + " tasks left :( ");
        }
    }

    /**
     * Checks if the checked index is within the current taskIndex and if it is above 0.
     *
     * @param index     the checked index
     * @param arraySize the total number of entries in the tasks array.
     * @return true if the index is within the bounds. else return DukeException().
     */

    public static boolean checkIfWithinBounds(int index, int arraySize) throws DukeException {
        if ((index < 0) || (index >= arraySize)) {
            throw new DukeException();
        }
        return true;
    }


    public static void main(String[] args) {

        // array storing the Task objects.
        ArrayList<Task> tasks = new ArrayList<Task>();

        //stores the number of words in the user's string input.
        int numOfWords;


        String inData;
        Scanner scan = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Duke\n" + "What can I do for you?");
        System.out.println("Enter <help> if you need the list of commands");
        System.out.println("____________________________________________________________");
        FileOperation.initialiseFile(tasks);
        System.out.println("____________________________________________________________");

        while (true) {
            // continuously receive user input
            inData = scan.nextLine();
            numOfWords = getNumOfWords(inData);

            if (inData.equals("list")) {
                //print entire list if input is equal to "list"
                int indexNum = 1;
                System.out.println("Here are the tasks in your list:");
                if (tasks.size() == 0) {
                    printNumOfTasks(0);
                } else {
                    for (Task task : tasks) {
                        System.out.println(indexNum + ". " + task.printList());
                        indexNum += 1;
                    }
                }
                System.out.println("____________________________________________________________");

            } else if (numOfWords >= 2) {
                // check if the user input has 2 words before checking for commands "mark" , "unmark" or "delete"
                String[] parsedInput = inData.split(" ");

                if (numOfWords == 2 && parsedInput[0].equals("unmark")) {
                    // checks if user input has entered "unmark" as the first word.
                    try {
                        int unmarkedIndex = Integer.parseInt(parsedInput[1]) - 1;
                        if (checkIfWithinBounds(unmarkedIndex, tasks.size())) {
                            //checks if the user input command is within the current bound of the tasks array.
                            tasks.get(unmarkedIndex).setDone(false);

                            System.out.println("OK, I've marked this task as NOT done yet:");
                            System.out.println("[" + tasks.get(unmarkedIndex).getTaskType() + "]" + "[" + tasks.get(unmarkedIndex).getStatusIcon() + "] " + tasks.get(unmarkedIndex).getDescription());
                            System.out.println("____________________________________________________________");
                            FileOperation.writeToFile(tasks);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid unmark command - non numeric index detected.");
                        System.out.println("____________________________________________________________");
                    } catch (DukeException e) {
                        System.out.println("Invalid unmark command - Index out of bounds.");
                        System.out.println("____________________________________________________________");
                    }

                } else if (numOfWords == 2 && parsedInput[0].equals("mark")) {
                    // checks if user input has entered "mark" as the first word.
                    try {
                        int markedIndex = Integer.parseInt(parsedInput[1]) - 1;
                        if (checkIfWithinBounds(markedIndex, tasks.size())) {
                            // checks if the user input command is within the current bound of the tasks array.
                            tasks.get(markedIndex).setDone(true);
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("[" + tasks.get(markedIndex).getTaskType() + "]" + "[" + tasks.get(markedIndex).getStatusIcon() + "] " + tasks.get(markedIndex).getDescription());
                            System.out.println("____________________________________________________________");
                            FileOperation.writeToFile(tasks);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid mark command - non numeric index detected.");
                        System.out.println("____________________________________________________________");
                    } catch (DukeException e) {
                        System.out.println("Invalid mark command - Index out of bounds.");
                        System.out.println("____________________________________________________________");
                    }
                } else if (numOfWords == 2 && parsedInput[0].equals("delete")) {
                    try {
                        int deleteIndex = Integer.parseInt(parsedInput[1]) - 1;
                        if (checkIfWithinBounds(deleteIndex, tasks.size())) {
                            // checks if the user input command is within the current bound of the tasks array.

                            System.out.println("Noted. I've removed this task:");
                            System.out.println("[" + tasks.get(deleteIndex).getTaskType() + "]" + "[" + tasks.get(deleteIndex).getStatusIcon() + "] " + tasks.get(deleteIndex).getDescription());
                            tasks.remove(deleteIndex);
                            FileOperation.writeToFile(tasks); // to update the file with the updated tasks array
                            if (tasks.size() == 0) {
                                printNumOfTasks(0);
                            } else {
                                printNumOfTasks(tasks.size());
                            }
                            FileOperation.writeToFile(tasks);

                            System.out.println("____________________________________________________________");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid delete command - non numeric index detected.");
                        System.out.println("____________________________________________________________");
                    } catch (DukeException e) {
                        System.out.println("Invalid delete command - Index out of bounds.");
                        System.out.println("____________________________________________________________");
                    }

                } else {

                    // Synthesized array after removing the command input.
                    StringBuilder synthesizedArr = new StringBuilder();
                    for (int i = 1; i < numOfWords; i += 1) {
                        synthesizedArr.append(parsedInput[i]).append(" ");
                    }
                    String taskDescription = synthesizedArr.toString();

                    switch (parsedInput[0]) {
                    case "todo":

                        tasks.add(new Todo("T", taskDescription, false));
                        FileOperation.writeToFile(tasks);
                        System.out.println(tasks.get(tasks.size()-1));
                        printNumOfTasks(tasks.size());

                        break;
                    case "deadline":
                        // find "/" break point before processing the description and the deadline
                        String deadline = taskDescription;
                        if (taskDescription.contains("/by")) {
                            //update taskDescription and deadline
                            String[] tempArray = taskDescription.split("/by");
                            taskDescription = tempArray[0];
                            deadline = tempArray[1];
                        }


                        tasks.add(new Deadline("D", taskDescription, false, deadline));
                        FileOperation.writeToFile(tasks);
                        System.out.println(tasks.get(tasks.size()-1));
                        printNumOfTasks(tasks.size());

                        break;

                    case "event":
                        String eventPeriod = taskDescription;
                        if (taskDescription.contains("/at")) {
                            //update taskDescription and deadline
                            String[] tempArray = taskDescription.split("/at");
                            taskDescription = tempArray[0];
                            eventPeriod = tempArray[1];
                        }

                        tasks.add(new Event("E", taskDescription, false, eventPeriod));
                        FileOperation.writeToFile(tasks);
                        System.out.println(tasks.get(tasks.size()-1));
                        printNumOfTasks(tasks.size());

                        break;
                    default:
                        System.out.println("Unrecognised command");
                        System.out.println("____________________________________________________________");
                        break;
                    }
                }
            } else {
                if (inData.equals("bye")) {
                    // exits the while loop if the user inputs is equal to "bye"
                    break;
                } else if (inData.equals("help")) {
                    System.out.println("Available commands:");
                    System.out.println("1) todo ");
                    System.out.println("2) event, use </at> to indicate time period");
                    System.out.println("3) deadline use </by> to indicate deadline");
                    System.out.println("4) mark <item number>  ");
                    System.out.println("5) unmark <item number>)");
                    System.out.println("6) list ");
                    System.out.println("7) bye ");
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("Unrecognised command");
                    System.out.println("____________________________________________________________");
                }
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

    }
}

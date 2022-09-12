package duke;

import duke.taskings.Task;
import duke.taskings.Todo;
import duke.taskings.Event;
import duke.taskings.Deadline;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;


public class FileOperation {

    /**
     * to check if file exists, then start storing file inputs back into the current tasks array
     *
     * @param tasks the array which we would be inserting entries into
     */
    public static void initialiseFile(ArrayList<Task> tasks) {
        try {
            File file = manageFile();
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String[] parsedStoredTask = input.nextLine().split(" \\| ");
                switch (parsedStoredTask[0]) {
                case "T":
                    if (parsedStoredTask[1].equals("X")) {
                        tasks.add(new Todo("T", parsedStoredTask[2], true));
                    } else {
                        tasks.add(new Todo("T", parsedStoredTask[2], false));
                    }
                    break;
                case "E":
                    if (parsedStoredTask[1].equals("X")) {
                        tasks.add(new Event("E", parsedStoredTask[2], true, parsedStoredTask[3]));
                    } else {
                        tasks.add(new Event("E", parsedStoredTask[2], false, parsedStoredTask[3]));
                    }
                    break;
                case "D":
                    if (parsedStoredTask[1].equals("X")) {
                        tasks.add(new Deadline("D", parsedStoredTask[2], true, parsedStoredTask[3]));
                    } else {
                        tasks.add(new Deadline("D", parsedStoredTask[2], false, parsedStoredTask[3]));
                    }
                    break;
                default:
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

    }


    /**
     * called when file does not exist and creates a new file
     *
     * @return file when new file is created and null if no file is created.
     */
    public static File createFile() {
        try {
            File file = new File("data/dukefile.txt");
            file.createNewFile();
            return file;
        } catch (Exception e) {
            System.out.println("File creation error occurred.");
        }
        return null;
    }

    /**
     * called to check on the existance of directory and file. Function creates missing directory and files when necessary.
     *
     * @return file after ensuring making sure directory and file is set.
     */
    public static File manageFile() {

        File directory = new File("data");
        File file = new File("data/dukefile.txt");
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Creating new directory");
        }
        if (!file.exists()) {
            file = createFile();
            System.out.println("* Created new file for use *");
        } else {
            System.out.println("* Existing file is loaded and ready to be used *");
        }
        return file;
    }

    /**
     * updates dukefile.txt when called for each valid user command which requires updating of the tasks array.
     *
     * @param tasks the array containing the entries which would be converted to text format.
     */
    public static void writeToFile(ArrayList<Task> tasks) {
        try {
            FileWriter file = new FileWriter("data/dukefile.txt");
            String textToWrite = "";
            for (Task task : tasks) {
                switch (task.getTaskType()) {
                case "D":
                    textToWrite = String.format(task.getTaskType() + " | " + task.getStatusIcon() + " | " + task.getDescription() + " | " + task.getBy());

                    break;
                case "E":
                    textToWrite = String.format(task.getTaskType() + " | " + task.getStatusIcon() + " | " + task.getDescription() + " | " + task.getAt());

                    break;
                case "T":
                    textToWrite = String.format(task.getTaskType() + " | " + task.getStatusIcon() + " | " + task.getDescription());
                    break;
                default:
                    break;
                }
                file.write(textToWrite + System.lineSeparator());
            }
            file.close();

        } catch (IOException e) {
            System.out.println("Cannot write into file.");
        }

    }

}
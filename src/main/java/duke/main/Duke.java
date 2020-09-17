package duke.main;

import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Duke {
    static ArrayList<Task> taskList = new ArrayList<Task>();
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String userInput;

        System.out.println("Hello from\n" + LOGO);
        printOutput("Hello! I'm Duke\n" +
                "What can I do for you?", true);
        // Attempt to load the saved data
        try {
            FileInputStream fis = new FileInputStream("data.duke");
            ObjectInputStream ois = new ObjectInputStream(fis);
            taskList = (ArrayList<Task>) ois.readObject();
            ois.close();
            fis.close();
            printOutput("I found your saved list of tasks and successfully loaded them!", true);
        } catch (FileNotFoundException e) {
            printOutput("I couldn't find any previously saved data.", true);
        } catch (IOException e) {
            e.printStackTrace();
            printOutput("I couldn't read your saved data! Did you give me read permissions?", true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            printOutput("I couldn't parse your saved data! Could it be corrupted?", true);
        }
        while (true) {
            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                listTasks(userInput);
            } else if (userInput.toLowerCase().startsWith("done ")) {
                setDone(userInput);
            } else if (userInput.toLowerCase().startsWith("deadline")) {
                createDeadline(userInput);
            } else if (userInput.toLowerCase().startsWith("event")) {
                createEvent(userInput);
            } else if (userInput.toLowerCase().startsWith("todo")) {
                createTodo(userInput);
            } else if (userInput.toLowerCase().startsWith("delete")) {
                deleteTask(userInput);
            } else {
                printOutput("Commands: bye list delete todo deadline event", false);
            }
            writeToFile();
        }
        printOutput("Bye. Hope to see you again soon!", true);
    }

    /**
     * Lists all the tasks that are currently available
     * @param userInput The string supplied by the user to be interpreted
     */
    private static void listTasks(String userInput) {
        if (taskList.size() == 0) {
            printOutput("Oops the list is empty!", false);
            return;
        }
        String taskString = "";
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            taskString += (i + 1) + ". " + t.toString() + System.lineSeparator();
        }
        printOutput(taskString, false);
    }

    /**
     * Marks the given task as done.
     * @param userInput The string supplied by the user to be interpreted
     */
    private static void setDone(String userInput) {
        try {
            String taskStr = userInput.substring(4).strip();
            Task t = taskList.get(Integer.parseInt(taskStr) - 1);
            t.setDone(true);
            printOutput("Nice! I've marked this task as done:\n\t" + t.toString(), false);
        } catch (NumberFormatException e) {
            printOutput("Oops! Please enter a number for the task!", true);
        } catch (IndexOutOfBoundsException e) {
            printOutput("Oops! We couldn't find that entry in the list!", true);
        } catch (Exception e) {
            printOutput("Oops! That didn't work, please check your input and try again!", true);
        }
    }

    /**
     * Creates a new deadline task
     * @param userInput The string supplied by the user to be interpreted
     */
    private static void createDeadline(String userInput) {
        if (!userInput.toLowerCase().contains("/by")) {
            printOutput("usage: deadline <name of item> /by <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
            return;
        }
        String[] taskArgs = userInput.substring(8).strip().split("/by");
        try {
            Deadline d = new Deadline(taskArgs[0].strip(), taskArgs[1].strip());
            taskList.add(d);
            printOutput("Added new deadline: " + taskArgs[0] + System.lineSeparator() + "\tDue by: " + d.getDue());
        } catch (DukeException de) {
            printOutput("Unable to parse date! Please try again.", true);
            return;
        }
    }

    /**
     * Creates a new ToDo task
     * @param userInput The string supplied by the user to be interpreted
     */
    private static void createTodo(String userInput) {
        String taskString = userInput.substring(4).strip();
        ToDo t = new ToDo(taskString);
        taskList.add(t);
        printOutput("Added new todo: " + t.toString());
    }

    /**
     * Creates a new event
     * @param userInput The string supplied by the user to be interpreted
     */
    private static void createEvent(String userInput) {
        if (!userInput.toLowerCase().contains("/at")) {
            printOutput("usage: event <name of item> /at <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
            return;
        }
        String[] taskArgs = userInput.substring(5).strip().split("/at");
        try {
            Event e = new Event(taskArgs[0].strip(), taskArgs[1].strip());
            taskList.add(e);
            printOutput("Added new event: " + taskArgs[0] + System.lineSeparator() + "\tStart at: " + e.getStart());
        } catch (DukeException de) {
            printOutput("Unable to parse date! Please try again.", true);
            return;
        }
    }

    private static void deleteTask(String userInput) {
        try {
            String taskStr = userInput.substring(6).strip();
            Task t = taskList.get(Integer.parseInt(taskStr) - 1);

            t.setDone(true);
            printOutput("Noted! I've removed this task from the list:\n\t" + t.toString(), false);
            taskList.remove(t);
        } catch (NumberFormatException e) {
            printOutput("Oops! Please enter a number for the task!", true);
        } catch (IndexOutOfBoundsException e) {
            printOutput("Oops! We couldn't find that entry in the list!", true);
        } catch (Exception e) {
            printOutput("Oops! That didn't work, please check your input and try again!", true);
        }
    }

    private static void writeToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("data.duke");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(taskList);
            oos.close();
        } catch (FileNotFoundException e) {
            printOutput("I couldn't save your data to file! Did you give me write permissions?", true);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            printOutput("I couldn't perform the write operation!", true);
        }
    }

    private static void printOutput(String text) {
        printOutput(text, false);
    }

    /**
     * Prints the output with the divider lines and the supplied text
     * Option to make the text non instant for extra effect
     * @param text string to be printed
     * @param isInstant whether the string is printed instantly
     */
    private static void printOutput(String text, boolean isInstant) {
        final String UNDERSCORES = "____________________________________________________________";
        System.out.println(UNDERSCORES);
        // Split text according to the lines to format.
        String[] lines = text.split("\\r?\\n");
        for (String s : lines) {
            if (isInstant) {
                System.out.println("  " + s);
            } else {
                System.out.print("  ");
                // charAt is constant time lookup so we do that instead of splitting strings.
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    System.out.print(c);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(System.lineSeparator());
            }
        }
        System.out.println(UNDERSCORES);
    }
}

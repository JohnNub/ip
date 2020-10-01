package duke.main;

import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static ArrayList<Task> taskList = new ArrayList<>();
    private Scanner in;
    private boolean shutdown = false;
    private Parser parser;
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    /**
     * Initializes a new UI and prints greeting message
     * @param taskList The Task List if any saved data was found
     */
    public Ui(ArrayList<Task> taskList) {
        this.taskList = taskList;
        in = new Scanner(System.in);
        parser = new Parser();
        System.out.println("Hello from\n" + LOGO);
        printOutput("Hello! I'm Duke\n" +
                "What can I do for you?", true);
    }

    /**
     * UI Execution routine. Fetches command from scanner, calls parser and sends the command to the correct function
     */
    public void run() {
        String inStr = in.nextLine();
        UserInput userInput = parser.parse(inStr);
        if (userInput.getCommand().equalsIgnoreCase("bye")) {
            shutdown = true;
            return;
        } else if (userInput.getCommand().equalsIgnoreCase("list")) {
            listTasks(userInput);
        } else if (userInput.getCommand().equalsIgnoreCase("done")) {
            setDone(userInput);
        } else if (userInput.getCommand().equalsIgnoreCase("deadline")) {
            createDeadline(userInput);
        } else if (userInput.getCommand().equalsIgnoreCase("event")) {
            createEvent(userInput);
        } else if (userInput.getCommand().equalsIgnoreCase("todo")) {
            createTodo(userInput);
        } else if (userInput.getCommand().equalsIgnoreCase("delete")) {
            deleteTask(userInput);
        } else if (userInput.getCommand().equalsIgnoreCase("find")) {
            findTask(userInput);
        } else {
            printOutput("Commands: bye list done delete todo deadline event find", false);
        }
    }

    /**
     * Lists all the tasks that are currently available
     * @param userInput UserInput object for command processing (Args ignored for this command)
     */
    private static void listTasks(UserInput userInput) {
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
     * Marks the given task as done. requires key ""
     * @param userInput UserInput object for command processing
     */
    private static void setDone(UserInput userInput) {
        if (userInput.getNumArgs() < 1) {
            printOutput("Oops! You didn't provide at least 1 argument!", true);
            return;
        }
        try {
            String arg = userInput.getArg("");
            Task t = taskList.get(Integer.parseInt(arg) - 1);
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
     * Creates a new deadline task. Requires key "" and "by"
     * @param userInput UserInput object for command processing
     */
    private static void createDeadline(UserInput userInput) {
        if (userInput.getNumArgs() < 2) {
            printOutput("Oops! You didn't provide at least 2 arguments!", true);
            return;
        }
        String by = userInput.getArg("by");
        if (by == null || by.equals("")) {
            printOutput("usage: deadline <name of item> /by <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
            return;
        }
        String base = userInput.getArg("");
        if (base == null || base.equals("")) {
            printOutput("Oops! Please provide a name for the deadline!", true);
            return;
        }
        try {
            Deadline d = new Deadline(base, by);
            taskList.add(d);
            printOutput("Added new deadline: " + d.getDescription() + System.lineSeparator()
                    + "\tDue by: " + d.getDue());
        } catch (DukeException de) {
            printOutput("Unable to parse date! Please try again.", true);
        }
    }

    /**
     * Creates a new ToDo task. Requires key ""
     * @param userInput UserInput object for command processing
     */
    private static void createTodo(UserInput userInput) {
        if (userInput.getNumArgs() < 1) {
            printOutput("Oops! You didn't provide at least 1 argument!", true);
            return;
        }
        String taskString = userInput.getArg("");
        if (taskString == null || taskString.equals("")) {
            printOutput("Oops! Please provide a name for the ToDo!", true);
            return;
        }
        ToDo t = new ToDo(taskString);
        taskList.add(t);
        printOutput("Added new todo: " + t.toString());
    }

    /**
     * Creates a new event. Requires key "", "at" and "to"
     * @param userInput UserInput object for command processing
     */
    private static void createEvent(UserInput userInput) {
        if (userInput.getNumArgs() < 3) {
            printOutput("Oops! You didn't provide at least 3 arguments!", true);
            return;
        }
        String at = userInput.getArg("at");
        if (at == null || at.equals("")) {
            printOutput("usage: event <name of item> /at <dd-MMM-yyyy HH:mm:ss OR next [weekday]> "
                    + "/to <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
            return;
        }
        String base = userInput.getArg("");
        if (base == null || base.equals("")) {
            printOutput("Oops! Please provide a name for the deadline!", true);
            return;
        }
        String to = userInput.getArg("to");
        if (to == null || to.equals("")) {
            printOutput("usage: event <name of item> /at <dd-MMM-yyyy HH:mm:ss OR next [weekday]> "
                    + "/to <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
            return;
        }
        try {
            Event e = new Event(base, at, to);
            taskList.add(e);
            printOutput("Added new event: " + e.getDescription() + System.lineSeparator()
                    + "\tStart at: " + e.getStart() + " Ends : " + e.getEnd());
        } catch (DukeException de) {
            printOutput("Unable to parse date! Please try again.", true);
        }
    }

    /**
     * Deletes the tasks by index. Requires key ""
     * @param userInput UserInput object for command processing
     */
    private static void deleteTask(UserInput userInput) {
        if (userInput.getNumArgs() < 1) {
            printOutput("Oops! You didn't provide at least 1 argument!", true);
            return;
        }
        try {
            String arg = userInput.getArg("");
            Task t = taskList.get(Integer.parseInt(arg) - 1);

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

    /**
     * Finds the task specified by the user. Requires key ""
     * @param userInput UserInput object for command processing
     */
    private static void findTask(UserInput userInput) {
        if (userInput.getNumArgs() < 1) {
            printOutput("Oops! You didn't provide at least 1 argument!", true);
            return;
        }
        if (taskList.size() == 0) {
            printOutput("Oops the list is empty!", false);
            return;
        }
        String base = userInput.getArg("");
        if (base == null || base.equals("")) {
            printOutput("Oops! Please provide a string to search for!", true);
            return;
        }
        String taskString = "";
        int count = 1;
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            if (t.getDescription().toLowerCase().contains(base.toLowerCase())) {
                taskString += (i + 1) + ". " + t.toString() + System.lineSeparator();
            }
        }
        if(taskString.equals("")){
            taskString = "Oops! There were no tasks matching your specification!";
        }
        printOutput(taskString, false);
    }

    /**
     * Returns if the loop should exit
     * @return true if the program should terminate
     */
    public boolean shouldShutdown() {
        return shutdown;
    }

    /**
     * Utility function for printing errors triggered by other classes
     * @param text The string to be printed
     */
    public void printError(String text) {
        printOutput(text, true);
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

import java.util.ArrayList;
import java.util.Scanner;


public class Duke {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<Task>();
        String userInput;
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        printOutput("Hello! I'm Duke\n" +
                "What can I do for you?", true);
        while (true) {
            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (taskList.size() == 0) {
                    printOutput("Oops the list is empty!", false);
                    continue;
                }
                String tl = "";
                for (int i = 0; i < taskList.size(); i++) {
                    Task t = taskList.get(i);
                    tl += (i + 1) + ". " + t.toString() + System.lineSeparator();
                }
                printOutput(tl, false);
            } else if (userInput.toLowerCase().startsWith("done ")) {
                String taskStr = userInput.substring(4).strip();
                try {
                    Task t = taskList.get(Integer.parseInt(taskStr) - 1);
                    t.setDone(true);
                    printOutput("Nice! I've marked this task as done:\n\t" + t.toString(), false);
                } catch (Exception e) {
                    printOutput("Oops! That didn't work, please check your input and try again!", true);
                }
            } else if (userInput.toLowerCase().startsWith("deadline")) {
                if (!userInput.toLowerCase().contains("/by")) {
                    printOutput("usage: deadline <name of item> /by <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
                    continue;
                }
                String[] taskArgs = userInput.substring(8).strip().split("/by");

                Deadline d = new Deadline(taskArgs[0].strip(), taskArgs[1].strip());
                if (d.getDue() == null) {
                    printOutput("Unable to parse date! Please try again.", true);
                    continue;
                }
                taskList.add(d);
                printOutput("Added new deadline: " + taskArgs[0] + System.lineSeparator() + "\tDue by: " + d.getDue());
            } else if (userInput.toLowerCase().startsWith("event")) {
                if (!userInput.toLowerCase().contains("/at")) {
                    printOutput("usage: event <name of item> /at <dd-MMM-yyyy HH:mm:ss OR next [weekday]>", true);
                    continue;
                }
                String[] taskArgs = userInput.substring(5).strip().split("/at");

                Event e = new Event(taskArgs[0].strip(), taskArgs[1].strip());
                if (e.getStart() == null) {
                    printOutput("Unable to parse date! Please try again.", true);
                    continue;
                }
                taskList.add(e);
                printOutput("Added new event: " + taskArgs[0] + System.lineSeparator() + "\tStart at: " + e.getStart());
            } else if (userInput.toLowerCase().startsWith("todo")) {
                String taskString = userInput.substring(4).strip();
                ToDo t = new ToDo(taskString);
                taskList.add(t);
                printOutput("Added new todo: " + t.toString());
            } else {
                printOutput("Commands: bye list todo deadline event", false);
            }
        }
        printOutput("Bye. Hope to see you again soon!", true);
    }


    private static void printOutput(String text) {
        printOutput(text, false);
    }

    /**
     * Prints the output with the divider lines and the supplied text
     * Option to make the text non instant for extra effect
     * @param text string to be printed
     * @param instant whether the string is printed instantly
     */
    private static void printOutput(String text, boolean instant) {
        System.out.println("____________________________________________________________");
        //Split text according to the lines to format
        String[] lines = text.split("\\r?\\n");
        for (String s : lines) {
            if (instant) {
                System.out.println("  " + s);
            } else {
                System.out.print("  ");
                //charAt is constant time lookup so we do that instead of splitting strings
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
        System.out.println("____________________________________________________________");
    }
}

import java.util.ArrayList;
import java.util.Arrays;
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
                "What can I do for you?",true);
        while (true) {
            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (taskList.size() == 0) {
                    printOutput("Oops the list is empty!",false);
                    continue;
                }
                String tl = "";
                for (int i = 0; i < taskList.size(); i++) {
                    Task t = taskList.get(i);
                    tl+= (i + 1) + ". " + t.toString()+System.lineSeparator();
                }
                printOutput(tl,false);
            } else if(userInput.toLowerCase().startsWith("done")) {
                String taskStr = userInput.substring(4).strip();
                try {
                    Task t = taskList.get(Integer.parseInt(taskStr)-1);
                    t.setDone(true);
                    printOutput("Nice! I've marked this task as done:\n\t"+t.toString(),false);
                } catch (Exception e){
                    printOutput("Oops! That didn't work, please check your input and try again!",true);
                }
            } else {
                taskList.add(new Task(userInput));
                printOutput("added: " + userInput,false);
            }
        }
        printOutput("Bye. Hope to see you again soon!",true);
    }

    /**
     * Prints the output with the divider lines and the supplied text
     * Option to make the text non instant for extra effect
     * @param text string to be printed
     * @param instant whether the string is printed instantly
     */
    private static void printOutput(String text,boolean instant){
        System.out.println("____________________________________________________________");
        //Split text according to the lines to format
        String[] lines = text.split("\\r?\\n");
        for(String s:lines) {
            if (instant) {
                System.out.println("  " + s);
            } else {
                System.out.print("  ");
                //charAt is constant time lookup so we do that instead of splitting strings
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    System.out.print(c);
                    try {
                        Thread.sleep(20);
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

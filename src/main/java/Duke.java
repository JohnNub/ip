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
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Duke\n" +
                " What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (taskList.size() == 0) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Oops the list is empty!");
                    System.out.println("____________________________________________________________");
                    continue;
                }
                System.out.println("____________________________________________________________");
                for (int i = 0; i < taskList.size(); i++) {
                    Task t = taskList.get(i);
                    System.out.println(" " + (i + 1) + ". " + t.description);
                }
                System.out.println("____________________________________________________________");
            } else {
                taskList.add(new Task(userInput));
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}

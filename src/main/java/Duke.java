import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
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
        while(true){
            userInput = in.nextLine();
            if(userInput.equalsIgnoreCase("bye")){
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println(" "+userInput);
            System.out.println("____________________________________________________________");
        }
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}

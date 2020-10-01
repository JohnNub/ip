package duke.main;

import duke.tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Duke {


    public static void main(String[] args) {
        Storage s = new Storage("data.duke");
        ArrayList<Task> taskList = new ArrayList<>();

        // Attempt to load the saved data
        String outcome = "";
        try {
            taskList = s.read();
            outcome = "I found your saved list of tasks and successfully loaded them!";
        } catch (FileNotFoundException e) {
            outcome = "I couldn't find any previously saved data.";
        } catch (IOException e) {
            e.printStackTrace();
            outcome = "I couldn't read your saved data! Did you give me read permissions?";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            outcome = "I couldn't parse your saved data! Could it be corrupted?";
        }
        Ui ui = new Ui(taskList);
        ui.printError(outcome);
        while (!ui.shouldShutdown()) {
            ui.run();
            writeToFile(s,taskList);
        }
        ui.printError("Bye. Hope to see you again soon!");
    }



    private static String writeToFile(Storage s, ArrayList<Task> taskList) {
        try {
            s.save(taskList);
            return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "I couldn't save your data to file! Did you give me write permissions?";
        } catch (IOException e) {
            e.printStackTrace();
            return "I couldn't perform the write operation!";
        }
    }


}

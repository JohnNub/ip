package duke.main;

import duke.tasks.Task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
    String path;

    public Storage(String path) {
        this.path = path;
    }

    /**
     * Reads the saved file and returns the arraylist of tasks
     * @return ArrayList containing tasks if file is found
     * @throws IOException File is not found or no permission to read
     * @throws ClassNotFoundException Error with casting the serialized object to class
     */
    public ArrayList<Task> read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Task> taskList = (ArrayList<Task>) ois.readObject();
        ois.close();
        fis.close();
        return taskList;
    }

    /**
     * Save the provided arraylist of task to file
     * @param taskList ArrayList containing tasks to be saved
     * @throws IOException Error performing write
     */
    public void save(ArrayList<Task> taskList) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(taskList);
        oos.close();
    }
}

package duke.main;

import duke.tasks.Task;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    String path;
    public Storage(String path){
        this.path = path;
    }
    public ArrayList<Task> read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Task> taskList = (ArrayList<Task>) ois.readObject();
        ois.close();
        fis.close();
        return taskList;
    }
    public void save(ArrayList<Task> taskList) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(taskList);
        oos.close();
    }
}

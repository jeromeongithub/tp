package seedu.todolist.storage;

import seedu.todolist.exception.FailedLoadException;
import seedu.todolist.exception.FailedSaveException;
import seedu.todolist.task.TaskList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//@@author jeromeongithub
/**
 * A class for saving the task list as a text file and loading it.
 */
public class Storage {
    public static final String DEFAULT_SAVE_PATH = "./data.txt";
    private boolean isNewSave;
    private File file;

    public Storage(String filepath) {
        assert filepath != null : "NULL filepath was given";
        file = new File(filepath);
        isNewSave = !file.exists();
    }

    public boolean isNewSave() {
        return isNewSave;
    }

    /**
     * Writes the current task list to the local save file.
     *
     * @param taskList The task list being saved.
     * @throws FailedSaveException if an error occurs while writing to the save file.
     */
    public void saveData(TaskList taskList) throws FailedSaveException {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(taskList);
        } catch (Exception e) {
            throw new FailedSaveException();
        }
    }

    /**
     * Loads the task list from the local save file, if it exists.
     *
     * @return The task list read from the save file, if it exists.
     * @throws FailedLoadException if an error occurs while reading from the save file.
     */
    public TaskList loadData() throws FailedLoadException {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (TaskList) ois.readObject();
        } catch (Exception e) {
            throw new FailedLoadException();
        }
    }
}

package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.ToDoListException;
import seedu.duke.ui.Ui;
import seedu.duke.task.TaskList;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;

class DeleteTaskCommandTest {
    private CommandParser parser = new CommandParser();
    private TaskList testList;
    private Ui ui = new Ui();

    private HashMap<String, String> generateInputArguments(String index) {
        HashMap<String, String> args = new HashMap<>();
        args.put(AddTaskCommand.KEYWORD, index);
        return args;
    }

    @BeforeEach
    void setUp() {
        final String SAMPLE_TASK = "add something -d 06-04-2000 23:59";
        testList = new TaskList();
        try {
            parser.parseCommand(SAMPLE_TASK).execute(testList, ui);
        } catch (ToDoListException e) {
            fail("Failed to initialize test task list");
        }
    }

    @Test
    public void deleteTask_emptyIndex_throwsException() {
        try {
            Command testDelete = new DeleteTaskCommand(generateInputArguments(""));
            testDelete.execute(testList, ui);
        } catch (ToDoListException e) {
            return;
        }
        fail("A delete command was successfully constructed with missing index");
    }

    @Test
    public void deleteTask_invalidIndex_throwsException() {
        final String[] invalidIndex = {"1000", "-1", "52.1"};
        for (String index : invalidIndex) {
            try {
                Command testDelete = new DeleteTaskCommand(generateInputArguments(index));
                testDelete.execute(testList, ui);
            } catch (ToDoListException e) {
                continue;
            }
            fail("A delete command was successfully constructed with invalid index: " + index);
        }
    }
}
package seedu.todolist.logic.command;

import org.junit.jupiter.api.Test;
import seedu.todolist.exception.ToDoListException;
import seedu.todolist.logic.Parser;
import seedu.todolist.model.Priority;
import seedu.todolist.model.TaskList;
import seedu.todolist.ui.Ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.fail;

class ListFullInfoCommandTest {
    private Parser parser = new Parser();
    private TaskList testList;
    private Ui ui = new Ui();

    @Test
    void execute_oneID_expectFullInfo() {
        LocalDate date = LocalDate.of(2023,3,10);
        LocalTime time = LocalTime.of(15,30,45);
        LocalDateTime localDateTime = LocalDateTime.of(date,time);
        TreeSet<String> tags = new TreeSet<>();
        tags.add("first");
        testList = new TaskList();
        testList.addTask("test1", localDateTime, "tester@gmail.com", tags, 0, Priority.HIGH);
        try {
            parser.parseCommand("info 1").execute(testList, null, ui);
        } catch (ToDoListException e) {
            fail("Unable to list all information with regard to task ID: 1");
        }
    }
}

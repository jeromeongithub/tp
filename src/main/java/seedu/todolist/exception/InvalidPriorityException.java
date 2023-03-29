package seedu.todolist.exception;

import seedu.todolist.constants.Errors;
import seedu.todolist.constants.Messages;

public class InvalidPriorityException extends ToDoListException{
    public InvalidPriorityException (String priority) {
        super(Errors.INVALID_PRIORITY.getMessage() + priority + System.lineSeparator()
                + Messages.PRIORITY_LEVELS.getMessage());
    }

}
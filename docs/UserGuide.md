# User Guide

* Table of Contents
{:toc}

## Introduction

With the transition to Canvas, the most important feature of LumiNUS’s deadline reminders is gone! **NUS To-Do List** aims to
bring an application to keep you aware of your deadlines and not miss them.

## Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest version of `NUS To-Do List` from [here](https://github.com/AY2223S2-CS2113-T11-4/tp/releases/tag/v2.1).
3. Run `java -jar NUSTo-DoList.jar` in a terminal to start the program.

## Command Format

### Flags

- Words with a dash in front such as `-due` or `-email`, are considered as flags. Hence, [parameters](#parameters) like the description or tags cannot start with a dash.
- Each command has its own flags that need to be provided when running the command, some of which are mutually exclusive.
- A typo in a flag may cause it to be recognised as a parameter instead of a flag.
- You **cannot** repeat the same flag (`-edit abc -edit def`) for a command.
- Flags in square brackets `[]` are optional. (Do not include the brackets in the command)

### Parameters

- Words in `UPPER_CASE` are parameters to be supplied by the user.
- Parameters cannot start with a dash `-`. For example, `add -random_name` is an invalid command.
- Flags and their parameters can be provided in any order after the command name and its parameter. For example, instead of `add DESCRIPTION -due DATE -email ADDRESS` you can also use `add DESCRIPTION -email ADDRESS -due DATE`.
- Unnecessary parameters for commands/flags that do not need them are ignored. For example, `exit 123` is equivalent to `exit`.

#### Restrictions

Given below is list of task attributes and their restrictions.

- `DESCRIPTION` A task must have a non-empty description.
  - Examples: `Hello yah`, `pi is roughly 3`
- `DEADLINE` The date-time format is `dd/mm/yyyy hh:mm` or `dd-mm-yyyy hh:mm`.
  - When adding a task or editing a deadline, the `DEADLINE` cannot be in the past.
  - Examples: `14/03/2025 16:40`, `29/02/2024 06:39`
- `EMAIL_ADDRESS` must be of a valid email address format. (We do not check if the mailbox actually exists.)
  - Examples: `abc@def.com`, `this-is-valid-@too.com`
- `LIST_OF_TAGS` consists of one or more tags, separated by a space. A task cannot have multiple of the same tag.
  - Tags will be sorted in lexicographic order.
  - Examples: `difficult later 3.14`, `ALSO_A_TAG`
- `REPEAT_TIMES` must be an integer from 0 to 2147483647, and can only be used if the task also has a `DEADLINE`.
  - A copy of the task (with a new id) will be added every `REPEAT_FREQUENCY` days (defaults to 7) starting from the `DEADLINE` for `REPEAT_TIMES` times.
  - Examples: `37`, `0` (the default if not specified, equivalent to no recurrence)
- `PRIORITY_LEVEL` can be either 1, 2, or 3 (1: `Low`, 2: `Medium`, 3: `High`).
  - A priority of `0` (the default if not specified) can also be used to indicate no priority.

### Selecting tasks

Some commands can act on multiple tasks at once, which can be selected by providing a list of ids, OR one or more filters.

- `IDS` consists of one or more ids, separated by a space.
  - **IMPORTANT**: Ids (indicated with `ID:`) are different from the ordering number of a task (indicated with `#`). Each task has a unique, fixed id number used to select that specific task. Hence, ids do not reset without using the [`reset`](#reset-to-do-list-reset) command.
  - A command will not be executed if any of the provided ids are invalid (not in the task list).
  - Commands will be executed as if the list of ids is provided in increasing order.
  - Examples: `1 3 5`, `99`
- `FILTERS` can be one or more of the following:
  - Boolean filters
    - Replace `1` with `0` to invert the filter. 
    - `-done 1` filters completed tasks.
    - `-overdue 1` filters overdue (incomplete and past deadline) tasks.
    - `-rep 1` filters recurring (`REPEAT_TIMES` > 0) tasks.
  - Non-boolean filters
    - `-all` selects all tasks (this overrides all other filters).
    - `-desc STRING` filters tasks with descriptions containing the given string.
    - `-email EMAIL_ADDRESS` filters tasks with the given email address.
    - `-before DEADLINE` filters tasks with deadlines before the given deadline.
    - `-after DEADLINE` filters tasks with deadlines after the given deadline.
    - `-tags LIST_OF_TAGS` filters tasks that have all the given tags.
    - `-prio PRIORITY_LEVEL` filters tasks with the given priority level.
  - Example: `-done 0 -desc next` selects all incomplete tasks with `next` in their description. 

### Others

- Trailing, leading or consecutive spaces will be removed from commands.
- Certain commands that have no effect, such as deleting a non-existent attribute from a task or marking an already complete task, can still be executed anyway.

## Features

### Adding a task `add`

Adds a new task with the given attributes to your To-Do list.

Format: `add DESCRIPTION [-due DEADLINE] [-email EMAIL_ADDRESS] [-tags LIST_OF_TAGS] [-rep REPEAT_TIMES] [-prio PRIORITY_LEVEL]`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.

Example of usage and output: 

`add todo -due 23/09/2023 23:59 -rep 10` creates a task with the description `todo`, the deadline `23 Sep 2023 23:59`, which will produce a copy of itself after `30 Sep 2023 23:59`.
```
Okay, I have added this task:
[ID:00001][ ][   ][todo                               ][Due by: 23 Sep 2023 23:59]
```
`add do math homework -email abc@def.com -prio 2 -tags difficult later 3.14` creates a task with the description `do math homework`, email address `abc@def.com`, priority level `Medium`, and the tags `3.14`, `difficult` and `later`.
```
Okay, I have added this task:
[ID:00002][ ][II ][do math homework                   ]
```

<!-- @@author RuiShengGit -->

### Mark a task (as complete) `mark`

Marks tasks with the given ids or matching the given filters as completed.

Format: `mark ID` OR `mark FILTERS`

- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.

Example of usage and output:

`mark 1` marks the task with id 1 in the To-Do list as done.
```
Okay, I have marked the following task(s) as complete:
__________________________________________________________________________________________
[#00001][ID:00001][X][   ][todo                               ][Due by: 23 Sep 2023 23:59]
```
`mark -done 0` marks all incomplete tasks as done.
```
Okay, I have marked the following task(s) as complete:
__________________________________________________________________________________________
[#00001][ID:00002][X][II ][do math homework                   ]
```

### Unmark a task (as incomplete) `unmark`

Unmarks tasks with the given ids or matching the given filters as incomplete.

Format: `unmark ID` OR `unmark FILTERS`

- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.

Example of usage and output:

`unmark 1` unmarks the task with id 1 in the To-Do list, so it is considered not done yet.
```
Okay, I have marked the following task(s) as incomplete:
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][todo                               ][Due by: 23 Sep 2023 23:59]
```
`unmark -prio 2` unmarks all tasks with priority level 2 (`Medium`), so they are considered not done yet.
```
Okay, I have marked the following task(s) as incomplete:
__________________________________________________________________________________________
[#00001][ID:00002][ ][II ][do math homework                   ]
```

### Delete a task `delete`

Deletes all tasks with the given ids or matching the given filters from the To-Do List.

Format: `delete ID` OR `delete FILTERS`

- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.

Example of usage and output:

`delete 2` deletes the task with id 2 from the To-Do list.
```
Okay, I have removed the following task(s):
__________________________________________________________________________________________
[#00001][ID:00002][ ][II ][do math homework                   ]
```
`delete -before 12/12/2024 12:12` deletes all tasks with deadlines before 12 Dec 2024 12:12.
```
Okay, I have removed the following task(s):
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][todo                               ][Due by: 23 Sep 2023 23:59]
```

<!-- @@author ERJUNZE -->

###  Edit description of a task `desc`

Edits the description of tasks with the given ids or matching the given filters.

Format: `desc IDS -edit DESCRIPTION` OR `desc FILTERS -edit DESCRIPTION`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.

Example of usage and output:

`desc 1 -edit abc def` sets the description of the task with id 1 to `abc def`.
```
Okay, I have edited the description of the following task(s) to [abc def]:
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 23 Sep 2023 23:59]
```

<!-- @@author clement559 -->

### Edit/delete deadline `due`

Edits or deletes the deadline of tasks with the given ids or matching the given filters.

Format: `due IDS -edit DEADLINE` OR `due FILTERS -edit DEADLINE`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.
- `-edit DEADLINE` can be replaced with `-del` to delete the deadline from a task instead.
  - Deleting the deadline from a recurring task will set its repeat times to 0, making it no longer recurring.

Example of usage and output:

`due 1 -edit 30-03-2024 18:00` sets the deadline of the task with id 1 to `30-03-2024 18:00`.
```
Okay, I have edited the deadline of the following task(s) to [30 Mar 2024 18:00]:
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 30 Mar 2024 18:00]
```

<!-- @@author RuiShengGit -->

### Edit/Delete email address `email`

Edits or deletes the email address of tasks with the given ids or matching the given filters.

Format: `email IDS -edit EMAIL_ADDRESS` OR `email FILTERS -edit EMAIL_ADDRESS`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.
- `-edit EMAIL_ADDRESS` can be replaced with `-del` to delete the email address from a task instead.

Example of usage and output:

`email 1 -edit rui@gmail.com` adds the email address `rui@gmail.com` to the task of id 1 in the To-Do list.
```
Okay, I have edited the email of the following task(s) to [rui@gmail.com]:
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 30 Mar 2024 18:00]
```

<!-- @@author ERJUNZE -->

### Add/Edit/Delete tags `tags`

Adds, edits, or deletes the tags of tasks with the given ids or matching the given filters.

Format: `tags IDS -edit LIST_OF_TAGS` OR `tags FILTERS -edit LIST_OF_TAGS`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.
- `-edit LIST_OF_TAGS` can be replaced with `-add LIST_OF_TAGS` to add all given tags to a task instead.
- `-edit LIST_OF_TAGS` can be replaced with `-del LIST_OF_TAGS` to delete all given tags from a task instead.
- `-edit LIST_OF_TAGS` can be replaced with `-del` to delete all tags from a task instead.

Example of usage and output:

`tags 1 -edit difficult later` sets the tags of the task with id 1 to `difficult` and `later`.
```
Okay, I have edited the tags of the following task(s) to [difficult, later]:
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 30 Mar 2024 18:00]
```
`tags -tags later -del later` deletes the tag `later` from all tasks with the tag `later`.
```
Okay, I have removed the tags [later] from the following task(s):
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 30 Mar 2024 18:00]
[#00002][ID:00002][ ][   ][do math homework                   ]
```

<!-- @@author clement559 -->

### Edit/delete repeat times `rep`

Edits or deletes the recurrence count of tasks with the given ids or matching the given filters.

Format: `rep IDS -edit REPEAT_TIMES` OR `rep FILTERS -edit REPEAT_TIMES`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.
- `-edit REPEAT_TIMES` can be replaced with `-del` to delete the recurrence count from a task instead.
- You cannot set or delete the recurrence count of tasks without a deadline.

Example of usage and output:

`rep 1 -edit 3` sets the recurrence count of the task with id 1 to `3`.
```
Okay, I have edited the repeat times of the following task(s) to [3]:
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 30 Mar 2024 18:00]
```
`rep -rep 1 -del` deletes the recurrence count of all recurring tasks.
```
Okay, I have deleted the repeat times of the following task(s):
__________________________________________________________________________________________
[#00001][ID:00001][ ][   ][abc def                            ][Due by: 30 Mar 2024 18:00]
```

<!-- @@author RuiShengGit -->

###  Edit/Delete priority level `prio`

Edits or deletes the priority level of tasks with the given ids or matching the given filters.

Format: `prio IDS -edit PRIORITY_LEVEL` OR `prio FILTERS -edit PRIORITY_LEVEL`

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.
- `-edit PRIORITY_LEVEL` can be replaced with `-del` to delete the priority level from a task instead.
  - `-edit 0` is equivalent to `del`.

Example of usage and output:

`prio 1 -edit 3` sets the priority level of the task with id 1 to 2 (High).
```
Okay, I have edited the priority level of the following task(s) to [High]:
__________________________________________________________________________________________
[#00001][ID:00001][ ][III][abc def                            ][Due by: 23 Sep 2023 23:59]
```
`prio -prio 2 -del` deletes the priority level of all tasks with priority level 0.
```
Okay, I have deleted the priority level of the following task(s):
__________________________________________________________________________________________
[#00001][ID:00002][ ][   ][do math homework                   ]
```

<!-- @@author KedrianLoh -->

### View all/selected tasks in To-Do list `list`

Display a summary of all tasks, or tasks matching the given filters.

Format: `list [IDS] [-sort SORT_OPTION]` OR `list [FILTERS] [-sort SORT_OPTION]`

- Reading the task summary:
  - `[#number]` indicates the ordering of the displayed task.
  - `[ID:number]` indicates the id of the displayed task.
  - `[!]` indicates an overdue task, `[ ]` indicates an incomplete task, `[X]` indicates a completed task.
  - `[   ]` indicates no priority, `[I  ]` indicates `Low` priority, `[II ]` indicates `Medium` priority, `[III]` indicates `High` priority.
  - The description is shown after the priority, and is truncated with `...` if it is too long.
  - The deadline is shown after the description, if it exists.
  - Other attributes can be seen with the [`info`](#view-detailed-information-of-a-task-info) command.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.
  - If no ids or filters are provided, all tasks will be displayed by default.
- `SORT_OPTION` can be one of the following:
  - `due` sorts tasks by their deadline, with earlier deadlines first and no-deadline tasks last.
  - `prio` sorts tasks by their priority, with higher priority levels first and no-priority tasks last.
  - `desc` sorts tasks by their description in lexicographic order.
  - `done` sorts tasks by their completion status, with overdue tasks first, then incomplete tasks, and complete tasks last.

Example of usage and output:

`list -sort done` will display a summary of all tasks, sorted by completion status. 
```
Okay, here is your task list, with 6 tasks
__________________________________________________________________________________________
[#00001][ID:00005][!][II ][object Object                      ][Due by: 03 Mar 2023 03:03]
[#00002][ID:00006][ ][III][Test                               ][Due by: 08 Apr 2023 20:00]
[#00003][ID:00003][ ][I  ][baba                               ][Due by: 20 Apr 2023 13:37]
[#00004][ID:00001][ ][III][abc def                            ][Due by: 30 Mar 2024 18:00]
[#00005][ID:00002][X][   ][do math homework                   ]
[#00006][ID:00004][X][   ][bubu                               ]
```
`list -done 0 -sort prio` will display a summary of all incomplete tasks, sorted by priority.
```
Okay, here is your task list, with 4 tasks
__________________________________________________________________________________________
[#00001][ID:00001][ ][III][abc def                            ][Due by: 30 Mar 2024 18:00]
[#00002][ID:00006][ ][III][Test                               ][Due by: 08 Apr 2023 20:00]
[#00003][ID:00005][!][II ][object Object                      ][Due by: 03 Mar 2023 03:03]
[#00004][ID:00003][ ][I  ][baba                               ][Due by: 20 Apr 2023 13:37]
```

### View detailed information of a task `info`

Display all the attributes (description, deadline, email, tags, repeat times, priority level) of tasks with the given ids or matching the given filters.

Format: `info IDS` OR `info FILTERS`

- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.

Example of usage and output:

`info 2 6` displays the details of the tasks with ids 2 and 6.
```
Okay, here is the detailed information of the following task(s):
__________________________________________________________________________________________
ID:           2
Description:  do math homework
Completed:    Yes
Email:        abc@def.com
Tags:         3.14, can, difficult, okay, yeah
__________________________________________________________________________________________
ID:           6
Description:  Test
Completed:    No
Priority:     High
Due:          08 Apr 2023 20:00
Repeat times: 12
```

<!-- @@author ERJUNZE -->

### View all tags in task list `taglist`

Displays all tags found in your To-Do List.

Format: `taglist`

Example of usage and output:

`taglist` displays any tags your tasks have.
```
Okay, here are the tags associated with your task list:
3.14, can, difficult, haha, okay, yeah
```

<!-- @@author jeromeongithub -->

### Show progress of tasks that are due this week `progress`

Displays the progress of and lists tasks that are due this week in To-Do list. Progress is shown in the form of a 
percentage (up to 2 decimal places) and a progress bar (where `=` denotes the proportion of tasks completed and `-` 
denotes the proportion of tasks that are left undone). Called automatically on startup.

- "this week" starts from Monday to Sunday of the current week.
- For example, if `progress` is used at any time on 06/04/2023 (Thursday), "this week" would include any time within 
03/04/2023 00:00 (Monday 12:00AM) to 09/04/2023 23:59 (Sunday 11:59PM).

Format: `progress`

Example of usage:

`progress`
```
You have completed 33.33% of the 3 tasks due this week!
Progress: |================----------------------------------|
__________________________________________________________________________________________
[#00001][ID:00001][X][III][task1                              ][Due by: 30 Mar 2023 18:00]
[#00002][ID:00002][ ][III][task2                              ][Due by: 30 Mar 2023 19:00]
[#00003][ID:00003][ ][III][task3                              ][Due by: 31 Mar 2023 20:00]
```

<!-- @@author clement559 -->

### View/Edit configurable settings `config`

Displays/edits the current configuration for repeating tasks.

Format: `config` OR `config [-repfreq REPEAT_FREQUENCY] [-chkfreq CHECK_FREQUENCY]` OR `config -reset`

- `REPEAT_FREQUENCY` is in days and must be an integer from 1 to 2147483647.
  - Tasks will recur, creating a copy of themselves (with a new id) if it has been at least `REPEAT_FREQUENCY` since their deadline.
- `CHECK_FREQUENCY` is in minutes and must be an integer from 0 to 2147483647.
  - The program will check for recurring tasks on startup and when a command is executed if it has been at least `CHECK_FREQUENCY` minutes since the last check.
  - A `CHECK_FREQUENCY` of 0 will mean that the program will check for recurring tasks every time a command is executed.
- `config -reset` will cause configuration settings to be reset to the default (equivalent to `config -repfreq 7 -chkfreq 0`).
  - `-reset` will override `-repfreq` and `-chkfreq` if provided together.

Example of usage and output:
`config` displays the current configuration settings.
```
Here are the configuration settings for your system:
Repeating tasks every 7 days
Checking for repeating task every: 0 minutes
```
`config -chkfreq 5 -repfreq 3` sets tasks to repeat every 3 days and the check for recurring task to every 5 minutes.
```
Okay, I have changed the configuration to as follows:
Repeating tasks every 3 days
Checking for repeating task every: 5 minutes
```

<!-- @@author RuiShengGit -->

### View help List `help`

Displays all possible commands/filters/sorts that can be used.

Format: `help [HELP_TYPE]`

- If `HELP_TYPE` is `filter`, the help message regarding filter options is displayed.
- If `HELP_TYPE` is `sort`, the help message regarding sorting options is displayed.
- If `HELP_TYPE` is anything else or not provided, the command help message is displayed.

Example of usage and output:

`help` will display the command help message.
```
Here is the list of commands that you can use:
+----------------------------------------------------------------------------------------+
| Command                                | Description                                   |
+----------------------------------------------------------------------------------------+
| add DESCRIPTION [-due DEADLINE]        | Adds a new task with the given attributes to  |
|                 [-email EMAIL_ADDRESS] | your To-Do list.                              |
|                 [-tags TAGS]           |                                               |
|                 [-rep REPEAT_TIMES]    |                                               |
|                 [-prio PRIORITY_LEVEL] |                                               |
+----------------------------------------------------------------------------------------+
| mark IDS                               | Marks given tasks as completed.               |
+----------------------------------------------------------------------------------------+
| unmark IDS                             | Marks given tasks as incomplete.              |
+----------------------------------------------------------------------------------------+
| delete IDS                             | Deletes given tasks from your To-Do list.     |
+----------------------------------------------------------------------------------------+
| desc IDS -edit DESCRIPTION             | Sets the descriptions of given tasks.         |
+----------------------------------------------------------------------------------------+
| prio IDS -edit PRIORITY_LEVEL          | Sets the priority levels of given tasks.      |
| prio IDS -del                          | Deletes the priority levels of given tasks.   |
+----------------------------------------------------------------------------------------+
| due IDS -edit DEADLINE                 | Sets the deadlines of given tasks.            |
| due IDS -del                           | Deletes the deadlines of given tasks.         |
+----------------------------------------------------------------------------------------+
| email IDS -edit EMAIL_ADDRESS          | Sets the email addresses of given tasks.      |
| email IDS -del                         | Deletes the email addresses of given tasks.   |
+----------------------------------------------------------------------------------------+
| tags IDS -edit LIST_OF_TAGS            | Sets the tags of given tasks.                 |
| tags IDS -add LIST_OF_TAGS             | Adds given tags to given tasks.               |
| tags IDS -del LIST_OF_TAGS             | Deletes given tags from given tasks.          |
| tags IDS -del                          | Deletes all tags from given tasks.            |
+----------------------------------------------------------------------------------------+
| rep IDS -edit REPEAT_TIMES             | Sets the recurrence count of given tasks.     |
| rep IDS -del                           | Makes given tasks no longer recur.            |
+----------------------------------------------------------------------------------------+
| list [FILTERS] [-sort SORT_OPTION]     | Display summary of all/filtered tasks that    |
|                                        | is sorted if a sorting option is given.       |
+----------------------------------------------------------------------------------------+
| info IDS                               | Display all attributes of given tasks.        |
+----------------------------------------------------------------------------------------+
| progress                               | Displays the progress and summary of tasks    |
|                                        | that are due this week.                       |
+----------------------------------------------------------------------------------------+
| taglist                                | Displays all tags found in your To-Do List.   |
+----------------------------------------------------------------------------------------+
| config                                 | Displays current configuration settings.      |
| config [-chkfreq CHECK_FREQUENCY]      | Sets checking frequency for recurring tasks.  |
|        [-repfreq REPEAT_FREQUENCY]     | Sets repeating frequency of recurring tasks.  |
| config -reset                          | Resets configuration settings to default.     |
+----------------------------------------------------------------------------------------+
| help                                   | Displays summary of all possible commands.    |
| help filter                            | Displays summary of all filter options.       |
| help sort                              | Displays summary of all sorting options.      |
+----------------------------------------------------------------------------------------+
| reset                                  | Deletes all tasks and reset IDs to 0.         |
+----------------------------------------------------------------------------------------+
| exit                                   | Exits the program.                            |
+----------------------------------------------------------------------------------------+
IDS can also be replaced with FILTERS, use "help filter" for information on filters.
```

### Exit program `exit`

Exits the program.

Format: `exit`

Example of usage and output:

`exit`
```
See you again, bye!
```

### Reset To-Do List `reset`

Resets your To-Do List, deleting all tasks and resetting the allocated id numbers.

Exits the program.

Format: `reset`

- Must be confirmed by entering `YES` (case sensitive).
- **Warning!** This is irreversible.

Example of usage and output:

`reset`
```
Are you sure you want to reset your To-Do List?
This cannot be undone. Enter "YES" to confirm, or anything else to cancel: 
```
`YES` will reset your To-Do List if entered after the above confirmation prompt.
```
Okay, your To-Do List has been reset.
```

<!-- @@author jeromeongithub -->

## FAQ

**How do I transfer my data to another computer?**
- Your task list is saved in the generated `data.json` file, and configs are saved in the `config.json` file.
- Copy these files to the other computer and place them in the same location as the jar file for the program before running it.

**Can I edit my save/config file using a text editor?**
- Yes, but this is not recommended unless you know what you are doing.
- If your save/config file is invalidated and cannot be loaded, it will be copied to `invalid_data.json` and `invalid_config.json`, while your task list/config settings will be reset.

**Why was my recurring task details not updated with the original task?**
- The recurring tasks that are automatically added by the system are not linked to the original task.
- If you wish to change the task details for future recurring tasks, you may edit the latest task added by the system before the current deadline.

## Command Summary

- Refer to the [Restrictions](#restrictions) section for more information about the parameters.
- Refer to the [Selecting tasks](#selecting-tasks) section for more information about selecting targeted tasks.

| Action                                 | Command                                                                                                                  |
|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| Add a task                             | `add DESCRIPTION [-due DEADLINE] [-email EMAIL_ADDRESS] [-tags LIST_OF_TAGS] [-rep REPEAT_TIMES] [-prio PRIORITY_LEVEL]` |
| Mark tasks complete                    | `mark IDS`                                                                                                               |
| Mark tasks incomplete                  | `unmark IDS`                                                                                                             |
| Delete tasks                           | `delete IDS`                                                                                                             |
| Edit description of tasks              | `desc IDS -edit DESCRIPTION`                                                                                             |
| Edit deadline to tasks                 | `due IDS -edit DEADLINE`                                                                                                 |
| Edit email to tasks                    | `email IDS -edit EMAIL_ADDRESS`                                                                                          |
| Edit tags to tasks                     | `tags IDS -edit LIST_OF_TAGS`                                                                                            |
| Edit repeat times of tasks             | `rep IDS -edit REPEAT_TIMES`                                                                                             |
| Edit priority level of tasks           | `prio IDS -edit PRIORITY_LEVEL`                                                                                          |
| Display summary of tasks               | `list [FILTERS] [-sort SORT_OPTION]`                                                                                     |
| Check all details of a task            | `info`                                                                                                                   |
| Check all tags in task list            | `taglist`                                                                                                                |
| Check progress of current week's tasks | `progress`                                                                                                               |
| Show/edit configuration settings       | `config [-repfreq REPEAT_FREQUENCY] [-chkfreq CHECK_FREQUENCY]`                                                          |
| Check all commands of the program      | `help`                                                                                                                   |
| Exit program                           | `exit`                                                                                                                   |
| Reset task list                        | `reset`                                                                                                                  |

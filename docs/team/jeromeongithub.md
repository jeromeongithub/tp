# Jerome Foo - Project Portfolio Page

## Project: NUS To-Do List
NUS To-Do List is a Java command-line interface (CLI) application that is designed to allow NUS students to keep track
of tasks that needs to be completed.

Given below are my contributions to the project.

### Summary of Contributions
- **New Feature**: Created the base the task class which was greatly improved by the team over the project.
    - What it does: Allows for the creation and modification of tasks in the To-Do list. 
    - Justification: This feature is essential to the program as tasks have to be created for the task list to be useful 
recording them in the first place.


- **New Feature**: Provided storage functionality using GSON library.
    - What it does: The user's To-Do list is saved as a `.json` file after every command executed by the user. The saved 
file is loaded whenever the user uses the program again.
    - Justification: This feature is essential to the program as the task list would only be useful for the user if it 
can be saved and used again when the user needs to refer to the task list. By using the GSON library, the user's task 
list is saved as a human-readable file which can be easily modified by advanced users to quickly edit their task list.
    

-  **New Feature**: Created a `progress` command to allow users to check their progress on tasks that are due within the 
                   current week.
    - What it does: Allows the user to see their progress on all tasks in the To-Do list that are due within the current
week. The user will see the percentage of tasks completed, a progress bar and a list of all the tasks that are due 
within the current week.
    - Justification: This feature is useful to the program as it provides users with a quick and easy way to view all of
their tasks that are due within the current week. This is especially useful if the user has a long To-Do list and only 
wants to see the tasks that are due very soon (in the current week), rather than listing out more tasks and having to 
double-check which are the ones that are due in the current week.

-  **New Feature**: Changed the commands dealing with task id to handle multiple ids instead of a single id only.
    - What it does: Commands such as the delete command can be done on multiple ids at once, instead of having to be 
selected 1 by 1.
    - Justification: During the PE Dry Run, our team obtained feedback that it was very troublesome to delete tasks 1 by
1 and that a mass delete option would be very useful to reduce the effort required to delete many tasks.

- **New Feature**: Changed the edit tags command so that there was the option of selective deletion of tags instead of
having the option to delete all tags only.
  - What it does: Users can specify task ids and the particular tags to they want to delete from those tasks.
  - Justification: During the PE Dry Run, our team obtained feedback that it was very inconvenient to remove specific 
tags as the only way to do so was by using the edit tags command and re-listing all the tags that the user wanted to 
have. The feedback suggested that an option to delete specific tags would make the program much more convenient to use.

#### Code Contributed:
- [RepoSense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=jeromeongithub&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2023-02-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

#### Enhancements Implemented:
- Wrote JUnit tests for the Storage class.

#### Contributions to the UG:
- Contributed to the `Features` section.
  - Contributed to the `Show progress of tasks that are due this week` component.

#### Contributions to the DG:
- Added implementation details for the progress command.
- Added implementation details and design considerations for the storage feature.
- Created Sequence Diagrams using Plant UML for the storage feature.
- Created Sequence Diagrams using Plant UML for the progress command.

#### Contributions to team-based tasks:
- Contributed to the planning of the product by coming up with features to implement.
- Ensure that the code being merged does not have bugs or typos.

#### Review/monitoring contributions:
- Reviewed and approved PRs for the team: [Full List](https://github.com/AY2223S2-CS2113-T11-4/tp/issues?q=is%3Aclosed+reviewed-by%3Ajeromeongithub)

### Contributions beyond the project team:
- Reported bugs in other teams' programs and provide suggestions to improve the program: [CS2113-W13-1](https://github.com/jeromeongithub/ped/issues)
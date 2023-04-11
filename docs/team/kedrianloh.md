# Kedrian Loh - Project Portfolio Page

## Project: NUS To-Do List

NUS To-Do List is a Java command-line interface (CLI) application that is designed to allow NUS students to keep track
of tasks that needs to be completed.

Given below are my contributions to the project.

### Summary of Contributions

- **New Feature**: Created the `info` command to allow users to view all relevant information about a task.
  - What it does: Allows the user to view all information tagged to a task.
  - Justification: This feature is essential so that users can exactly identify the task with all its specifications.

- **New Feature**: Created the `ParserUtil` predicate for filtering flags and arguments.
  - What it does: Returns predicate matching all given filters. If no filters are provided, returns null.
  - Justification: Allows for more efficient and clearer code to be written for the sorting functions for user display.

- **New Feature**: Created the `TaskList` sorting function.
  - What it does: Sorts the TaskList based on the predicate and comparator
  - Justification: This allows the list command to be sorted by its various requirements before displaying to the user.

### Code Contributed:

- [RepoSense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=kedrian&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-02-17&tabOpen=true&tabType=authorship&zFR=false&tabAuthor=KedrianLoh&tabRepo=AY2223S2-CS2113-T11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented:

- Added a Junit test for `info` command.

### Contributions to the UG:

- Contributed to the `list` section.

### Contributions to the DG:

- Added implementation for details for list function
- Created Sequence Diagrams using Plant UML for list commands.

### Contributions to team-based tasks:

- Contributed to the planning of the product by coming up with features to implement.
- Ensure that the code being merged does not have bugs or typos.

### Review/monitoring contributions:

- Reviewed and approved PRs for the team: [CS2113-T11-4](https://github.com/AY2223S2-CS2113-T11-4/tp/pulls?page=2&q=is%3Apr+is%3Aclosed)

### Contributions beyond the project team:

- Reported bugs in other teams' programs and provide suggestions to improve the program:[CS2113-W12-1](https://github.com/KedrianLoh/ped)

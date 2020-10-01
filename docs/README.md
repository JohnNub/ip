# User Guide

## Features 

### 3 Different Type of Tasks

* Todo - For the most simple of tasks
* Deadline - For tasks with a due date
* Event - For tasks with a start and end time

### Automatic Save and Load

Application will automatically save all your data after every command and load it on startup. 
No user setup required means it's simple and easy to use!

### Advanced relative dates

Instead of entering a date format, specify a day relative to today with simple wording like "next friday" or "end sun"
to quickly fill up your tasks instead of spending time entering time formats. This provides a great efficiency boost
compared to other applications.

### Find that Task!

With a case insensitive search feature, you can find any task you create by name with convenience. Even if the list
of tasks get long, you can always find the specific task that you want.

## Usage

### `bye` - Quits the app

Stops the application naturally

Example of usage: 

`bye`

Expected outcome:

```
____________________________________________________________
  Bye. Hope to see you again soon!
____________________________________________________________
```

### `list` - Shows list

Shows all the tasks in the list along with some information about each task's status.

Example of usage: 

`list`

Expected outcome:

```
____________________________________________________________
  1. [D][✘] CS2113T IP Due: 2020-09-18T23:49:01.706936800
  2. [T][✘] Another Project
  3. [D][✘] something Due: 2020-10-02T17:54:12.952375400
  4. [E][✘] something Starts: 2020-10-02T17:58:35.609400800 Ends: 2020-10-02T17:58:35.609400800
____________________________________________________________
```
### `delete <taskIndex>` - Removes task

Removes the specified task by index.  
Index should be an integer larger than 1 representing the index of the item reflected by `list`

Example of usage: 

`delete 5`

Expected outcome:

```
____________________________________________________________
  Noted! I've removed this task from the list:
  	[E][✓] Leave Starts: 2020-10-03T21:37:05.966028700 Ends: 2020-10-04T21:37:05.966028700
____________________________________________________________
```
### `find <findString>` - Finds a task by name

Shows all tasks that match the specified argument `findString`  
Returns a list showing the number of items matching the criteria. Or an empty list if there are no matching items

Find is case insensitive. The returned list preserves the original indexes of the items, allowing you to use find
to identify the index of any task.

Example of usage: 

`find some`

Expected outcome:

```
____________________________________________________________
  3. [D][✘] something Due: 2020-10-02T17:54:12.952375400
  4. [E][✘] something Starts: 2020-10-02T17:58:35.609400800 Ends: 2020-10-02T17:58:35.609400800
____________________________________________________________
```
### `todo <taskName>` - Create todo

Adds a new todo to the list with the specified name

Example of usage: 

`todo My Todo`

Expected outcome:

```
____________________________________________________________
  Added new todo: [T][✘] My Todo
____________________________________________________________
```


### `deadline <deadlineName> /by <time>` - Create deadline

Adds a new deadline to the list with the specified date and time.  
Time supports formats like dd/MM/yyyy and dd-MMM-yyyy HH:mm:ss.  
Alternatively, you may use shorthand terms like "next friday" or "end saturday" to specify time relative to now.

Example of usage: 

`deadline my deadline /by next friday`

Expected outcome:

```
____________________________________________________________
  Added new deadline: my deadline
	Due by: 2020-09-10T13:36:29.396448400
____________________________________________________________
```

### `event <deadlineName> /at <startTime> /to <endTime>` - Create event

Adds a new event to the list with the specified date and time for starting and ending.  
Time supports formats like dd/MM/yyyy and dd-MMM-yyyy HH:mm:ss.  
Alternatively, you may use shorthand terms like "next friday" or "end saturday" to specify time relative to now.

When using relative time for end time, it will find the next weekday relative to the start time.  
The call will fail if the start time is after the end time.

Example of usage: 

`event vactaion /at next friday /to end thurs`

Expected outcome:

```
____________________________________________________________
  Added new event: vactaion
  	Start at: 2020-10-02T22:02:28.094886900 Ends : 2020-10-08T23:59:59
____________________________________________________________
```
Homework assignment no. 2, Text 
====================================

**Publication date:**  April 26, 2021

**Submission deadline:** May 10, 2021

General information
-------------------
In this assignment you will create a program which allows for a simple line processing of text files.
The application should support the following operations. 


| Operation | Type | CLI option | Description |
| ------ | ------ | ------ | ------ |
| unique | intermediate | -u | Filter unique lines |
| sort | intermediate | -s | Sort lines by natural ordering |
| duplicates | intermediate | -d | Filter duplicate lines |
| lines | terminal | lines | Print lines (default operation) |
| count | terminal | count | Count lines |
| sizes | terminal | sizes | Counts the characters for each line (excluding line separators)|
| similar | terminal | similar | Lists pairs of most similar (distinct) lines according to Levenshtein distance |

In addition to operations the application should also support these command line options

| CLI option | Description |
| ------ | ------ |
| --help | Print application usage |
| --file | Path to file operated on by the application |

For more details about the CLI see the section "Running the application" in this README as well as JUnit tests.

**Hint:** *You want to start by implementing the command line arguments first in order to run the tests. Unfortunately JCommander calls System.exit when it encounters an unknown command line option.*

**Hint 2:** *First homework should give you enough information about how to implement this command line interface.*

### Evaluation
Beside functional correctness this assignment is focused on object oriented design.
This means that the way you structure your program will be an important part of its evaluation.
On the other hand the given set of tests is not trying to provide an elaborate test coverage and incorrect behaviour in corner-cases should not have a large impact on the evaluation.

Note that all this is at your seminar teacher's discretion.

The maximum number of points for this assignment is **12**.

- **7 points** for passing the tests (attached tests do not guarantee a 100% correctness).
- **5 points** for correct and clean implementation (evaluated by your class teacher).

### Preconditions
To successfully implement this assignment you need to know the following

1. Creating object design of an application
2. Working with collections
3. Exception handling
4. Ability to work with 3rd party code

### Project structure
The structure of project provided as a base for your implementation should meet the following criteria.

1. Package ```cz.muni.fi.pb162.hw02``` contains classes and interfaces provided as part of the assignment.
  - **Do not modify or add any classes or subpackages into this package.**
2. Package  ```cz.muni.fi.pb162.hw02.impl``` should contain your implementation.
  - **Anything outside this package will be ignored during evaluation.**

### Names in this document
Unless fully classified name is provided, all class names are relative to  package ```cz.muni.fi.pb162.hw02``` or ```cz.muni.fi.pb162.hw02.impl``` for classes implemented as part of your solution.


### Compiling the project
The project can be compiled and packaged in the same way you already know

```bash
$ mvn clean install
```

The only difference is, that unlike with seminar project, this time checks for missing documentation and style violation will produce an error.
You can temporarily disable this behavior when running this command.

```bash
$ mvn clean install -Dcheckstyle.fail=false
```

### Running the application
Build command mentioned above will produce a runnable jar file ``target/application.jar``. 
The following are example usages of developed application.

```bash
# Basic print of file lines
$ java -jar application.jar --file /example/path/duplicities.txt lines
This is a single line!
This is another one!
This is another one!
This is a single line!
This one is unique!


# Lines operation is the default
$ java -jar application.jar --file /example/path/duplicities.txt
This is a single line!
This is another one!
This is another one!
This is a single line!
This one is unique!


# Print of sorted lines
$ java -jar application.jar --file /example/path/duplicities.txt -s
This is a single line!
This is a single line!
This is another one!
This is another one!
This one is unique!
 
 
# Print unique lines
$ java -jar application.jar --file /example/path/duplicities.txt -u 
This is a single line!
This is another one!
This one is unique!


# Print duplicate lines
$ java -jar application.jar --file /example/path/duplicities.txt -d 
This is another one!
This is a single line!


# Count lines in the file
$ java -jar application.jar --file /example/path/duplicities.txt count
5


# Count unique lines
$ java -jar application.jar --file /example/path/duplicities.txt -u count
3


# Find most similar lines by Levenshtein distance
$ java -jar application.jar --file /example/path/duplicities.txt similar
Distance of 9
This is a single line! ~= This is another one!


# Duplicates and Unique lines together are invalid 
$ java -jar application.jar --file /example/path/duplicities.txt -d - u
Distance of 9
Invalid combination of options was used! # std err
# Application usage should follow
```


You can consult your seminar teacher to help you set the ```checkstyle.fail``` property in your IDE (or just google it).

### Submitting the assignment
The procedure to submit your solution may differ based on your seminar group. However generally it should be OK to submit ```target/homework02-2021-1.0-SNAPSHOT-sources.jar``` to the homework vault.

## Implementation
Generally speaking there are no mandatory requirements on the structure of your code as long as the command line interface of ```Appplication``` class works correctly.
These requirements are described above and covered by JUnit tests. 
The use of classes, enums and interfaces, provided as part of the project skeleton is up to your decision.

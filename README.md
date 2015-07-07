# maven1-eclipse-userlib-generator
Utility to generate a Eclipse user libraries files, from Maven 1 dependencies file (project.xml).

### Quick Start

To generate the Eclipse .userlibraries file, execute the following command:

```bash
java -jar maven1-eclipse-userlib-generator-all-1.0.jar -p <path to file folder where project.xml is located> >> project.userlibraries
```

### Jar generation

To generate the jar with the included dependencies use the *fatJar* Gradle task.

### Input arguments

* **-p** List of project folders that contain the *project.xml* file, separated by comma.
* **-i** List of artifact group Ids to ignore, separated by comma.
* **-ia** List of artifact Ids to ignore, separated by comma.
* **-?** Prints help.

### TODO

- [ ] Obtain file path separator by running operating system.
- [ ] Infer maven1 home local if not set.

### Releases

[v1.0](https://github.com/aromeroj/maven1-eclipse-userlib-generator/releases/download/v1.0/maven1-eclipse-userlib-generator-all-1.0.jar)

### Motivation

I created this utility mostly to aid with legacy project configurations and to learn a little bit of the Scala programming language.

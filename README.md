# Open Data Parser Generator

## Getting Started

### Prerequisites

To use this program, the user needs to install [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Eclipse IDE for Java EE](https://www.eclipse.org/downloads/).

### Installation Guide

1. Clone this git repository.

```bash
git clone https://github.com/OpendataDeveloperNetwork/open-data-parser-generator
```

2. The root folder `open-data-parser-generator/` should contain these files:

```
open-data-parser-generator/
  .setting/
  bin/
  src/
  .classpath
  .project
  commons-io-2.6.jar
  json-20140107.jar
```

3. Import the open data parser generator folder to the Eclipse

```
Open Eclipse
Open File -> Open Project from File System -> Directory -> Choose the JSON Converter Folder -> Finish
```

4. Import libraries to the Eclipse project

```
Right click open data parser generator project inside the Project Explorer in your Eclipse -> choose Properties 
Click Java Build Path -> Under Libraries -> Add External JARs 
Open open-data-parser-generatorJSON Converter Folder -> Open JSON Converter Folder -> Add json-20140107.jar and commons-io-2.6.jar
```

5. Run the project

```
Users can run an executable the JAR file parser-generator.jar to run the program or build it in eclipse:

Open open data parser generator eclipse project -> Open src folder -> open main folder -> open UI java class -> run
```

### Usage


Users can load a CSV file and a JSON Schema to produce a javascript script that can be run to produce a JSON data file based on the format of the imported JSON Schema.

1. Compile and run open data parser generator with Eclipse or other IDE.
2. Click the **Select CSV** button from the popup UI and navigate to the directory of your CSV file and click **Open**.
3. Click the **Select JSON Schema** button from the popup UI and navigate to the firectory of your JSON Schema file and click **Open**.
4. The leaf nodes of the JSON Schema will be displayed as a list on the left of the UI popup window. Select the corresponding CSV Column from the dropdown menu next to each JSON Leaf Node.
5. Click Save when you are done selecting all CSV columns and select a directory to save your `converter.js` file.

## Built With
* [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  * Java Development Kit
  * Version: 1.8.0_181
* [JSON in Java](https://mvnrepository.com/artifact/org.json/json/20140107)
  * JSON is a light-weight, language independent, data interchange format.
  * Version: 20140107
* [Apache Commons IO](https://mvnrepository.com/artifact/commons-io/commons-io/2.6)
  * The Apache Commons IO library contains utility classes, stream implementations, file filters, file comparators, endian transformation classes, and much more.
  * Version: 2.6

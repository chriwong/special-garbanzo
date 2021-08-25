# JDBC hands-on
The goal of this project is to create a text-based CLI program that reads user input and executes SQL commands to a Postgres/MySQL database using JDBC.

[This StackOverflow answer](https://stackoverflow.com/a/2840358) is basically everything you need to do (except they don't use Maven, so that's harder).

### Learning goals
* JDBC is part of the standard library
  * it's "built-in" to the Java language
  * similar to how Collections (Array, Map, Set) are already present in Java
  * the packages `java.sql` and `javax.sql` are JDBC (`javax.sql` is JDBC v2.0)

* JDBC is just a bunch of _interfaces_
  * there's no actual code
  * Java leaves this up to the vendors of database systems (Postgres, MySQL, Oracle, etc.)

* Databases are software applications entirely separate from your Java programs
  * Microsoft Word, Google Chrome, Spotify - all separate applications
  * MySQL, PostgreSQL, H2, Apache Derby - all separate applications
  * but they can be interacted with inside a running Java program through JDBC

* JDBC "Drivers" are implementations of the interfaces
  * the JDBC interfaces are implemented for specific databases
  * the creators of OracleDB wrote their own code to implement JDBC
  * as did the creators of MySQL, Postgres, MariaDB, H2, Apache Derby, etc.
  * whichever database application you chose to use, you'll need the corresponding JDBC Driver

* JDBC allows a Java program to connect to a database, execute queries, and view results
  * the queries are written in SQL, so you need to know that first

## Setup
1) Install a database (Postgres, MySQL, MariaDB)

2) Run commands using CLI
First, we practice running commands through the CLI to ensure our download worked
   1) open Terminal/Command Prompt/Powershell
   2) Connect
      * `mysql -u root -proot` (example command to execute to connect to MySQL server)
      * I'm not sure "root" is the default password for MySQL root - check the docs!
   3) Create and use database
      * `CREATE DATABASE applesauceDB;` (I find weird names to be more useful than "testDB")
      * `USE applesauceDB;`
   4) Create a table
      * `CREATE TABLE foods( name VARCHAR(255), yummy INTEGER );`
      * `DESCRIBE testTable;`
   5) Insert data
      * `INSERT INTO foods(name, yummy) VALUES ("spaghetti", 6), ("noodles", 9), ("beef tacos",3), ("potatoes", 5)`
   6) Query data
      * `SELECT * FROM foods;` (verify insert success)
   7) Delete table
      * `DROP TABLE foods`
   * Don't delete the `applesauceDB` - we're going to connect to it later

* JDBC allows developers to run all of these^^ commands within a program

1) Setup Java project
   * Get a new Maven project started
     * aka get a pom.xml and src/main/java. IntelliJ might make this easier, but it's doable in text editor alone
   * Add JDBC driver dependency to `pom.xml`
     * check official vendor docs (or just search Maven central)
     * ```xml
        <dependencies>
            <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.26</version>
            </dependency>
        </dependencies>
        ```
   * Setup other `pom.xml` properties/goals
     * Java source + target version
     * output JAR dependency bundling
   * Research connection URL format
     * check official docs (or StackOverflow)
       * [example for MySQL](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html)
       * [example for PostgreSQL](https://jdbc.postgresql.org/documentation/80/connect.html)

2) Setup database
   * Connect to database again (step 2.2)
   * Create another table (step 2.4)
   * Insert data into table (step 2.5)
   * Verify data was inserted (step 2.6)
Now we have data that we __know__ is in the database. Let's try retrieving it using Java code

3) JDBC classes
The Oracle documentation isn't so bad for understanding the basic process:
   1) Get a `Connection` object using either `DriverManager` (not deprecated, just old) or `DataSource`
   2) Create a `Statement` object from the `Connection` object
   3) Execute a query from the `Statement`, optionally storing the results in a `ResultSet` object
   4) Loop through the `ResultSet` object to see the data
```java
public class MyClass {
    public static void main(String[] args) {
        // Using JDBC with MySQL Driver to query an already-existing database
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/applesaucedb",
                    "root",
                    "root");

            if (connection.isValid(500)) {
                System.out.println("Got a connection using DriverManager!");

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM foods");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " scores " + resultSet.getString(3) + "/10");
                }
            }

        } catch (Exception e) {
            System.out.println("Not able to connect & execute because: " + e.getMessage());
        }
    }
}
```

4) Expand JDBC functionality
   * Try executing `INSERT`, `DELETE`, etc. using JDBC
   * Use `PreparedStatement` instead of `Statement` class
     * difference is `PreparedStatement` can specify a more generic SQL statement that _later_ receives parameters
     * it is more performant - a statement is compiled once and rerun vs compiled each time)
     * it is safer - helps prevent SQL injection attacks

5) Create CLI program
   * Read input for SQL command type (`INSERT` vs `SELECT`)
   * Depending on command type:
     * read input for table name, table columns, and new values (`INSERT`)
     * read input for desired columns and table name (`SELECT`)
   * Execute query
   * Display some kind of results page in terminal

6) Further study
   * Try connecting this completed CLI program to the Heroku instance on the web
     * you'll need a different connection URL (because it's not `localhost` and you may have used a different database locally than PostgreSQL on Heroku)

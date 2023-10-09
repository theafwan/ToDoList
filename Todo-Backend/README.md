# Todo-Backend Application

## Description

This is a backend application for a To-Do List Application. It provides RESTful APIs to create, read, update, and delete Todo items and their associated tasks.

## External Dependencies

The application uses the following external dependencies (JAR files):

1. **Spring Boot Starter Data JPA**
    - Description: This dependency provides the necessary Spring Boot starters for using Spring Data JPA for database access.
    - Artifact ID: `spring-boot-starter-data-jpa`

2. **Spring Boot Starter Web**
    - Description: This dependency provides the necessary Spring Boot starters for building web applications.
    - Artifact ID: `spring-boot-starter-web`

3. **MySQL Connector/J**
    - Description: MySQL Connector/J is the official JDBC driver for connecting to MySQL databases.
    - Artifact ID: `mysql-connector-j`
    - Scope: Runtime (used during runtime)

4. **Project Lombok**
    - Description: Project Lombok is used for reducing boilerplate code in Java classes by generating getters, setters, constructors, and other common methods.
    - Artifact ID: `lombok`
    - Optional: true (used during development but not required during runtime)

5. **Spring Boot Starter Test**
    - Description: This dependency provides Spring Boot starters for writing unit and integration tests.
    - Artifact ID: `spring-boot-starter-test`
    - Scope: Test (used for testing purposes)

## Building and Running the Application

To build and run the application, make sure you have the following prerequisites:

- Java 1.8 or higher
- MySQL database (configured in `application.properties`)
- Maven (for building and managing dependencies)

Follow these steps:

1. Clone the repository to your local machine.

2. Configure your MySQL database connection in the `application.properties` file:

   ```properties
   spring.datasource.url=jdbc:mysql://<YOUR_MYSQL_HOST>:<YOUR_MYSQL_PORT>/<YOUR_DATABASE_NAME>
   spring.datasource.username=<YOUR_MYSQL_USERNAME>
   spring.datasource.password=<YOUR_MYSQL_PASSWORD>
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    ```
3. Run the application using Maven:
    ```shell
    mvn spring-boot:run
    ```
4. The application will start and listen on port 8080 by default.

## Database Schema

The application uses two tables, todos and tasks, with a foreign key relationship between them. The schema is defined in schema.sql.

```sql
    CREATE TABLE IF NOT EXISTS todos (
        ID INT AUTO_INCREMENT PRIMARY KEY,
        NAME VARCHAR(255),
        DESCRIPTION VARCHAR(255)
    ) ENGINE=InnoDB;

    CREATE TABLE IF NOT EXISTS tasks (
        ID INT AUTO_INCREMENT PRIMARY KEY,
        NAME VARCHAR(255),
        DESCRIPTION VARCHAR(255),
        TODO_ID INT,
        CONSTRAINT FK_TASK_TODO FOREIGN KEY (TODO_ID) REFERENCES todos(ID)
    ) ENGINE=InnoDB;
```
## Testing

Unit test cases for the controller and service classes are provided to test both positive and error scenarios. You can run the tests using the following command:

```shell
mvn test
```

## Contributors
* Mohammed Afwan
      
## License
This project is licensed under the MIT License - see the LICENSE file for details.

```javascript
    Replace `<YOUR_MYSQL_HOST>`, `<YOUR_MYSQL_PORT>`, `<YOUR_DATABASE_NAME>`, `<YOUR_MYSQL_USERNAME>`, and `<YOUR_MYSQL_PASSWORD>` with the actual database connection details in your application.
```
### Using java or mvn
1. Pull the project from the repo and compile with the following maven command from the root directory.

```
mvn clean package
```

2. The command above will build user-management.jar in the target directory. You may run the jar file as follows:

```
cd application
java -jar target/user-management.jar
```

3. The application can also be started using maven:

```
mvn spring-boot:run 
```

4. Navigate to http://localhost:8061/user-management/swagger-ui to view the documentation.

5. To test the sending of emails, you need to pass email config to the run command i.e

```
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.mail.username=yourgmailusername --spring.mail.password=yourgmailapppassword
```
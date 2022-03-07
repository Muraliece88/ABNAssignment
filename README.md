# ABN - Receipe CRUD REST API

## Frameworks Used

Java 8, Spring Boot 2, Maven 3.8
For removing bolier plate code --- Lombok
API Documentation - Swagger2
Database-Embedded DB - H2
Testing - Junit jupiter, Mockito, MockMvc Embedded DB - H2

## Code setup and run
1. Extract the zip file
2. Navigate to project root folder and execute mvn spring-boot:run (ensure maven is present on the machine)
3. Application uses port 1234

## Production configuration

1. Spring boot application runs with active profiles. Considering the production ready state, spring.profiles.active=prod configured in app.properties
2. Spring security is enabled with different credentials for prod and test
3. Actuator and prometheus endpoints are made available to capture health and metrics which is accessible for user with role admin

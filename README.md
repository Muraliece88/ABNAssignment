# ABN - Receipe CRUD REST API

## Frameworks Used

Java 8, Spring Boot 2, Maven 3.8.
For removing bolier plate code --- Lombok.
API Documentation - Swagger2.
Database-Embedded DB - H2.
Testing - Junit jupiter, Mockito, MockMvc Embedded DB - H2.
No checkstyles files used

## Code setup and run
1. Extract the zip file
2. Navigate to project root folder and execute mvn spring-boot:run (ensure maven is present on the machine)
3. Application uses port 1234

## Packaging structure

Since this is a small scale application, Layer based packaging is done.

## Production ready considerations:

1. Spring boot application runs with active profiles. Considering that there are no further changes required when you test the assignment, the prod profile is configured to point the H2 database. In the real time scenario, different databases can be used based on the environment.
2. Spring security is enabled with different credentials for prod and test
3. Actuator and prometheus endpoints are made available to capture health and metrics which is accessible for user with role admin.
	
	http://localhost:1234/actuator :
	With actuators operational informations like health, metrics, info, dump can be collected

	http://localhost:1234/actuator/auditevents
	Audit events are collected

	Metrics sent by actuators can be intercepted by prometheus server for event monitoring and alerting
	http://localhost:1234/actuator/prometheus:


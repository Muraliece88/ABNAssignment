## PreRequsites

While testing the API in YARC or post man before begin the test , login to with below credentials http://localhost:1234/login 


Basic auth 
 1. username: USER
 2. password: password

Refer to swagger UI for method verb

## TestCases
 
 1. Given a request body, When POST  request is made  to http://localhost:1234/api/v1/dish,
 Then a JSON response body is retured with status code -200
 2. Given a request body, When PUT request is made to http://localhost:1234/api/v1/dish,
 Then  a JSON response body containing the id of the receipe is retured  with status code -200
 3. Given a ID parameter, When PUT request is made to with Invalid ID http://localhost:1234/api/v1/dish,
 Then  error with "receipe ID not found :" message is retured with status code- 500
 4. Given a ID parameter, When DELETE request is made to http://localhost:1234/api/v1/dish/{<id>},
 Then  a JSON string response body containing the id of the deleted receipe is retured with status code- 200
 5. Given the required query params, When a GET http://localhost:1234/api/v1/dish/searchrecipe
  Then  a JSON response body containing List of receipes is retured  with status code -200
 6. Given a ID parameter, When DELETE request is made to with Invalid ID http://localhost:1234/api/v1/dish/{<id>},
 Then  error with "receipe ID not found :" message is retured with status code- 500
 7. Given the required query params with date format other than dd-MM-yyyy hh:mm, When a GET http://localhost:1234/api/v1/dish/searchrecipe
  Then error with is returned status code -400
 8. Given the required query params without authentication, When any request is made to the base URL http://localhost:1234/api/v1/dish/
  Then  a login page is retured.
  9. Given the required query params with incorrect login credentials , When any request is made  is made http://localhost:1234/api/v1/dish, Then  a login page is retured with message bad credentials.
  10. Given the login credentials with role admin when any request to http://localhost:1234/actuator/health
  Then a actuator information is visible
  11. Given the login credentials with role user when any request to http://localhost:1234/actuator/health
  Then a response message access denied with 403 is thrown
  12. Given the login credentials with role admin when any request to http://localhost:1234/api/v1/dish
  Then access denied with 403 is thrown
  
  
  
  
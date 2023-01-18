# Banking-service

# Running the application locally

mvn clean install

create run config file to execute "com.assignment.banking.BankingService.BankingServiceApplication" 

Swagger URI -> http://localhost:8080/swagger-ui/

H2-Console-> http://localhost:8080/h2-console


# Design
My assumptions ->
1. While creating account user need to pass same opening balance and closing balance.
2. Account class is composed of List of transaction, basic user details like  name, emailId, address.
3. I have considered account are same if they same email id.
4. Have added basic validation on UUID, emailId and mandatory checks.
5. User can process with transfer type of transactions(Transfer).
6. When user request for Transfer, there would be two transaction one Debit and other would be Credit with request type as "Withdraw".
7. Amount entered would be in range of 0.00 till 9999999999.00
8. Account balance can never be negative.
9. Use spring profile local for H2 DB and local testing.
10. I have used persistence DB and can be activated using profile prod.
11. I have committed docker compose file containing the app and oracle DB image.
12. We have 2 docker-compose file for each environment.
13. Also shared the Postman collection for testing.
14. I have used profile test for Junit and integration testing.
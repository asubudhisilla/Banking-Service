# customer-service

# Running the application locally

mvn clean install

create run config file to execute "com.assignment.banking.BankingService.BankingServiceApplication" 

Swagger URI -> http://localhost:8080/swagger-ui/

H2-Console-> http://localhost:8080/h2-console


# Design
My assumptions ->
1. While creating account user need to pass same opening balance and closing balance.
2. Account class is composed of Card Details, basic user details like first name, last name, DOB, address and List of Transactions.
3. User can process with two type of transactions(Deposit/Withdraw).
4. When user request for Withdraw, then Transaction request type will be "Withdraw" and TransactionType would be "Debit".
5. When user request for Deposit, there would be two transaction one Debit and other would be Credit with request type as "Withdraw".
6. Amount entered would be in range of 0.00 till 9999999999.00
7. Account balance can never be negative.
8. For transaction using Credit, balance should be transaction AMount+Fee should be less than closing balance.
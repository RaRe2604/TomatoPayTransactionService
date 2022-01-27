# TomatoPayTransactionService

Build the Jar file using Gradle
</br>
`gradle clean`
</br>
`gradle build`
</br>
Make sure the build was Successful and jar file is generated in build/jars directory.
</br>
</br>
Now, to run the Jar we are generating the Docker image using the following commands
</br>
`docker build -t transactionservice .`
</br>
`docker run --name transservice -d -p 8080:8080 transactionservice`
</br>

## OPERATIONS
*To get all the transactions*
>`curl --location --request GET 'localhost:8080/transactions'`
</br>

*To create a transaction*
>`curl --location --request POST 'localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "accountId": "70bdcd28-39f8-4fc2-9d6b-ba5b7ec584c1",
    "currency": "INR",
    "amount": 100.50,
    "description": "Used to pay for Food by Tomato",
    "type": "DEBIT"
}'`
</br>

*To get the transaction*
>`curl --location --request GET 'localhost:8080/transactions/{id}'`
</br>

*To update a transaction*
>`curl --location --request PUT 'localhost:8080/transactions/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "accountId": "70bdcd28-39f8-4fc2-9d6b-ba5b7ec584c1",
    "currency": "INR",
    "amount": 50.50,
    "description": "Used to pay for Food by Tomato - Updated",
    "type": "DEBIT"
}'`
</br>

*To delete the transaction*
>`curl --location --request DELETE 'localhost:8080/transactions/{id}'`
</br>

*To get the balance*
>`curl --location --request GET 'localhost:8080/balance/{accountId}'`
</br>


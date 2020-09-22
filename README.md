# Reminder API

"Forget" about your worries. Reminder API is going to take care of reminding you
about them.

### Running Locally

Maven: to obtain a `.jar` out of the code. Run this command.

    mvn clean package

Start the application.

    java -jar reminder-api-<version-number>.jar
    
The application runs at `localhost:8080`

### Endpoints

#### Post a new reminder

Endpoint
    
    /reminders
    
CURL command

    curl --location --request POST 'localhost:8080/reminders' ^
    --header 'Content-Type: application/json' ^
    --data-raw '{
        "name": "Pick up laundry",
        "description": "Pick up laundry from the nearby laundry shop on Cornwall Rd. SX2 UO23",
        "dueDate": <future date in this format> "2020-08-30T13:29:00.0000+03:00",
        "receiverPhoneNumber": <phone-number>
    }'

#### Get reminder by id

Endpoint

    /reminders/{id}
    
CURL command

    curl --location --request GET 'localhost:8080/reminders/1'
    
    
#### Get all reminders

Endpoint

    /reminders
    
CURL command

    curl --location --request GET 'localhost:8080/reminders'
    
#### Update reminder (that hasn't been done yet)

Endpoint

    /reminders/{id}
    
CURL command (updating reminder with id 2)

    curl --location --request PUT 'localhost:8080/reminders/2' ^
    --header 'Content-Type: application/json' ^
    --data-raw '{
        "name": "Pick up laundry (revised)",
        "description": "Pick up laundry from the nearby laundry shop on Cornwall Rd. SX2 UO23 -- Pay with the card.",
        "dueDate": <future date in this format> "2020-08-30T13:29:00.0000+03:00",
        "receiverPhoneNumber": <phone-number>
    }'
    
    
#### Delete reminder

Endpoint

    /reminders/{id}
    
CURL command

    curl --location --request DELETE 'localhost:8080/reminders/1'

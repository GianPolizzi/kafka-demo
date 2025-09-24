# Kafka Producer/Consumer Demo

This project is a simple demonstration of an asynchronous communication system using Apache Kafka. It contains the following micro-services:
- producer-service: A RESTful API that sends messages to a Kafka topic.
- consumer-service: A Kafka listener that receives and processes messages from the same topic.

## Getting started

To get the project up and running locally, follow these steps.

### Prerequisites

- Java SDK: Version 17 or higher
- Apache Maven: For dependency management
- Docker and Docker Compose: To run Kafka and ZooKeeper

### 1. Clone the Repository

First, clone the project from GitHub:

  git clone https://github.com/your_username/kafka-demo.git
  cd kafka-demo
  
### 2. Start the Kafka Environment

This project uses Docker Compose to manage the Kafka and ZooKeeper containers. So, open Docker (Desktop) on your Device. 
After, from the project's root directory, start the services:

  docker-compose up -d

This will run the containers in the background. You can check their status with docker ps.
Check the correctly start of containers with thw following command bash:

  docker ps -a

If a contaner was terminated abnormally, check the kafka log with this command:

  docker logs kafka

### 3. Create the Kafka Topic

The topic must be created manually before the services can send and receive messages. Open a terminal and run the following command to enter the Kafka container and create the topic:

  docker exec -it kafka /bin/bash
  kafka-topics --create --topic my-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
  exit
  
### 4. Run the Microservices

Producer Service: Navigate to the producer-service directory. You can run the application directly from your IDE or use the Maven command:

  cd producer-service
  mvn spring-boot:run

This service will run on http://localhost:8081.

Consumer Service: Open a new terminal and navigate to the consumer-service directory. Run the application:


  cd consumer-service
  mvn spring-boot:run

This service will run on http://localhost:8080.

### 5. Send a Message

With both services running, you can send a message to the producer's endpoint. You can use your web browser or a tool like curl:

  curl "http://localhost:8080/send?message=Hello from the producer!"

If everything is working correctly, you will see a message logged in the console of the consumer-service terminal, confirming that the message was received.

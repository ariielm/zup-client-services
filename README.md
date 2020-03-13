# zup-client-services

A **RESTful Java application** containing the responsible services for managing the Zup clients.

This project was created only for technical evaluation purposes.

To check **only** the application running locally, jump to the section [Docker-compose (DB + application)](#docker-compose-database--application).

## Table of Contents

* [Technologies](#technologies)
* [Pre-requisites](#pre-requisites)
* [Build](#build)
* [Run](#run)
* [API Documentation](#api-documentation)
* [Automated Tests](#automated-tests)
* [Manual Tests](#manual-tests)
* [Docker container](#run)
* [Docker-compose (database + application)](#docker-compose-database--application)

## Technologies

* [Java Development Kit 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Web, JPA, Actuator, HATEOAS, REST Docs](https://spring.io/projects/)
* [Maven build tool](https://maven.apache.org/)
* [PostgreSQL](https://www.postgresql.org/) 
* [Postman](https://www.postman.com/)
* [Docker](https://www.docker.com/)
* [Docker-compose](#docker-compose-database--application)

## Pre-requisites

1. JDK 8 installed;
2. Docker installed;
3. Docker-compose installed (just in case of running the database + application).

## Build

The Apache Maven was used as software project management. To build the application:

```
./mvnw clean package
```

## Run

Launch first the PostgreSQL container, so the application can connect to the database (the user and password are written on the command just to simplify). 

```
docker run --name postgres -e 'POSTGRES_USER=client-services' -e 'POSTGRES_PASSWORD=client-services-password' -p 5432:5432 -d postgres
```

Then launch the application with Maven:

```
./mvnw spring-boot:run
```

The application will execute on the port 8080: http://localhost:8080/actuator/info

## API Documentation

Once the application is running, to see the documentation, just access:

http://localhost:8080/docs/zup-client-services.html

## Automated Tests

To check the tests and its results, run the Maven goal:

```
./mvnw test
```

## Manual Tests

The manual tests were made with Postman. To run them, just import the collection with the scenarios on Postman that are located on the directory `postman`.

It's included the **successful CRUD scenarios**, and some **validation errors scenarios**.

## Docker

The application also has its own Docker container. The container specification is located on the default path with `Dockerfile`. To build it, just run:

```
docker build -t zup-client-services .
```

To execute the container, some problems may occur due to the database connection. To run the container, go to the next topic with [docker-compose](#docker-compose-database--application).

## Docker-Compose (database + application)

To check the docker container running locally with the complete environment, just execute the [maven build](#build) step, then execute the [docker image](#docker) step, and then run the [docker-compose](https://docs.docker.com/compose/install/) command (if the database container is still running, stop it):

```
docker-compose up -d
```

# zup-client-services

A **RESTful Java application** containing the responsible services for managing the Zup clients.

This project was created only for technical evaluation purposes.

## Table of Contents

* [Technologies](#technologies)
* [Pre-requisites](#pre-requisites)
* [Build](#build)
* [Run](#run)
* [Automated Tests](#automated-tests)
* [Manual Tests](#manual-tests)

## Technologies

* JDK 8
* Spring Boot
* Spring Web, JPA, Actuator, HATEOAS
* PostgreSQL 
* Docker
* Postman

## Pre-requisites

1. JDK 8 installed.
2. Docker installed. 

## Build

```bash
./mvnw clean package
```

## Run

Launch first the PostgreSQL container, so the application can connect to the database (the user and password are written on the command just to simplify). 

```bash
docker run --name postgres -e 'POSTGRES_USER=client-services' -e 'POSTGRES_PASSWORD=client-services-p@$$w0rd' -p 5432:5432 -d postgres
```

## Automated Tests

## Manual Tests
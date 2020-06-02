## Overview
This is a sample project for [ASCENDING](https://www.ascendingdc.com/services/training) java back-end SDE training.

### Project Technical Overview:
This application is developed in Spring Framework by using Spring Boot, Hibernate, Spring RESTful web services, Postman, Maven, PostgresSql, Docker, Amazon SQS, and Amazon S3.
* Business Rules
   1. zhang3
   1. li4
* Development Approaches
   1. zhang3
   1. li4


## Configure local environment
### setup local database with docker 
Refer postgres docker [image](https://hub.docker.com/_/postgres) for environment option.
```
docker run --name ${PostgresContainerName} -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${databaseName} -p ${hostport}:${containerport} -d postgres
```
### migrate database schema
Refer to flyway setup [documentation](https://flywaydb.org/documentation/migrations), find all [migration schema](src/main/resources/db/migrate)
```
mvn clean compile flyway:migrate
```  
# ASCENDING Training course by Liwei Wang & Ryo Hang
## Description
This is a sample project for [ASCENDING](https://www.ascendingdc.com/services/training) java back-end SDE training.

# Branch information

* [master branch](https://github.com/daveywang/Training-Project/tree/master) maintains the latest code
* [warmup_branch](https://github.com/daveywang/Training-Project/tree/warm-up) maintains the java models, jdbc, unit test
* [hibernate_branch](https://github.com/daveywang/Training-Project/tree/hibernate) maintains hibernate implementation
* [springBoot_branch](https://github.com/daveywang/Training-Project/tree/spring-boot) maintains controllers implementation
* [jwt_branch](https://github.com/daveywang/Training-Project/tree/jwt) maintains security implementation
* [aws-s3-sqs_branch](https://github.com/daveywang/Training-Project/tree/aws-s3-sqs)  maintains aws and third party implementation

## Configure local environment
### setup local database with docker 
Refer postgres docker [image](https://hub.docker.com/_/postgres) for environment option.
```
docker run --name ${PostgresContainerName} -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${databaseName} -p ${hostport}:${containerport} -d postgres
```
### migrate database schema
Refer to flyway setup [documentation](https://flywaydb.org/documentation/migrations), find all [migration schema](src/main/resources/db/migrate)
 ```xml
      <plugin>
          <groupId>org.flywaydb</groupId>
          <artifactId>flyway-maven-plugin</artifactId>
          <version>${flyway.version}</version>
          <configuration>
            <driver>org.postgresql.Driver</driver>
            <url>jdbc:postgresql://localhost:5431/databaseName</url>
            <user>admin</user>
            <password>password</password>
            <schemas>
               <schema>public</schema>
            </schemas>
          </configuration>
       </plugin>
  ```

```
mvn clean compile flyway:clean flyway:migrate -Ddatabase_url=localhost:port/your_database -Ddatabase_user=your_username -Ddatabase_password=your_password
```  

### runtime environment variable
 ```
        -Ddatabase.driver=org.postgresql.Driver
        -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
        -Ddatabase.url=jdbc:postgresql://localhost:5431/databaseName
        -Ddatabase.user=admin
        -Ddatabase.password=password
        -Daws.accessKeyId=AWS accesskey
        -Daws.secretKey=AWS secretKey
        -Dspring.profiles.active=dev
        -Daws.s3.bucketName=my own bucket name
        -Daws.sqs.name=my own queue name
    ```

# About ASCENDING

ASCENDING is an AWS Consulting Select Partner and focuses on AWS with experts having deployed AWS solutions since 2012. We have successfully worked with startups, Mid-size businesses and Education organizations to meet their needs of AWS solutions, custom training and support.


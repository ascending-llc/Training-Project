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
        -Daws.s3.bucket=my own bucket name
        -Daws.region=my own region
        -Daws.sqs.name=my own queue name
        -Dsecret.key=any random key string
    ```

## About ASCENDING
ASCENDING is an AWS Certified Consulting Partner. ASCENDING provides Cloud Strategic Consulting service, Cloud Migration, Cloud Operation and web/application development services to our organization clients. ASCENDING also provides IT training (on-site bootcamp and online Udemy class) to individual customers. 
* Our recent client success [stories](https://ascendingdc.com/clientssuccesses)
* Our training [contents](https://ascendingdc.com/services/training)
* AWS Select Partner [capabilities](https://aws.amazon.com/partners/find/partnerdetails/?n=ASCENDING%20LLC&id=0010L00001v2JNtQAM)
* ASCENDING [Youtube Vlog](https://www.youtube.com/channel/UCi5_sn38igXkk-4hsR0JGtw/)

![AWS Select Partner](https://ascendingdc.com/static/media/asc_frugalops_part2_pic2.4a1c27d4.jpg)
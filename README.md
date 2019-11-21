# ASCENDING Training course by Liwei Wang

# Branch information

master branch maintains the latest code

jwt branch maintains the code that uses JWT to implement authentication and authorization for Restful API. The code is built on the spring-boot branch. This branch code is for the seventh and the eighth training classes.

The code demonstrates:
1. how to secure your Restful API by using JWT and filter.
2. how to implement authentication for Restful API.
3. how to implement authorization for Restful API.

# Database Migration

Uses flyway as migration tool

mvn clean compile flyway:clean flyway:migrate -Ddb_host=localhost:port -Ddb_name=your_database -Ddb_username=your_username -Ddb_password=your_password
mvn clean compile flyway:clean flyway:migrate -Ddb_host=localhost:5431 -Ddb_name=training_dev -Ddb_username=admin -Ddb_password=xxxxx

# About ASCENDING

ASCENDING is an AWS Consulting Select Partner and focuses on AWS with experts having deployed AWS solutions since 2012. We have successfully worked with startups, Mid-size businesses and Education organizations to meet their needs of AWS solutions, custom training and support.


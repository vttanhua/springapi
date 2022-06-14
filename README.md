# Spring api

#Usage notes

1. Clone repository
2. Run all tests in command line in project root directory: mvn  test
3. Run project from command line (by default on port 8080): mvn  spring-boot:run 
4. Import postman_collection.json file to postman and test the endpoints.

Application uses in memory database. Fetcing conversion rates from Apilayer is
scheduled using Quarts. Sheduling is done in SpringapiApplication.java using commandline runner. 
It also initiates immediate run but it can take about a minute before first fetch is done. During that time
conversion data is not available in the application. 

## Useful links

#### H2 sql console
http://localhost:8080/api/v1/h2-console/login.do

#### API endpoints
Convert amount between currencies
http://localhost:8080/api/v1/exchange/EUR/SEK/124.4 

#### OpenAPI documentation
https://apilayer.com/marketplace/exchangerates_data-api#documentation-tab

#### Swagger OpenAPI documentation and definition
http://localhost:8080/api/v1/swagger-ui/index.html
http://localhost:8080/api/v1/v3/api-docs

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Quartz Scheduler](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-quartz)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring OpenAPI doc](https://springdoc.org/)

### Additional documentation

This project uses code from below site to implement scheduling:
* [Quartz Scheduler](https://stackabuse.com/guide-to-quartz-with-spring-boot-job-scheduling-and-automation/)


### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)


### Local development
aws s3api create-bucket --bucket local-s3-bucket  --endpoint-url http://localhost:4566
aws s3api put-object  --bucket local-s3-bucket  --key testS3.txt  --body testS3.txt  --endpoint-url http://localhost:4566
aws s3api get-object  --bucket local-s3-bucket  --key testS3.txt  --endpoint-url http://localhost:4566  testS3Output.txt

### Commands

docker-compose up
If you have modified something remember to use:
docker-compose up --build

mvn spring-boot:build-image -Dmaven.test.skip -Dspring.profiles.active=default  -Dspring-boot.build-image.imageName=vttanhua/springapi:0.0.1-SNAPSHOT
docker run -it -p8080:8080 springapi:0.0.1-SNAPSHOT  "-Dspring.profiles.active=default"

mvn clean package -Dspring.profiles.active=default -Dmaven.test.skip

run application using default profile:
mvn spring-boot:run -Dspring.profiles.active=default

mvn spring-boot:run -Dspring.profiles.active=production

run single test:
mvn -Dtest=ExchangeRatesFetchServiceTest#testIsExchangeRateCreatedWhenNotExists test

run single test with profile:
mvn -Dtest=ExchangeRatesFetchServiceTest#testIsExchangeRateCreatedWhenNotExists test -Dspring.profiles.active=unittest

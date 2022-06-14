FROM eclipse-temurin:17-jre

RUN mkdir /opt/app

COPY ./target/springapi-0.0.1-SNAPSHOT.jar /opt/app/

CMD ["java", "-jar",  "/opt/app/springapi-0.0.1-SNAPSHOT.jar"]
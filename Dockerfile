FROM amazoncorretto:17.0.4-alpine3.15
COPY ./target/bank-0.0.1-SNAPSHOT.jar bank-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","/bank-0.0.1-SNAPSHOT.jar"]

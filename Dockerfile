FROM amazoncorretto:17
LABEL maintainer="nicouemacapdevila@gmail.com"
COPY ./target/bank-0.0.1-SNAPSHOT.jar bank-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/bank-0.0.1-SNAPSHOT.jar"]

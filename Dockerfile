FROM openjdk:17-jdk-alpine
MAINTAINER company.org
COPY build/libs/rest-api-1.0-SNAPSHOT.jar rest-api-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rest-api-1.0-SNAPSHOT.jar"]
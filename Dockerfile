FROM openjdk:8-jdk-alpine
MAINTAINER k.rahul2604@gmail.com
VOLUME /root
COPY build/libs/transaction-service-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java","-jar","/transaction-service-0.0.1-SNAPSHOT.jar"]
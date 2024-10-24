#FROM openjdk:8-jdk-alpine
FROM alpine:latest

ENV JAVA_HOME="/usr/lib/jvm/default-jvm/"
RUN apk add openjdk11

# Has to be set explictly to find binaries 
ENV PATH=$PATH:${JAVA_HOME}/bin
COPY "target/AnomalyDetection-0.0.1-SNAPSHOT.jar" "AnomalyDetection.jar"
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "AnomalyDetection.jar" ]
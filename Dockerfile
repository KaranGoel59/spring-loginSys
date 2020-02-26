# Start with a base image containing Java runtime
FROM openjdk:11
MAINTAINER Karan Goel <karangoel59@gmail.com>
VOLUME /tmp
EXPOSE 4000
ARG JAR_FILE=web/target/web-1.0-SNAPSHOT.jar
RUN mkdir -p /app/
ADD ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]


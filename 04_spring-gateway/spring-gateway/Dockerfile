#Start with a base image containing Java runtime
FROM openjdk:17-slim

# Add Maintainer Info
LABEL maintainer="Alex <contacto@devs4j.com>"

# The application's jar file
ARG JAR_FILE

RUN echo ${JAR_FILE}

# Add the application's jar to the container
COPY ${JAR_FILE} /app.jar

#execute the application
ENTRYPOINT ["java","-jar","/app.jar"]


# docker run --network host -p 9095:9095 alx/spring-gateway:0.0.1-SNAPSHOT
FROM java:8
FROM maven:alpine

# image layer
WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
ADD ./target/TPatos-1.0-SNAPSHOT.jar /developments/
ENTRYPOINT ["java","-cp","/developments/TPatos-1.0-SNAPSHOT.jar","test"]

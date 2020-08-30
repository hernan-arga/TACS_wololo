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
ADD ./target/wololo-0.0.1-SNAPSHOT.jar /developments/
ENTRYPOINT ["java","-jar","/developments/wololo-0.0.1-SNAPSHOT.jar"]

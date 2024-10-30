FROM amazoncorretto:21-alpine-jdk
VOLUME /tmp
EXPOSE 8080
COPY target/spaceShipManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
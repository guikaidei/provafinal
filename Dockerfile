FROM amazoncorretto:21

COPY target/provafinal-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

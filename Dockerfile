
FROM openjdk:11
ADD target/auth-0.0.1-SNAPSHOT.jar auth-0.0.1-SNAPSHOT.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "auth-0.0.1-SNAPSHOT.jar"]
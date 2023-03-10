FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target/Conditional_App-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]
FROM openjdk:17-alpine
ADD target/*.jar banking-service.jar
ENTRYPOINT ["java","-jar","banking-service.jar"]

FROM eclipse-temurin:17-jdk-alpine

COPY target/homi-manager.jar homi-manager.jar

ENTRYPOINT ["java","-jar","homi-manager.jar"]

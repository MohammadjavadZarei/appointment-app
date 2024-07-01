FROM docker.arvancloud.ir/openjdk:17-jdk-slim
VOLUME /tmp
COPY . .
ENTRYPOINT ["java","-jar","target/appointment-application-0.0.1-SNAPSHOT.jar"]

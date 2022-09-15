FROM openjdk:11-jdk-oracle
WORKDIR warranty
ADD target/warranty-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar app.jar
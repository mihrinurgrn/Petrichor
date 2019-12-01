 FROM openjdk:8
ADD target/petrichor-0.0.1-SNAPSHOT.jar petrichor/0.0.1-SNAPSHOT/petrichor-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","petrichor-0.0.1-SNAPSHOT.jar"]





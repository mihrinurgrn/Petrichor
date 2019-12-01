FROM openjdk:8
EXPOSE 8080:8080
ADD target/petrichor-0.0.1-SNAPSHOT.jar petrichor-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","petrichor-0.0.1-SNAPSHOT.jar"]





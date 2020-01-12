# petrichor-project #
Petrichor is a web-based audience interaction tool for meetings and events. Petrichor enables users to crowdsource top questions to drive meaningful conversations, engage participants with live polls and collect valuable interaction insights. It focuses on simplicity, both for meeting planners who can create an event in less than a minute, as well as for participants who can join via any device with a simple code.

## Getting Started ##
First of all you should change username, password and db name with your username password and db name for Postgresql (Line 6,8,9 in application.properties)
and you should run the project after that you should go to  http://localhost:8080/

and also you can run with docker

### create your image ###
`docker build ./ -t petrichor12`

### see your image on the list ###
`sudo docker image list -a`

### run the container ###

 `docker-compose up`

# Dependency #
* Java 1.8
* Postgresql 10
* Springboot 2.2.0
* Maven 3.6.0
* Docker 19.03.5


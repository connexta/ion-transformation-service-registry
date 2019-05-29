#!/bin/bash

# builds the project and creates the JAR file
mvn clean install
# uses the Dockerfile and names the resulting image "transformation-service-registry"
docker build -t transformation-service-registry -f ./Dockerfile .

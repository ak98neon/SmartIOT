#!/bin/sh

mvn clean && mvn install
docker build -t arkudrya/smart-iot ../
docker tag arkudrya/smart-iot arkudrya/smart-iot:latest
docker-compose -f docker-compose.service.yml -p iot-env up -d
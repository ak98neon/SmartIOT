#!/bin/sh

docker rm -f arkudrya/smart-iot
docker-compose -f docker-compose.service.yml -p iot-env stop
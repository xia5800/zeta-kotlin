#!/bin/bash
docker-compose down
docker rmi zeta_kotlin_server
docker-compose up -d

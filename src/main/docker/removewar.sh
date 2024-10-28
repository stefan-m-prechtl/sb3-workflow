#!/bin/bash
docker stop tomcat
if [ -e ./webapps/demo.war ]; then
    rm ./webapps/demo.war
fi
echo dsemrbw | sudo -S rm -Rf ./webapps/demo

cp ../../../build/libs/demo.war ./webapps/

docker start tomcat
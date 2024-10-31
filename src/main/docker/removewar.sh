#!/bin/bash
docker stop tomcat
if [ -e ./webapps/demo.war ]; then
    rm ./webapps/demo.war
fi
echo 'geheim' | sudo -S rm -rf ./webapps/demo

cp ../../../build/libs/workflow.war ./webapps/

docker start tomcat
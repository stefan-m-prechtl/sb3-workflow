# base image
FROM tomcat:10.1.31-jdk21-temurin-noble

# copy libs JDBC-driver: Google Guava
COPY postgresql-42.7.4.jar /usr/local/tomcat/lib/lib
COPY guava-31.1-jre.jar /usr/local/tomcat/lib/
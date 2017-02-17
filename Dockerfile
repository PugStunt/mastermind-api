FROM tomcat:8-jre8
MAINTAINER rochapaulo

ADD mastermind-web/target/mastermind-web.war /usr/local/tomcat/webapps/mastermind.war

EXPOSE 8080

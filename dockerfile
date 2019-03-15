FROM tomcat:8-jre8
MAINTAINER MM

RUN echo "export JAVA_OPTS=\"-Dapp.env=info\"" > /usr/local/tomcat/bin/setenv.sh

RUN rm -rf  /usr/local/tomcat/webapps/info-client.war
RUN rm -rf  /usr/local/tomcat/webapps/info-client
RUN rm -rf  /usr/local/tomcat/webapps/ROOT
COPY ./info-client.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]


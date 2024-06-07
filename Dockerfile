FROM tomcat:9.0.89-jdk17-corretto

RUN ["rm", "/etc/localtime"]
RUN ["ln", "-sf", "/usr/share/zoneinfo/Asia/Seoul", "/etc/localtime"]

RUN ["apt-get", "update"]
RUN ["apt-get", "install", "vim", "-y"]

COPY ./target/gajang-backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 9002

#start tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
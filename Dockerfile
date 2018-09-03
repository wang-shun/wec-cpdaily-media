FROM 172.16.9.100:5000/alpine-tomcat-node:1.1.0
ENV JAVA_OPTS="-server -Xms512m -Xmx1024m"
ARG PROJECT_WAR_PATH
ARG PROFILE

ADD . /app

RUN /dependency/apache-maven-3.3.9/bin/mvn clean install -f /app/pom.xml -P ${PROFILE} && \
    mv /app/${PROJECT_WAR_PATH} /apache-tomcat-8.0.36/webapps/ && \
    rm -rf /dependency && \
    rm -rf /app && \
    rm -rf /root/.m2 && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


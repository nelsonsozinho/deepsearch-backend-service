FROM maven:3.6.3-jdk-14

ADD . /usr/src/shire42
WORKDIR /usr/src/shire42
EXPOSE 4567
ENTRYPOINT ["mvn", "clean", "verify", "exec:java"]
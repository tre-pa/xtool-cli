FROM debian:stretch

RUN apt-get update && \
    apt-get install default-jre -y && \
    apt-get install git -y && \
    apt-get install graphviz -y

RUN apt-get install -y locales -qq && \
    locale-gen en_US.UTF-8 en_us && \
    dpkg-reconfigure locales && \
    dpkg-reconfigure locales && \
    locale-gen C.UTF-8 && \
    /usr/sbin/update-locale LANG=C.UTF-8

ENV LANG C.UTF-8

ENV LANGUAGE C.UTF-8

ENV LC_ALL C.UTF-8

COPY target/xtool-cli.jar xtool-cli.jar

ENTRYPOINT ["java",  "-Dworkspace=/opt/workspace", "-Drepository.home=/opt/repository", "-jar", "/xtool-cli.jar"]



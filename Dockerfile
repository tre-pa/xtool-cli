FROM debian:stretch

RUN apt-get update && \
    apt-get install maven -y &&\
    apt-get install default-jdk -y && \
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

COPY target/xtool-cli-2.0.0.jar xtool-cli.jar

ENTRYPOINT ["java", "-Dxtool.home=/opt/xtool", "-cp", "/xtool-cli.jar", "-Dloader.path=/opt/xtool/components", "org.springframework.boot.loader.PropertiesLauncher"]



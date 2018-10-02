FROM jenkins/jnlp-slave:3.26-1
MAINTAINER Chew Boon Heng <boonheng85@gmail.com>

EXPOSE 8080 50000

USER root

# install docker
ENV OS_VERSION stretch
ENV DOCKER_VERSION docker-ce_17.03.3~ce-0~debian-stretch_amd64.deb
RUN apt-get update && apt-get install -y vim unzip sudo iptables \
    init-system-helpers libapparmor1 libltdl7 libseccomp2 libdevmapper1.02.1 \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir /downloads \
    && curl -k -o /downloads/${DOCKER_VERSION} https://download.docker.com/linux/debian/dists/$OS_VERSION/pool/stable/amd64/$DOCKER_VERSION \
    && dpkg -i /downloads/$DOCKER_VERSION

ENV GRADLE_VERSION 4.6
RUN curl -o /downloads/gradle-${GRADLE_VERSION}-all.zip https://downloads.gradle.org/distributions/gradle-${GRADLE_VERSION}-all.zip \
    && mkdir /opt/gradle && unzip -d /opt/gradle /downloads/gradle-${GRADLE_VERSION}-all.zip

RUN usermod -aG docker jenkins

#COPY tools/jdk-8u171-linux-x64.tar.gz /downloads/java.tar.gz
#RUN tar zxvf /downloads/java.tar.gz && mkdir -p /usr/java && mv jdk* /usr/java/ && rm -f /downloads/java.tar.gz \
#    && chown -R jenkins:jenkins /downloads

USER jenkins

RUN mkdir /home/jenkins/gradle-cache

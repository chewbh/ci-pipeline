FROM jenkins/jenkins:lts
MAINTAINER Chew Boon Heng <boonheng85@gmail.com>

EXPOSE 8080 50000

USER root

# 1.13.1
ENV OS_VERSION stretch
ENV DOCKER_VERSION docker-ce_17.12.0~ce-0~debian_amd64.deb
ENV GRADLE_VERSION 4.5
ENV MAVEN_VERSION 3.5.2

# Install docker
RUN apt-get update && apt-get install -y sudo iptables \
    init-system-helpers libapparmor1 libltdl7 libseccomp2 libdevmapper1.02.1 \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir /downloads \
    && curl -o /downloads/${DOCKER_VERSION} https://download.docker.com/linux/debian/dists/$OS_VERSION/pool/stable/amd64/$DOCKER_VERSION \
    && dpkg -i /downloads/$DOCKER_VERSION \
    && curl -o /downloads/gradle-${GRADLE_VERSION}-all.zip https://downloads.gradle.org/distributions/gradle-${GRADLE_VERSION}-all.zip \
    && mkdir /opt/gradle && unzip -d /opt/gradle /downloads/gradle-${GRADLE_VERSION}-all.zip \
    && curl -o /downloads/apache-maven-${MAVEN_VERSION}-bin.tar.gz http://www-us.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    && mkdir /opt/maven && tar -xf /downloads/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt/maven \
    && curl -L -b "oraclelicense=a" http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-x64.tar.gz -o /downloads/jdk-8u161-linux-x64.tar.gz \
    && curl -L -b "oraclelicense=a" http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/c2514751926b4512b076cc82f959763f/jdk-9.0.4_linux-x64_bin.tar.gz -o /downloads/jdk-9.0.4_linux-x64_bin.tar.gz \
    && chown -R jenkins:jenkins /downloads

USER jenkins

#    No admin password setup
ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false"

# Install jenkins plugins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# Add groovy setup scripts
COPY init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/
COPY generate-ssh-key.sh /usr/local/bin/generate-ssh-key.sh
COPY entry-point.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
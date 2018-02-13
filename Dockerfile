FROM jboss/wildfly

RUN mkdir -p /tmp/dropservice

COPY target/drop.war /opt/jboss/wildfly/standalone/deployments/

FROM jboss/wildfly

COPY target/drop.war /opt/jboss/wildfly/standalone/deployments/

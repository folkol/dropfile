FROM jboss/wildfly

RUN mkdir -p /tmp/dropservice

COPY target/drop.war /opt/jboss/wildfly/standalone/deployments/

COPY standalone.xml /opt/wildfly/standalone/configuration/standalone.xml

CMD /opt/wildfly/bin/standalone.sh -b 0.0.0.0

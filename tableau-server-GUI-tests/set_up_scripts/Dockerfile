FROM tableau_server_image:20213.21.0917.1006
USER root
RUN curl -L -o /tmp/EXASOL_ODBC.tar.gz https://www.exasol.com/support/secure/attachment/119101/EXASOL_ODBC-7.1.1.tar.gz
RUN curl -L -o /tmp/EXASOL_JDBC.tar.gz https://www.exasol.com/support/secure/attachment/161232/EXASOL_JDBC-7.1.1.tar.gz
RUN mkdir -p /opt/exasol/odbc
RUN mkdir -p /opt/exasol/jdbc
RUN tar -xzf /tmp/EXASOL_ODBC.tar.gz --directory=/opt/exasol/odbc --strip-components 1
RUN tar -xzf /tmp/EXASOL_JDBC.tar.gz --directory=/opt/exasol/jdbc --strip-components 1
RUN mkdir -p /opt/tableau/tableau_driver/jdbc
RUN cp /opt/exasol/jdbc/exajdbc.jar /opt/tableau/tableau_driver/jdbc
RUN echo -e "[EXASolution Driver]\nDriver=/opt/exasol/odbc/lib/linux/x86_64/libexaodbc-uo2214lv2.so\n" >> /etc/odbcinst.ini

USER tableau:tableau
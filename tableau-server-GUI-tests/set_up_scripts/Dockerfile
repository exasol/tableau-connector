FROM tableau_server_image:20211.21.0208.1940
USER root
COPY ./setup_exasol_odbc_driver.sh /setup_exasol_odbc_driver.sh
RUN chmod 777 setup_exasol_odbc_driver.sh && ./setup_exasol_odbc_driver.sh
USER tableau:tableau
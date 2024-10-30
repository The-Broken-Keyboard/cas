ARG BASE_IMAGE="azul/zulu-openjdk:21"

FROM $BASE_IMAGE AS cas

WORKDIR /app
COPY . .
EXPOSE 8080 8443
ENV PATH $PATH:$JAVA_HOME/bin:.

# Entrypoint file at entrypoint.sh
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
ARG SPARK_VERSION=2.2.1
ARG SPARK_BINARY_ARCHIVE_NAME=spark-${SPARK_VERSION}-bin-hadoop2.7

FROM openjdk:8

# Spark related variables.
ARG SPARK_VERSION=2.2.1
ARG SPARK_BINARY_ARCHIVE_NAME=spark-${SPARK_VERSION}-bin-hadoop2.7
ARG SPARK_BINARY_DOWNLOAD_URL=http://apache.volia.net/spark/spark-${SPARK_VERSION}/${SPARK_BINARY_ARCHIVE_NAME}.tgz

RUN \
apt-get update && \
apt-get install git
RUN apt-get install scala -y
RUN wget -qO - ${SPARK_BINARY_DOWNLOAD_URL} | tar -xz -C /usr/local/ && \
cd /usr/local/ && \
ln -s ${SPARK_BINARY_ARCHIVE_NAME} spark && \
cp spark/conf/log4j.properties.template spark/conf/log4j.properties && \
sed -i -e s/WARN/ERROR/g spark/conf/log4j.properties && \
sed -i -e s/INFO/ERROR/g spark/conf/log4j.properties


RUN git init
RUN git remote add origin https://github.com/olehpidhi/count_triangles.git
RUN git fetch
RUN git checkout -t origin/master

USER root

EXPOSE 4040 8080 8081

CMD ["cd", "distributedalgorithms"]
CMD ["/usr/local/spark/bin/spark-submit", "--master", "local", "target/spark_triangles-1.0.jar", "followers.txt"]

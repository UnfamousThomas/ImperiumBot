# Written for a containerized version of our daemon.
# OpenJDK JRE 8
FROM openjdk:8

# Specify the Java arguments -Xmx and -Xms
ENV JAVA_MIN_HEAP=512M
ENV JAVA_MAX_HEAP=512M


# Copy jars
COPY target/ImperiumBot-jar-with-dependencies.jar /data/Bot.jar

# Change working directory so that server's files are created there
WORKDIR /data/

# Run the downloaded jarfile with the previously specified arguments
CMD java -jar -Xmx${JAVA_MIN_HEAP} -Xms${JAVA_MAX_HEAP} /data/Bot.jar
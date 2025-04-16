FROM openjdk:21-jdk
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
COPY target/budgetbuddy-0.0.1-SNAPSHOT.jar budgetbuddy-0.0.1.jar
ENTRYPOINT ["java","-jar","/budgetbuddy-0.0.1.jar"]
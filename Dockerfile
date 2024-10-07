FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/acebook-template-1.0-SNAPSHOT.jar /app/jacebook.jar
EXPOSE 8080

ENV JDBC_CONNECTION=$JDBC_CONNECTION
ENV JDBC_USER_NAME=$JDBC_USER_NAME
ENV JDBC_PASSWORD=$JDBC_PASSWORD
ENV OKTA_ISSUER_URL=$OKTA_ISSUER_URL
ENV OKTA_CLIENT_ID=$OKTA_CLIENT_ID
ENV OKTA_CLIENT_SECRET=$OKTA_CLIENT_SECRET

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/jacebook.jar"]

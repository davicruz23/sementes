# Use a imagem oficial do OpenJDK como imagem base
FROM openjdk:11-jre-slim

# Adiciona o JAR da aplicação ao container
ARG JAR_FILE=target/sementes-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Define o comando padrão para executar a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]

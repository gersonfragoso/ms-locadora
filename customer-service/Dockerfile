# Fase de build: Usar a imagem Maven para construir o JAR
FROM maven:3.9.5 as build

# Define o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo pom.xml e o código fonte para o container
COPY pom.xml .
COPY src ./src

# Faz o build da aplicação
RUN mvn clean package -DskipTests

# Use uma imagem mais leve do JDK para a fase final
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o .jar gerado do build para o container
COPY --from=build /app/target/*.jar .


# Define o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "customer-service-0.0.1-SNAPSHOT.jar"]
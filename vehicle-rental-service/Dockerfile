# Fase de build: Usar a imagem Maven para construir o JAR
FROM maven:3.9.5 as build

# Diretório de trabalho no container
WORKDIR /app

# Copia o pom.xml e a estrutura do código fonte para o container
COPY pom.xml ./
COPY src ./src

# Gera o JAR dentro do container
RUN mvn clean package -DskipTests

# Fase de execução: Usar uma imagem leve do JDK para rodar a aplicação
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho no container
WORKDIR /app

# Copia o JAR gerado da fase de build
COPY --from=build /app/target/vehicle-rental-service-1.jar vehicle-rental-service.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "vehicle-rental-service.jar"]

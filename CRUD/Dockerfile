# --- ETAPA 1: COMPILAÇÃO (BUILD) ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia tudo do seu projeto para dentro do construtor
COPY . .

# Roda o comando para gerar o .jar novo (pulando testes para ser rápido)
RUN mvn clean package -DskipTests

# --- ETAPA 2: EXECUÇÃO (RUN) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Pega o JAR novinho que foi criado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

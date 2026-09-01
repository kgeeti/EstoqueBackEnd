# ===================================================
# FASE 1: BUILD DA APLICAÇÃO (Maven + JDK 21)
# ===================================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de dependência primeiro para otimizar o cache de camadas do Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte da aplicação
COPY src ./src

# Compila o projeto e gera o arquivo .jar ignorando os testes unitários
RUN mvn clean package -DskipTests

# ===================================================
# FASE 2: RUNTIME DA APLICAÇÃO (JRE 21 Leve)
# ===================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Cria um usuário não-root por motivos de segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o .jar compilado na Fase 1 para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Render injeta a porta dinamicamente via variável de ambiente PORT (padrão 10000)
EXPOSE 8080


# Comando para iniciar a aplicação Java
ENTRYPOINT ["java", "-jar", "app.jar"]

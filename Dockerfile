# ==========================================
# Estágio 1: Build (Compilação do Projeto)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de configuração do Maven para aproveitar o cache de dependências
COPY pom.xml .

# Baixa as dependências sem compilar o código (otimiza builds futuros)
RUN mvn dependency:go-offline -B

# Copia todo o código fonte do projeto
COPY src ./src

# Executa o build gerando o arquivo .jar (ignora os testes para agilizar o deploy)
RUN mvn clean package -DskipTests

# ==========================================
# Estágio 2: Execução (Imagem Final Leve)
# ==========================================
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copia apenas o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão que o Spring Boot utiliza
EXPOSE 8080

# Comando para inicializar a API
ENTRYPOINT ["java", "-jar", "app.jar"]
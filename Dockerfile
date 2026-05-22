# ==========================================
# Estágio 1: Build com Gradle
# ==========================================
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Copia os arquivos do Gradle Wrapper primeiro (cache de layers)
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# Copia os arquivos de configuração do build
COPY build.gradle .
COPY settings.gradle .

# Baixa as dependências sem compilar (otimiza builds futuros)
RUN ./gradlew dependencies --no-daemon || true

# Copia o código fonte
COPY src ./src

# Gera o .jar ignorando os testes
RUN ./gradlew bootJar -x test --no-daemon

# ==========================================
# Estágio 2: Imagem final leve
# ==========================================
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Render injeta $PORT — o Spring respeita via SERVER_PORT
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT:-8080} app.jar"]

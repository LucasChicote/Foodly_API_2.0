# Foodly API — Spring Boot + Oracle SQL Developer

## Descrição do Projeto

O Foodly API é o back-end de uma plataforma de delivery de alimentos desenvolvida em Java com Spring Boot. A aplicação gerencia usuários, restaurantes, produtos, categorias e pedidos, oferecendo uma API RESTful completa com autenticação via JWT.

O projeto utiliza Oracle como banco de dados e Flyway para versionamento das migrations. A segurança é implementada com Spring Security, garantindo controle de acesso por perfil de usuário (Cliente, Dono de Restaurante e Administrador).

---

## Arquitetura

A aplicação segue o padrão de camadas:

- **Controller** — recebe as requisições HTTP e delega para os serviços
- **Service** — contém a lógica de negócio
- **Repository** — acesso ao banco de dados via Spring Data JPA
- **Security** — autenticação JWT e controle de acesso por role
- **DTO** — objetos de transferência de dados entre as camadas
- **Model** — entidades mapeadas para as tabelas do banco

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA + Hibernate
- Oracle Database (SQL Developer)
- Flyway (migrations)
- Lombok
- Gradle

---

## Pré-requisitos

Antes de executar, certifique-se de ter instalado:

- [Java 21+](https://adoptium.net/)
- [Oracle Database](https://www.oracle.com/database/technologies/xe-downloads.html) (XE ou SQL Developer)
- Gradle (ou usar o wrapper `./gradlew` incluso no projeto)

---

## Configuração do Banco de Dados

1. Abra o Oracle SQL Developer e conecte ao seu banco
2. Crie um usuário/schema para a aplicação (caso não tenha):

```sql
CREATE USER foodly IDENTIFIED BY sua_senha;
GRANT CONNECT, RESOURCE, DBA TO foodly;
```

3. Edite o arquivo `src/main/resources/application.yaml` com as suas credenciais:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:xe
    username: seu_usuario
    password: sua_senha
    driver-class-name: oracle.jdbc.OracleDriver
  jpa:
    hibernate:
      ddl-auto: none
    database-platform: org.hibernate.dialect.OracleDialect
  flyway:
    enabled: true
    baseline-on-migrate: true
```

> As tabelas são criadas automaticamente pelo Flyway ao iniciar a aplicação pela primeira vez, usando os scripts da pasta `src/main/resources/db/migration/`.

---

## Instruções de Uso

### 1. Clonar o repositório

```bash
git clone https://github.com/LucasChicote/Foodly_API_2.0
```

### 2. Entrar na pasta do projeto

```bash
cd Foodly_API_2.0
```

### 3. Executar a aplicação

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

A API ficará disponível em:

```
http://localhost:8080
```

---

## Perfis de Usuário

| Role | Descrição |
|------|-----------|
| `ROLE_CUSTOMER` | Cliente — pode navegar e fazer pedidos |
| `ROLE_RESTAURANT_OWNER` | Dono de Restaurante — gerencia restaurantes, produtos e pedidos |
| `ROLE_ADMIN` | Administrador — acesso total, gerencia usuários e categorias |

---

## Endpoints Principais

### Autenticação

```
POST /auth/register   → Cadastrar novo usuário
POST /auth/login      → Login e obter token JWT
```

Exemplo de body para login:
```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

Exemplo de resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### Usuários (apenas ADMIN)

```
GET    /usuarios           → Listar todos os usuários
DELETE /usuarios/{id}      → Deletar usuário
GET    /usuarios/cep/{cep} → Buscar endereço pelo CEP
```

---

### Restaurantes

```
GET  /restaurantes        → Listar todos os restaurantes (público)
GET  /restaurantes/meus   → Listar restaurantes do dono autenticado
POST /restaurantes        → Criar restaurante (OWNER ou ADMIN)
```

Exemplo de body para criar restaurante:
```json
{
  "nome": "Mc Donald's",
  "descricao": "FastFood",
  "categoria": "Lanches",
  "imagemUrl": "https://..."
}
```

---

### Produtos

```
GET    /produtos                  → Listar todos (público)
GET    /produtos/restaurante/{id} → Listar por restaurante (público)
GET    /produtos/categoria/{id}   → Listar por categoria (público)
POST   /produtos                  → Criar produto (OWNER ou ADMIN)
DELETE /produtos/{id}             → Deletar produto (OWNER ou ADMIN)
```

Exemplo de body para criar produto:
```json
{
  "nome": "Big Mac",
  "descricao": "Pão Com Gergelim, Dois Hamburguers, Alface, Queijo e Molho Especial.",
  "preco": 24.90,
  "categoriaId": 1,
  "restauranteId": 1,
  "imagemUrl": "https://..."
}
```

---

### Categorias

```
GET    /categorias       → Listar categorias (público)
POST   /categorias       → Criar categoria (apenas ADMIN)
DELETE /categorias/{id}  → Deletar categoria (apenas ADMIN)
```

---

### Pedidos

```
POST  /pedidos                  → Criar pedido (CUSTOMER ou ADMIN)
GET   /pedidos/meus             → Meus pedidos (CUSTOMER ou ADMIN)
GET   /pedidos/restaurante/{id} → Pedidos do restaurante (OWNER ou ADMIN)
PATCH /pedidos/{id}/status      → Atualizar status (OWNER ou ADMIN)
```

Exemplo de body para criar pedido:
```json
{
  "restauranteId": 1,
  "itens": [
    { "produtoId": 1, "quantidade": 2 },
    { "produtoId": 3, "quantidade": 1 }
  ]
}
```

---

## Migrations do Banco (Flyway)

Os scripts SQL ficam em `src/main/resources/db/migration/` e são executados automaticamente na ordem:

| Arquivo | Descrição |
|---------|-----------|
| `V1__init.sql` | Criação de todas as tabelas |
| `V2__insert_dados.sql` | Dados iniciais (categorias padrão) |
| `V3__cascade_delete_usuario.sql` | Adiciona ON DELETE CASCADE nas FK de usuários |
| `V4__imagem_url_para_clob.sql` | Altera coluna imagem_url para CLOB |

---

## Estrutura do Projeto

```
Foodly_API/
│
├── src/
│   ├── main/
│   │   ├── java/com/foodly/foodly/
│   │   │   ├── FoodlyApplication.java
│   │   │   ├── client/
│   │   │   │   └── ViaCepClient.java
│   │   │   ├── config/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── HttpClientConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AutenticacaoController.java
│   │   │   │   ├── CategoriaController.java
│   │   │   │   ├── PedidoController.java
│   │   │   │   ├── ProdutoController.java
│   │   │   │   ├── RestauranteController.java
│   │   │   │   └── UsuarioController.java
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   │   └── JwtAuthFilter.java
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── db/migration/
│   │           ├── V1__init.sql
│   │           ├── V2__insert_dados.sql
│   │           ├── V3__cascade_delete_usuario.sql
│   │           └── V4__imagem_url_para_clob.sql
│   └── test/
│
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

---

## Troubleshooting

**Erro: `ORA-01017` (usuário/senha inválidos)**
```
Verifique as credenciais no arquivo application.yaml.
```

**Erro de Flyway ao iniciar pela primeira vez**
```yaml
# Adicione no application.yaml:
spring:
  flyway:
    baseline-on-migrate: true
```

**Erro: porta 8080 já em uso**
```yaml
# Adicione no application.yaml:
server:
  port: 8081
```

**Erro de CORS no front-end**
```
Verifique em SecurityConfig.java se a origem http://localhost:4200
está na lista de allowedOrigins.
```

---

## Conclusão

A Foodly API oferece:

- Autenticação segura com JWT
- Controle de acesso por perfil de usuário (ADMIN, OWNER, CUSTOMER)
- CRUD completo de restaurantes, produtos, categorias e pedidos
- Integração com ViaCEP para preenchimento automático de endereço
- Versionamento do banco via Flyway
- Suporte a imagens em Base64 armazenadas no banco Oracle

## Vídeo demonstrativo da aplicação:

[![Vídeo de Demonstração](https://img.youtube.com/vi/89RdIERCHqM/hqdefault.jpg)](https://youtu.be/89RdIERCHqM?si=9i-5eB3Z3j2PgVS1)

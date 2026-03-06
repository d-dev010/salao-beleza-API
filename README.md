# Salão Beleza API (Serviços Técnicos)

API REST para agendamento de serviços em um salão de beleza.

## Stack

- Spring Boot 3.5.9
- Java 17
- Spring Web / Validation
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL
- Springdoc OpenAPI (Swagger UI)
- Docker / Docker Compose

## URLs

- **Base URL (local)**: `http://localhost:8081`
- **Swagger UI**: `http://localhost:8081/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`

> No `docker-compose.yml` a API expõe `8081:8080`.

## Como rodar

### 1) Rodar com Maven (sem Docker)

Requisitos:

- Java 17
- Maven
- PostgreSQL rodando localmente (porta `5432`)

Comandos:

```bash
mvn -q -DskipTests package
mvn spring-boot:run
```

Configuração do banco (default em `application.properties`):

- DB: `agendamento_db`
- User: `postgres`
- Pass: `postgres123`

Você pode sobrescrever via variáveis de ambiente:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

### 2) Rodar com Docker Compose

Requisitos:

- Docker Desktop
- Docker Compose

Comandos:

```bash
docker compose up -d --build
```

Containers:

- **Postgres**: `agendamento-postgres` (porta `5432`)
- **API**: `salao-beleza-api` (porta `8081`)

#### Troubleshooting (Windows / Docker)

Se aparecer erro do tipo:

- `dockerDesktopLinuxEngine: The system cannot find the file specified`

Faça:

- Reinicie o Docker Desktop
- Verifique se o backend está como **WSL 2**
- Confirme que o Docker está rodando (Settings -> Resources / WSL Integration)

## Autenticação (JWT)

### Login

- **Endpoint**: `POST /api/v1/auth/login`
- **Body**:

```json
{
  "email": "admin@salao.com",
  "senha": "admin123"
}
```

- **Response**:

```json
{ "token": "<JWT>" }
```

### Usando o token

Em todas as rotas protegidas, envie:

- Header: `Authorization: Bearer <JWT>`

### Usuários de teste (in-memory)

Configurados no `SecurityConfig`:

- `admin@salao.com` / `admin123` (ROLE_ADMIN)
- `stylist@salao.com` / `stylist123` (ROLE_STYLIST)

## CORS

O CORS está permitindo por padrão:

- Origin: `http://localhost:5173`

Se seu frontend rodar em outra porta (ex.: `3000`), ajuste em `SecurityConfig.corsConfigurationSource()`.

## Endpoints

A API atual está padronizada com prefixo `/api/v1` e recursos em português (compatibilidade com o projeto atual):

### Auth

- `POST /api/v1/auth/login`

### Usuários (`/api/v1/usuarios`)

- `POST /api/v1/usuarios`
- `GET /api/v1/usuarios`
- `GET /api/v1/usuarios/{id}`
- `GET /api/v1/usuarios/email?email=...`
- `PUT /api/v1/usuarios/{id}`
- `DELETE /api/v1/usuarios/{id}`

**Acesso** (conforme `SecurityConfig`):

- `ROLE_STYLIST` ou `ROLE_ADMIN`

### Serviços (`/api/v1/servicos`)

- `POST /api/v1/servicos`
- `GET /api/v1/servicos`
- `GET /api/v1/servicos/{id}`
- `PUT /api/v1/servicos/{id}`
- `DELETE /api/v1/servicos/{id}`

Filtro:

- `GET /api/v1/servicos?nome=...`

**Acesso**:

- Está como `permitAll()` no `SecurityConfig`.

### Agendamentos (`/api/v1/agendamentos`)

- `POST /api/v1/agendamentos`
- `GET /api/v1/agendamentos`
- `GET /api/v1/agendamentos/{id}`
- `PATCH /api/v1/agendamentos/{id}` (atualiza status)
- `DELETE /api/v1/agendamentos/{id}`

Filtros:

- `GET /api/v1/agendamentos?status=AGENDADO`
- `GET /api/v1/agendamentos?usuarioId=1`

**Acesso**:

- `ROLE_STYLIST` ou `ROLE_ADMIN`

### Availability (`/api/v1/availability`)

- `GET /api/v1/availability?stylistId=...&date=...`

> Endpoint atual é um **placeholder** (retorna os parâmetros recebidos).

### Stylists (`/api/v1/stylists`)

- `GET /api/v1/stylists`

> Endpoint atual é um **placeholder**.

## Exemplos de teste (curl)

### 1) Login

```bash
curl -X POST "http://localhost:8081/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"admin@salao.com\",\"senha\":\"admin123\"}"
```

Copie o token retornado.

### 2) Listar usuários (protegido)

```bash
curl "http://localhost:8081/api/v1/usuarios" \
  -H "Authorization: Bearer <TOKEN>"
```

### 3) Listar serviços (público)

```bash
curl "http://localhost:8081/api/v1/servicos"
```

### 4) Criar agendamento (protegido)

> Verifique o formato esperado no Swagger (`AgendamentoRequestDTO`).

```bash
curl -X POST "http://localhost:8081/api/v1/agendamentos" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d "{\"dataHora\":\"2026-03-07T10:00:00\",\"nomeCliente\":\"Maria\",\"servicoId\":1,\"usuarioId\":1}"
```

### 5) Atualizar status do agendamento

```bash
curl -X PATCH "http://localhost:8081/api/v1/agendamentos/1" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d "{\"status\":\"CANCELADO\"}"
```

## Observações

- O token JWT atualmente é gerado usando uma chave hardcoded em `JwtUtil`. Para produção, mova para variável de ambiente e **não** commite segredos.
- `docker-compose.yml` ainda contém variáveis `SPRING_SECURITY_USER_*`, mas a autenticação efetiva está sendo feita via JWT + `InMemoryUserDetailsManager` no `SecurityConfig`.

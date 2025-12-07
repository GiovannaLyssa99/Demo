# 🌍 Sistema de Gestão de Turismo

Trabalho final da disciplina de **Software para Persistência de Dados**.

Este projeto é um sistema web Fullstack (Backend + Frontend) para
cadastro, consulta e gestão de pontos turísticos, hospedagens e
avaliações. O diferencial é a utilização de uma arquitetura de
**persistência poliglota (híbrida)**, integrando bancos relacionais,
NoSQL, cache em memória e armazenamento em disco.

------------------------------------------------------------------------

## 🚀 Tecnologias Utilizadas

### Backend & Core

-   **Java 17**
-   **Spring Boot 3.3.0** (Web, Security, Data JPA, Data MongoDB, Data
    Redis)
-   **Maven** (Gerenciador de dependências)
-   **Spring Security + JWT** (Autenticação e Autorização via Token)

### Persistência (Arquitetura Híbrida)

1.  **PostgreSQL (Relacional):**
    -   Armazena dados estruturados e críticos: Usuários, Pontos
        Turísticos e Hospedagens.
    -   Garante integridade referencial e transações ACID.
2.  **MongoDB (NoSQL):**
    -   Armazena dados volumosos e semi-estruturados: Comentários (com
        respostas aninhadas) e Metadados das Fotos.
    -   Permite flexibilidade e escalabilidade para dados de interação.
3.  **Redis (Cache):**
    -   Cache de dados de leitura frequente (Lista de Pontos Turísticos)
        para alta performance.
    -   Estratégia de invalidação (`@CacheEvict`) na
        escrita/atualização.
4.  **Disco Local (Filesystem):**
    -   Armazenamento físico dos arquivos de imagem (upload), salvando
        apenas o caminho/referência no banco.

### Frontend

-   **Thymeleaf:** Renderização de templates no servidor.
-   **HTML5 / JavaScript (Vanilla):** Consumo da API REST.
-   **Bootstrap 5:** Estilização responsiva e componentes visuais.

------------------------------------------------------------------------

## ✨ Funcionalidades Principais

-   **Autenticação:** Sistema de Login e Registro com criptografia de
    senha e JWT.
-   **Pontos Turísticos:** CRUD completo com geolocalização
    (Latitude/Longitude) e Média de Avaliações calculada
    automaticamente.
-   **Multimídia:** Upload de fotos (validado por tamanho e extensão)
    com visualização em galeria.
-   **Hospedagens:** Cadastro de hotéis/pousadas vinculados ao ponto
    turístico com link de reserva.
-   **Interação:** Sistema de Avaliação (1-5 estrelas) e Comentários.
-   **Exportação:** Funcionalidade para baixar relatório dos dados em
    **CSV** e **JSON**.
-   **Cache:** Otimização de performance na listagem da Home.
-   **Segurança:**
    -   Usuários comuns só podem apagar seus próprios
        comentários/avaliações.
    -   Administradores possuem permissão total de moderação.

------------------------------------------------------------------------

## 🛠️ Como Rodar o Projeto

### Pré-requisitos

-   **Docker** e **Docker Compose** instalados e rodando.
-   **Java 17** (Opcional, pois usamos o Maven Wrapper embutido, mas
    recomendado).

### Passo 1: Subir a Infraestrutura

Na raiz do projeto (onde está o arquivo `docker-compose.yml`), execute:

``` bash
docker-compose up -d
```

Isso iniciará automaticamente os containers do PostgreSQL (porta 5432),
MongoDB (porta 27017) e Redis (porta 6379) já configurados.

### Passo 2: Iniciar a Aplicação

Utilize o script do Maven Wrapper incluído no projeto para compilar e
rodar:

**No Windows (PowerShell/CMD):**

    .\mvnw.cmd spring-boot:run

**No Linux ou Mac:**

    ./mvnw spring-boot:run

### Passo 3: Acessar o Sistema

Abra o navegador e acesse:

-   **Aplicação Web:** http://localhost:8080\
-   **Documentação da API (Swagger):**
    http://localhost:8080/swagger-ui/index.html

------------------------------------------------------------------------

## 🔐 Acesso Administrativo

O sistema verifica automaticamente se existe um administrador ao
iniciar. Se não existir, ele cria o usuário padrão para testes (via
DataSeeder):

-   **Login:** admin
-   **Email:** admin@email.com
-   **Senha:** 123456

Utilize este usuário para testar funcionalidades exclusivas, como
exclusão de comentários de terceiros ou pontos turísticos.

------------------------------------------------------------------------

## 📂 Estrutura de Pastas Importantes

    src/main/java/.../controller       # Endpoints da API e Controladores de Tela
    src/main/java/.../entity           # Entidades JPA (SQL)
    src/main/java/.../mongo            # Documentos MongoDB (NoSQL)
    src/main/java/.../service          # Regras de negócio
    src/main/resources/templates        # Telas do Frontend (HTML)
    uploads/                           # Pasta gerada automaticamente para fotos

API RESTful desenvolvida para leitura e analise da lista de indicados e vencedores da categoria "Pior Filme" do Golden Raspberry Awards.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4.0.0
- Spring Data JPA
- H2 Database
- Maven
- Lombok
- JUnit 5 
- MockMvc (para testes de integração)

## Pré-requisitos

- Java JDK 21 instalado.
- Maven instalado.
- Porta 8080 disponivel.

## Como Executar o Projeto

1. Clone o repositório:

```bash
    git clone https://github.com/GabReiser/outsera-java-backend.git
```
2. Execute a aplicação usando Maven:

* **Linux/Mac/GitBash:**
  ```bash
  ./mvnw spring-boot:run

* **Windows:**
  ```cmd
  .\mvnw spring-boot:run
  ```
  
3. Aguarde a mensagem no console: `Informações carregadas na base com sucesso!`
4. A API estará disponível em `http://localhost:8080/api/movies/intervals`.

## Como executar os testes de integração

* **Linux/Mac:**
    ```bash
    ./mvnw test
    ```

* **Windows:**
    ```cmd
    .\mvnw test
    ```

Caso queira rodar apenas o teste de integração específico do Controller:
```bash
mvnw -Dtest=MovieControllerIntegrationTest test
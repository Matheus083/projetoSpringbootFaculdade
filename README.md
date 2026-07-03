# 📦 Clube de Assinaturas API

Uma API RESTful completa, segura e escalável desenvolvida com **Java 21** e **Spring Boot 3**, projetada para gerenciar um ecossistema de clube de assinaturas de caixas de produtos (Boxes).

---
## 🧑🏻‍🎓 Desenvolvedor:
- **ALUNO:** Matheus Nunes Dantas de Sena.
- **RGM:** 46315802

## 🚀 Tecnologias e Recursos Utilizados

* **Java 21** & **Spring Boot 3**
* **Gradle** (Gerenciador de Dependências)
* **MySQL** (Banco de Dados Relacional)
* **Spring Data JPA & Hibernate** (Mapeamento Objeto-Relacional)
* **Spring Security & JWT** (Autenticação Stateless e Controle de Acesso por Roles)
* **Jakarta Validation** (Validação de Dados de Entrada)
* **Lombok** (Produtividade e Eliminação de Boilerplate)
* **OpenAPI 3 / Swagger UI** (Documentação Interativa da API)

---

## 🏗️ Arquitetura do Projeto

O projeto segue as boas práticas da arquitetura em camadas do ecossistema Spring:

```text
src/main/java/br/com/projeto/
├── config/       # Configurações globais (Segurança, Swagger)
├── controller/   # Portas de entrada REST (Endpoints HTTP)
├── dto/          # Objetos de Tráfego de Dados (Request/Response)
├── entity/       # Modelos de Dados (Mapeados para as tabelas MySQL)
├── enums/        # Tipos Enumerados do domínio do negócio
├── exception/    # Manipulador global de erros e exceções customizadas
├── repository/   # Interfaces de comunicação com o banco de dados
├── security/     # Classes do ecossistema JWT e filtros de segurança
└── util/         # Classes utilitárias e conversores (Mappers manuais)

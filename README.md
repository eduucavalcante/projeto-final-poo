# CineManager

Sistema desktop de gerenciamento de cinema desenvolvido em **Java** como projeto final da disciplina de **Programação Orientada a Objetos** do curso de Engenharia de Software.

O sistema permite o gerenciamento de um cinema através de dois perfis de usuário: **Administrador** e **Cliente**, utilizando interface gráfica em Swing e persistência de dados com SQLite.

---

## Funcionalidades

### Cliente

* Visualizar filmes em cartaz;
* Consultar sessões disponíveis;
* Escolher assentos em uma grade visual;
* Comprar ingressos (Inteira ou Meia);
* Visualizar histórico de ingressos;
* Exportar ingressos para arquivo `.txt`.

### Administrador

* Gerenciar filmes;
* Colocar/retirar filmes em cartaz;
* Gerenciar salas;
* Gerenciar sessões;
* Visualizar dashboard com o total de vendas.

---

## Arquitetura

O projeto foi organizado em camadas (arquitetura MVC) para separar responsabilidades:

```text
src/
├── controllers/
├── models/
│   ├── daos/
│   └── entities/
├── views/
├── utils/
└── Main.java
```

A comunicação entre as camadas ocorre da seguinte forma:

```text
Views
   ↓
Controllers
   ↓
Models/DAOs
   ↓
SQLite
```

---

## Conceitos de Programação Orientada a Objetos

O projeto utiliza diversos conceitos estudados na disciplina, entre eles:

* Herança;
* Classes abstratas;
* Sobrescrita de métodos;
* Sobrecarga de construtores;
* Polimorfismo;
* Coleções (`List` e `ArrayList`, por exemplo);
* Tratamento de exceções;
* Agregação;
* Composição;
* Arquitetura em camadas;
* Injeção de dependência por construtor;
* Manipulação de arquivos.

---

## Persistência

Os dados são armazenados utilizando **SQLite** através da API **JDBC**.

As senhas dos usuários são protegidas utilizando **BCrypt**, evitando armazenamento em texto puro.
> Obs.: Login simplificado, pois autenticação e autorização não eram o foco principal do projeto.

---

## Tecnologias utilizadas

* Java;
* Java Swing;
* SQLite;
* JDBC;
* BCrypt;
* NetBeans GUI Builder.

---

## Como executar

1. Clone este repositório;
2. Abra o projeto no NetBeans ou na IDE de sua preferência;
3. Execute a classe `Main.java`.

O banco de dados será criado automaticamente na primeira execução, caso não exista.


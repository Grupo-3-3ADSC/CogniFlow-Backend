# API de Estoque - Megaplate

Bem-vindo ao repositório da API de Estoque da Megaplate, desenvolvida pela Cogniflow. Esta API fornece funcionalidades para gerenciamento de estoque, relatórios, etc.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 (banco de dados)

## Instalação

### Requisitos
- Java 17+
- Maven

### Configuração do Projeto

1. Clone o repositório:
   ```sh
   git clone https://github.com/Grupo-3-3ADSC/CogniFlow-Backend.git
   ```

## Endpoints Principais

### Usuário
- **GET** `/usuarios` - Lista todos os usuários
- **POST** `/usuarios` - Adiciona um novo usuário com cargo comum
- **POST** `/usuarios/gestor` - Adiciona um novo usuário com cargo de gestor
- **PUT** `/usuarios/{id}` - Atualiza um usuário
- **DELETE** `/usuarios/{id}` - Remove um usuário

### GESTOR
- **GET** `/cargo` - Lista todos os cargos
- **POST** `/cargo` - Adiciona um novo cargo
- **PUT** `/cargo/{id}` - Atualiza um cargo
- **DELETE** `/cargo` - Remove um cargo

## Licença
Este projeto está sob a licença MIT.

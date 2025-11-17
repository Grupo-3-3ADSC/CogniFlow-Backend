# API de Estoque - Megaplate

Bem-vindo ao repositório da API REST da Megaplate, desenvolvida pela Cogniflow. Esta API fornece funcionalidades para 

- Gerenciamento de estoque
- Cadastro e login de usuários
- Gerar relatórios
- Entre muitas outras funcionalidades

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL (banco de dados)
- Redis
- RabbitMQ
- JWT

## Instalação

### Requisitos
- Java 21+
- Maven
- Docker

### Configuração do Projeto

1. Clone o repositório:
   ```sh
   git clone https://github.com/Grupo-3-3ADSC/CogniFlow-Backend.git
   ```
   
2. Inicialize os containers necessários do docker (Redis e RabbitMQ): 
   ```sh
   sudo docker run -d --name redis-local -p 6379:6379 redis
   ```
   
   ```sh
   sudo docker run -d --hostname rabbit --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
   ```

3. Utilize um compilador ou uma IDE como intellij ou eclipse para compilar o projeto

## Endpoints Principais

### Usuário
- **GET** `/usuarios` - Lista todos os usuários
- **POST** `/usuarios` - Adiciona um novo usuário com cargo comum
- **POST** `/usuarios/gestor` - Adiciona um novo usuário com cargo de gestor
- **PUT** `/usuarios/{id}` - Atualiza um usuário
- **DELETE** `/usuarios/{id}` - Remove um usuário

### Cargo
- **GET** `/cargo` - Lista todos os cargos
- **PUT** `/cargo/{id}` - Atualiza um cargo
- **DELETE** `/cargo` - Remove um cargo

## Licença
Este projeto está sob a licença MIT.

# Semana Dev Superior 2.0 - DSDelivery

Este é o projeto backend do sistema para realização de pedidos e entregas de encomendas de um restaurante. 

Tecnologia utilizada:

- Java 20

- Spring Boot 3

# Pré-requisitos do projeto

- docker;
- docker compose;

# Modelo conceitual

![Image](https://raw.githubusercontent.com/Lubrum/dsdeliver-sds2/master/assets/modelo-conceitual.png "Modelo conceitual")


# Arquitetura

![Image](https://raw.githubusercontent.com/Lubrum/dsdeliver-sds2/master/assets/camadas.png "Padrão camadas")

# Execução do projeto

Na pasta raíz do projeto:

```bash
docker compose up -d
```

O comando acima executa o projeto java em si e o banco de dados Postgres inicializado com dados fictícios. 

Para parar a execução

```bash
docker compose down
```

# Deploy

Este projeto está com um mecanismo de CI/CD configurado para realizar o deploy em uma instância EC2 da Amazon a cada commit feito.

A instância precisa ter instalado nela o 'docker' e o 'docker compose'.

Para modificar a lógica de CI/CD, editar o arquivo em .github/workflows/deploy.yml

Existem alguns secrets utilizados que devem ser configurados no repositório:

- HOST: IP ou domínio da instância EC2;
- KEY: chave privada de acesso ssh à instância EC2;
- PORT: socket de acesso à instância EC2;
- PWD_DOCKER_HUB: senha ou token da conta no Docker Hub;
- USERNAME: usuário da instância EC2;

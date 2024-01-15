# Semana Dev Superior 2.0 - DSDelivery

Este é o projeto backend do sistema para realização de pedidos e entregas de encomendas de um restaurante desenvolvido na semana Dev Superior 2.0, ministrado pelo prof. Nélio Lopes. 

Este projeto abarca melhorias adicionais feitas como inclusão do Docker, atualização para o Spring Boot 3, Java 21 e outros. 

Tecnologia utilizada:

- Java 21

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
docker compose -f docker-compose-dev.yml up -d --build
```

## Para parar a execução

```bash
docker compose down
```

# Deploy

Este projeto está com um mecanismo de CI/CD configurado para realizar o deploy em uma instância EC2 da Amazon a cada commit feito.

A instância precisa ter instalado nela o 'docker' e o 'docker compose'.

Ainda, na pasta /home/ubuntu é requerido a existência do diretório **dsdeliver**, e dentro dela o diretório nginx/conf.d. 

Dentro desse diretório do nginx é necessário ter um arquivo semelhante ao **default.conf** deste projeto, porém com a URL adaptada para o ambiente de produção. No caso do EC2 é algo como http://ec2-IP-DA-INSTANCIA.REGIAO-DA-INSTANCIA.compute.amazonaws.com/.

Para modificar a lógica de CI/CD, editar o arquivo em .github/workflows/deploy.yml

Existem alguns secrets utilizados que devem ser configurados no repositório:

- HOST: IP ou domínio da instância EC2;
- KEY: chave privada de acesso ssh à instância EC2;
- PORT: socket de acesso à instância EC2;
- PWD_DOCKER_HUB: senha ou token da conta no Docker Hub;
- USERNAME: usuário da instância EC2;

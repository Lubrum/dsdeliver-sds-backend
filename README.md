# Semana Dev Superior 2.0 - DSDelivery

Este é o projeto backend do sistema para realização de pedidos e entregas de encomendas de um restaurante. 

Tecnologia utilizada:

- Java 17

- Spring Boot

# Pré-requisitos do projeto

- Java JDK 17;
- Maven (Gerenciamento de dependências);
- STS (IDE para desenvolvimento);
- Postman (Teste de APIs);
- Postgresql e pgAdmin (Banco de dados SQL e UI para Postgres);
- Heroku CLI;


# Modelo conceitual

![Image](https://raw.githubusercontent.com/Lubrum/dsdeliver-sds2/master/assets/modelo-conceitual.png "Modelo conceitual")


# Arquitetura

![Image](https://raw.githubusercontent.com/Lubrum/dsdeliver-sds2/master/assets/camadas.png "Padrão camadas")

# Comandos para o Deploy no Heroku

[Tutorial completo de deploy com Heroku.](https://devcenter.heroku.com/articles/git)


## Pre-requisitos: 

- Git;
- Heroku CLI;


## Rastrear (track) o app com o Git.

```bash
cd myapp
git init
git add .
git commit -m "My first commit"
```

## Criar um 'Heroku remote'.

- Para um novo app no Heroku:

```bash
heroku create
git remote -v
```

- Para um app existente no Heroku:

```bash
heroku git:remote -a [heroku_app_name]
```

## Para realizar o deploy no Heroku:

```bash
git push heroku main
```

Link da API: https://luciano-sds2.herokuapp.com/

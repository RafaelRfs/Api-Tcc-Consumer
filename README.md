# Módulo de Gestão Estratégica de Projetos do Sistema de Gestão Integrada Municipal (SGM)
# Api Consumidora de Emails
O projeto é destinado a uma POC do trabalho de conclusão de curso PUC Minas

# Requisitos:
- Java 11;
- Maven;
- Docker;
- Banco de dados Postgresql;
- RabbitMQ;


#Variaveis de ambiente necessárias:
AMQP_URL=[substituir pela url criada do topico do RabbitMQ, crie sua conta gratuita por este site: https://www.cloudamqp.com/]
DATABASE_URL=[ substituir pela url do banco de dados no seguinte formato: 
       postgres://[usuario]:[senha]@[servidor]:[porta]/[banco de dados]

## Buildando o projeto
Para buildar o projeto faça:
- Crie o jar da aplicação na pasta raiz pelo comando:
### `mvn clean install `

- Crie a imagem Docker na pasta raiz do projeto:
### `docker build -t api-consumidora-tcc:1.0.0 . `


## Rodando o projeto
- Acesse a pasta pasta target e rode o jar pelo seguinte comando:
### `docker run -e AMQP_URL=[substituir pelo valor da variavel AMQP] -e DATABASE_URL=[substituir pelo valor da variavel DATABASE_ULR] -e KEYSTORE_ALIAS=api_tcc_producer -e KEYSTORE_PASS=[pedir a senha por email]  api-consumer-tcc:1.0.0 `


## Projeto criado por :
- Rafael Ferreira
- Erik Cavalcanti
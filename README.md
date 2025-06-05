# ZENA (JAVA)
- Título: Estação Climática Inteligente com Alerta de Eventos Extremos
- Objetivo: Criar um sistema que recebe dados de uma estação climática, analisa esses dados para detectar condições de risco (calor extremo, umidade muito baixa, pressão indicando tempestade) e fornece uma interface para monitoramento e notificação, além de detectar os abrigos mais próximos baseados na localização do usuário.
```
Json para criar Usuarios:
{
  "id": "1",
  "username": "admin",
  "password": "12345",
  "email": "cmilfont021@gmail.com",
  "role": "ADMIN"
}

Json para criar Estacoes:
{
  "id": "23",
  "nome": "Estação Luz",
  "localizacao": "Centro de São Paulo",
  "ativo": true, ----> eu coloco sempre 1 ou 0 pois banco oracle é muito rigido!
  "usuario": {
    "id": "1"
  }
}

Json para criar Leituras Climaticas:
{
  "estacaoId": "23",
  "temperatura": 36.5,
  "umidade": 45.0,
  "pressao": 1013.0,
  "velocidadeVento": 5.2,
  "direcaoVento": "Noroeste",
  "precipitacao": 2.0,
  "condicoesClimaticas": "Parcialmente nublado"
}

Json para criar alertas:
{
  "mensagem": "Calor extremo",
  "tipo": "temperatura acima de 38 graus",
  "leituraId": "4b1ec253-021b-4f9e-9637-5e59f1c03ddc",
  "usuarioId": "1"
}

Usei UUID para gerar automaticamente alguns ID's
```
### Arquitetura:
- Backend: Spring Boot + JPA + REST + PostgreSQL (Banco rodando com Docker Compose)
- Frontend: React
- IoT: Dispositivo que envia dados via HTTP/MQTT para uma API REST 
- API: /leituras, /alertas, /usuarios, /abrigos
- Documentação: Swagger + README.md completo
- Testes: Unitários (JUnit), cobertura mínima de 60%

#### MODELAGEM DE DOMÍNIO (Java + JPA)

- Entidades principais:
- Estacao (id, nome, localizacao, ativo)
- LeituraClimatica (id, estacao_id, temperatura, umidade, pressao, dataHora)
- Alerta (id, tipo, mensagem, leitura_id, dataHora)
- Usuario (id, nome, email, senha, notificacoesAtivas)
- Abrigo (id, nome, localizacao)

#### REGRAS DE NEGÓCIO

##### A cada nova LeituraClimatica, um serviço verifica se há condições extremas:
- Temperatura > 38 °C → Gera alerta de calor
- Umidade < 20% → Gera alerta de baixa umidade
- Pressão < 1000 hPa → Gera alerta de tempestade
- O alerta é persistido e pode ser enviado via e-mail e exibir no dashboard

  
#####  Segurança por perfil:
- Enum Role { ADMIN, USER }
##### Restrições:
Apenas usuários logados podem acessar dados, exceto login e cadastro

### CRUD Completo
CRUD completo para as entidades

Apenas usuários logados podem acessar dados, exceto login e cadastro


Segurança: apenas usuários logados podem acessar os dados, exceto login/register
## Tecnologias:

### - BACKEND – Spring Boot

Camadas:
- controller: recebe requisições REST
- service: regras de negócio, geração de alertas e autenticações
- repository: acesso ao banco com Spring Data JPA
- model: classes de domínio com anotações JPA
- DTOs para entrada/saída de dados
- security: para minhas configurações de Token e liberação de requisições para determinadas Roles
- config: para configurar o CORS dos meus endpoints e ajustar configurações do meu Swagger para personalizar ainda mais

### - FRONTEND 
Utilizei minha API java para lidar com as requisições do App Mobile, evidente na lógica de login no controller de User

#### Caching:
- O cache “users” vai guardar a página e critérios da página

### Uitlizando Paginação, filtro e ordenação nos Controllers:
- Exemplo no controller de Estação:
Ajustes no EstacaoRepository, extendendo JpaSpecificationExecutor<Estacao> (o método findAll(...) que você está usando espera um Specification<Estacao> e não um lambda)
```
GET /estacoes/paginado?page=0&size=5&sort=nome,asc&nome=central&ativo=true
```
![image](https://github.com/user-attachments/assets/4dae5bcc-89e6-43d1-b10d-71b95a0d4483)

- Swagger reconhece os parâmetros automaticamente, exibindo os parâmetros page, size, sort, nome, localizacao, ativo com base nas anotações @RequestParam

#### Paginação e filtros:
```
- Como buscar no Postman
  retorna a primeira página com 5 usuários ordenados por username ascendente.
  GET /users?page=0&size=5&sort=username,asc
```
#### Autenticação com JWT

- Adicionar dependências do Spring Security + JWT (auth0)
- Criar as classes:
- UserDetailsService personalizado
- Filtro de autenticação JWT
- AuthController com endpoints /login e /register
- Usuário se cadastra via /register e faz login via /login para receber um token
- O token é enviado em Authorization: Bearer xxx nas demais requisições

## Testando o JWT:
### Para logar no Swagger:
```
{
  "username": "usuarioTeste",
  "password": "minhaSenha123"
}
```
### Autenticação Basic do Spring Security:
```
admin
senha: 1234
```
```
- cadastro de usuário (endpoint /estacoes - POST)
- URL: http://localhost:8080/estacoes
```
- Método: POST
Exemplo:
![image](https://github.com/user-attachments/assets/7f82e6b8-365f-4bf5-83ed-ee1a7b2c682c)
```
- Body (JSON):
{
  "id": "1",
  "username": "usuarioTeste",
  "password": "minhaSenha123",
  "email": "teste@example.com",
  "role": "USER"
}
```
![image](https://github.com/user-attachments/assets/8db1c42a-e98d-496c-8d16-4623773381d4)

### Agora a senha criptografada, gerando token...:
```
Testar login para gerar token JWT (endpoint /users/login - POST)
URL: http://localhost:8080/users/login

Método: POST

Body (JSON):
{
  "username": "usuarioTeste",
  "password": "minhaSenha123"
}
```
![image](https://github.com/user-attachments/assets/ac24c967-f403-41f3-88c0-248088688ff4)

-----------------
## Por ultimo, o deploy em nuvem da aplicação (Render) :
- problema: A Render (e outras plataformas como Heroku e Railway) não oferecem suporte nativo ao Oracle Database.

- Solução que apliquei, manter o banco Oracle da FIAP remoto e fazer deploy apenas do backend na nuvem
![image](https://github.com/user-attachments/assets/6af3fdbe-ec68-45bc-a3b5-485ce04dd300)

- Criando DockerFile para testar:
![image](https://github.com/user-attachments/assets/8d9b7d66-e4e1-4dcc-b3fc-1ecd56d66bef)
(aqui atualizei após o build para o nome exato do meu .jar (COPY target/ZENA-0.0.1-SNAPSHOT.jar app.jar)

- Antes de rodar localmente utilizando os comandos do docker file, gerei meu .jar da aplicação:
```
mvn clean package
```
![image](https://github.com/user-attachments/assets/e1a0377c-0118-488e-80c2-78128166398b)

- Após isso rodei
```
docker build -t zena-app .
```
![image](https://github.com/user-attachments/assets/dcb8a4bf-c1d1-4a91-b399-bd2dae498206)

Próximo passo foi: rodar o container localmente para testar
```
docker run -p 8080:8080 zena-app
```
![image](https://github.com/user-attachments/assets/91b36b2c-7e24-459b-b2c1-46fcf5ce62b5)

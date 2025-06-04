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
  "ativo": true,
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

### CRUD Completo

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
- config: para configurar o CORS dos meus endpoints

### - FRONTEND 
- Painel com gráfico de temperatura, umidade, pressão
- Lista de alertas mais recentes
- Tela para cadastrar estação

#### Autenticação com JWT

- Adicionar dependências do Spring Security + JWT (jjwt ou auth0)
- Criar as classes:
- UserDetailsService personalizado
- Filtro de autenticação JWT
- AuthController com endpoints /login e /register
- Utilitário JwtUtil
- Usuário se cadastra via /register e faz login via /login para receber um token
- O token é enviado em Authorization: Bearer xxx nas demais requisições

## Testando o JWT:
```
- cadastro de usuário (endpoint /users - POST)
- URL: http://localhost:8080/users (ajuste a porta se for diferente)

- Método: POST

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

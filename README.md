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
- ADMIN pode tudo
- USER pode: ver dados, criar leitura, ver alertas
- Ações de criar/editar/deletar estação, deletar alertas ou usuários são só para ADMIN
  

### CRUD Completo
- EstacaoController: GET /estacoes, GET /estacoes/{id}, POST, PUT, DELETE
- LeituraClimaticaController: GET /leituras, filtros por data, estacao
- AlertaController: GET /alertas, filtros por tipo e data
- UsuarioController: GET /usuarios, PUT /usuarios/{id}, DELETE /usuarios/{id} (admin only),  POST /usuarios/criar
- AbrigoController: GET /abrigos, PUT /abrigos/{id}, DELETE /abrigos/{id} (admin only), POST /abrigo/criar (admin only)
- Uso de DTOs e validações com @Valid

Segurança: apenas usuários logados podem acessar os dados, exceto login/register
## Tecnologias:

### - BACKEND – Spring Boot

Camadas:
- controller: recebe requisições REST
- service: regras de negócio e geração de alertas
- repository: acesso ao banco com Spring Data JPA
- model: classes de domínio com anotações JPA
- DTOs para entrada/saída de dados


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

![image](https://github.com/user-attachments/assets/2537a945-ee36-4fd3-beed-fdd06fa775c2)

------------------------------------------------------------------------
### Fluxo Aplicação (Criação do DashBoard com alertas e historico de alertas)
![image](https://github.com/user-attachments/assets/e0972c79-c899-4985-8759-e7c0ad0feca5)



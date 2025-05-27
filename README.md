# ZENA
- Nosso projeto para Global Solutions

### EndPoints
- POST /leituras → recebe leitura e gera alertas se necessário
- GET /alertas → retorna todos os alertas gerados
- GET /leituras?estacao=1&desde=ontem → retorna leituras filtradas
- POST /usuarios → cria novo usuário (CRUD simples)
- POST /login (autenticação simples, se quiser usar Spring Security)

### CRUD Completo
- EstacaoController: GET /estacoes, GET /estacoes/{id}, POST, PUT, DELETE
- LeituraClimaticaController: GET /leituras, filtros por data, estacao
- AlertaController: GET /alertas, filtros por tipo e data
- UsuarioController: GET /usuarios, PUT /usuarios/{id}, DELETE /usuarios/{id} (admin only)
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
  
![image](https://github.com/user-attachments/assets/60ca8476-05e2-481e-b22a-ed5bf00e85d1)

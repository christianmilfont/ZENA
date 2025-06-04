# ZENA
Nosso projeto para Global Solutions da matéria de .NET, para localizar abrigos próximos ao Usuário

## Tecnologias:
Oracle EntityFramework
Microsoft EntityFramework Design
Microsoft AspNetCore

## Estrutura do projeto:
- domains: para definir meus modelos (Usuário e Abrigo)
- controller: definir os endpoints da minha API 
- context: para mapear meus domains e também garantir que sejam formatados corretamente no banco de dados Oracle (muito rígido)
- Mogrations: para armazenar o schema da minha entidade do banco de dados
- Program.cs: registar minha dbcontext
- AppSettings.json: definir minha conexão com o banco

  
Corpo de requisição JSON:
```
[
  {
    "id": "string",
    "nome": "string",
    "endereco": "string",
    "capacidade": 0,
    "ocupacaoAtual": 0,
    "ativo": true,
    "usuarioId": "string",
    "usuario": {
      "id": "string",
      "username": "string",
      "password": "string",
      "email": "string",
      "role": "string",
      "abrigos": [
        "string"
      ]
    },
    "latitude": 0,
    "longitude": 0
  }
]
```
## Usando o migrations:

### Utilizando o comando:
```
dotnet ef migrations add CreateAbrigosTable
dotnet ef update database
```
- Criei a migrations apenas de Abrigos pois usuario ja esta linkada a outras tabelas, fazendo parte da minha API Java!
- 
```
Para isso adicionei no DbContext essa linha:
 // Aqui, diga que o EF não deve tentar criar essa tabela:
    modelBuilder.Entity<Usuario>().Metadata.SetIsTableExcludedFromMigrations(true);
que induz para nçao tentar criar uma tabela usuarios
```
- Isso vai aplicar a migration e criar a tabela ABRIGOS no banco, junto com a chave estrangeira para a tabela USUARIOS.
- Defeni também o tipo VARCHAR2(255), da minha tabela do banco de dados que ja existia

![image](https://github.com/user-attachments/assets/28be6938-6071-43f9-89ab-9304a6e93b5e)
![image](https://github.com/user-attachments/assets/a5982b0f-a1a4-4f04-8b11-57c28dc852db)

## Testando Swagger para ver se funcionou!:
![image](https://github.com/user-attachments/assets/c357e3e5-4fd2-4f90-9a65-97d73bcba157)
![image](https://github.com/user-attachments/assets/a60c6bd4-185f-4261-a931-b90f5fb35058)


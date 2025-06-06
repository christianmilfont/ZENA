# ZENA
### Nosso projeto para Global Solutions para matéria de mobile seria um App que possui um Dashboard com os ultimos alertas, alem disso uma tela para mostrar os abrigos mais proximos e indicar o caminho a ser feito pelo 
usuario

Equipe:
Christian Milfont rm555345
Iago Victor rm558450
Anderson Pedro rm557002

- [ ] (10 pts) Criar no mínimo 5 telas e utilizar navegação entre elas (React Navigation ou Expo Router): 
- [ ] (40 pts) Implementar um CRUD utilizando a API criada na disciplina JAVA ou .NET (Axios ou Fetch).
- [ ] (10 pts) Estilização do aplicativo, incluindo cores, fontes, e imagens personalizadas.
- [ ] (20 pts) Arquitetura: será avaliada a organização dos arquivos, nomes das variáveis, funções e componentes utilizados.
- [ ] (20 pts) Elabore uma gravação de um vídeo de todas as funcionalidades do Ap

------------------------------------

## Criar no mínimo 5 telas e utilizar navegação entre elas (React Navigation ou Expo Router):
- O aplicativo deve conter, no mínimo, 5 telas distintas com navegação fluida entre elas.
- A navegação deve seguir boas práticas de usabilidade e acessibilidade.

## CRUD com API (Java/.NET) usando Axios ou Fetch 40 pontos
 
- Implementar operações de Create, Read, Update, Delete usando uma API RESTful desenvolvida nas disciplinas de backend (Java ou .NET).
- Utilização de bibliotecas adequadas (Axios ou Fetch) com tratamento de erros e feedback visual

## Estilização com identidade visual personalizada 10 pontos
 
- Personalização de cores, fontes e imagens de acordo com o tema do app.
- Uso consistente do design, respeitando padrões de usabilidade.
- Aplicação das guidelines da Apple/Google. (usei o safe area context mesmo)
- Criatividade e identidade visual clara e funcional

![image](https://github.com/user-attachments/assets/eed92702-db0e-45a9-8115-bca1658d243d)
------
![image](https://github.com/user-attachments/assets/00a75da5-7836-427a-9aca-bb5304740f2a)
------
![image](https://github.com/user-attachments/assets/ce257bbf-866d-4248-9e6c-8c35fed09dbd)

## Tela de editar perfil:
![Imagem do WhatsApp de 2025-06-06 à(s) 10 44 49_bf4ca267](https://github.com/user-attachments/assets/5a760772-07c1-44fc-82dd-2e10963d8aee)


## Tela de Rotas: Lógica para encontrar o abrigo mais próximo (fórmula de Haversine)
- A tela RotasScreen.js é responsável por localizar o abrigo mais próximo do usuário com base na sua geolocalização:

-  Obter a localização do usuário
Utiliza a biblioteca expo-location para solicitar permissão de acesso à localização.

Caso o usuário permita, obtém suas coordenadas atuais (latitude e longitude).

```
const location = await Location.getCurrentPositionAsync({});
const { latitude, longitude } = location.coords;
```
- Buscar todos os abrigos disponíveis
Realiza uma requisição GET para a API (/api/Abrigoes) usando axios.
![image](https://github.com/user-attachments/assets/df4f5281-2e2d-4a8c-9980-c05e7fb5b8ee)

Espera-se que cada abrigo possua as propriedades latitude e longitude.
```
const response = await axios.get('https://localhost:7095/api/Abrigoes');
const abrigos = response.data;
```
-  Calcular a distância de cada abrigo
Implementa a fórmula de Haversine, que calcula a distância entre dois pontos na superfície da Terra com base nas coordenadas geográficas.

Percorre todos os abrigos e calcula a distância de cada um em relação à posição atual do usuário.
```
function calcularDistancia(lat1, lon1, lat2, lon2) {
  ...
  return R * c; // Retorna a distância em quilômetros
}
```
- Filtrar o abrigo mais próximo
Compara as distâncias e armazena o abrigo mais próximo dentro de um raio máximo de 30 km.

Se não houver nenhum abrigo dentro desse raio, exibe uma mensagem informando isso.
```
const RAIO_KM = 30;

if (distancia < menorDistancia && distancia <= RAIO_KM) {
  menorDistancia = distancia;
  abrigoProximo = abrigo;
}
```
- Exibir os dados do abrigo encontrado
Exibe o nome, endereço e nome do responsável do abrigo mais próximo.

Caso não encontre nenhum abrigo dentro do raio, exibe uma mensagem de aviso.

Oferece um botão para retornar à tela inicial.- Tecnologia usada: npm install axios @react-native-async-storage/async-storage expo-location
![image](https://github.com/user-attachments/assets/fbd5828f-f264-448a-8561-a9b4b17630ac)

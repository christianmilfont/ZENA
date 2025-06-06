import axios from 'axios';
// CLASSE AXIOS PARA API JAVA 
const api = axios.create({
  baseURL: 'http://192.168.0.10:8080', //http://192.168.0.10:8080 para aplicacao em IOS/ANDROID e //http://localhost:8080 para aplicacao rodando na versao WEB
  auth: {
    username: 'teste@example.com',
    password: 'minhaSenha123',
  }
});

export default api;

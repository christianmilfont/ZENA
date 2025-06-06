import React, { useState, useEffect } from 'react';
import { 
  View, Text, TextInput, TouchableOpacity, StyleSheet, Alert, ActivityIndicator, KeyboardAvoidingView, Platform 
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import api from '../api/axios-api';

export default function LoginScreen({ navigation }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const verificarLogin = async () => {
      const usuario = await AsyncStorage.getItem('usuarioLogado');
      if (usuario) navigation.replace('Perfil');
    };
    verificarLogin();
  }, []);

  const handleLogin = async () => {
    if (!username.trim() || !password.trim()) {
      Alert.alert('Atenção', 'Por favor, preencha usuário e senha.');
      return;
    }

    setLoading(true);
    try {
      const response = await api.post('/users/login', { username, password });
      const usuario = response.data;
      await AsyncStorage.setItem('usuarioLogado', JSON.stringify(usuario));
      navigation.replace('Perfil');
    } catch (error) {
      Alert.alert('Erro', 'Usuário ou senha inválidos.');
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <Text style={styles.title}>ZENA - Login</Text>

      <TextInput
        style={styles.input}
        placeholder="Usuário"
        value={username}
        onChangeText={setUsername}
        autoCapitalize="none"
        autoCorrect={false}
        editable={!loading}
      />

      <TextInput
        style={styles.input}
        placeholder="Senha"
        secureTextEntry
        value={password}
        onChangeText={setPassword}
        editable={!loading}
      />

      <TouchableOpacity
        style={[styles.button, loading && styles.buttonDisabled]}
        onPress={handleLogin}
        disabled={loading}
        activeOpacity={0.8}
      >
        {loading ? (
          <ActivityIndicator size="small" color="#FFF" />
        ) : (
          <Text style={styles.buttonText}>Entrar</Text>
        )}
      </TouchableOpacity>

      <TouchableOpacity
        style={[styles.button, styles.backButton]}
        onPress={() => navigation.navigate('Home')}
        activeOpacity={0.8}
        disabled={loading}
      >
        <Text style={[styles.buttonText, styles.backButtonText]}>← Voltar ao Início</Text>
      </TouchableOpacity>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    justifyContent: 'center', 
    padding: 24, 
    backgroundColor: '#F3F4F6' 
  },
  title: { 
    fontSize: 28, 
    fontWeight: 'bold', 
    marginBottom: 32, 
    textAlign: 'center', 
    color: '#111827' 
  },
  input: {
    backgroundColor: '#FFF',
    padding: 14,
    borderRadius: 8,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: '#D1D5DB',
    fontSize: 16,
  },
  button: {
    backgroundColor: '#2563EB',
    paddingVertical: 14,
    borderRadius: 10,
    alignItems: 'center',
    marginBottom: 16,
  },
  buttonDisabled: {
    backgroundColor: '#94A3B8',
  },
  buttonText: { 
    color: '#FFF', 
    fontWeight: '600', 
    fontSize: 18 
  },
  backButton: {
    backgroundColor: '#E0E7FF',
    marginBottom: 0,
  },
  backButtonText: {
    color: '#2563EB',
    fontWeight: '600',
  }
});

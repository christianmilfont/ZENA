import React, { useState, useEffect } from 'react';
import { 
  View, Text, TextInput, TouchableOpacity, StyleSheet, Alert, ActivityIndicator, KeyboardAvoidingView, Platform 
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import api from '../api/axios-api';

export default function EditarPerfilScreen({ navigation }) {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const carregarUsuario = async () => {
      const usuarioData = await AsyncStorage.getItem('usuarioLogado');
      if (usuarioData) {
        const usuario = JSON.parse(usuarioData);
        setUsername(usuario.username);
        setEmail(usuario.email);
        // Não preenche password por segurança, usuário precisa digitar para alterar
      }
    };
    carregarUsuario();
  }, []);

  const handleAtualizar = async () => {
    if (!username.trim() || !email.trim()) {
      Alert.alert('Atenção', 'Usuário e email são obrigatórios.');
      return;
    }

    setLoading(true);
    try {
      const usuarioAtualizado = {
        username,
        email,
        password: password.trim() ? password : undefined // só envia se digitou senha nova
      };

      // Chamar PUT na API, usando email como chave na URL
      await api.put(`/users/email/${email}`, usuarioAtualizado);

      // Atualiza o AsyncStorage com os dados atualizados
      await AsyncStorage.setItem('usuarioLogado', JSON.stringify(usuarioAtualizado));

      Alert.alert('Sucesso', 'Perfil atualizado com sucesso!');
      navigation.goBack();
    } catch (error) {
      console.log(error);
      Alert.alert('Erro', 'Não foi possível atualizar o perfil.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <Text style={styles.title}>Editar Perfil</Text>

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
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        autoCapitalize="none"
        autoCorrect={false}
        editable={!loading}
      />

      <TextInput
        style={styles.input}
        placeholder="Senha (deixe vazio para não alterar)"
        secureTextEntry
        value={password}
        onChangeText={setPassword}
        editable={!loading}
      />

      <TouchableOpacity
        style={[styles.button, loading && styles.buttonDisabled]}
        onPress={handleAtualizar}
        disabled={loading}
        activeOpacity={0.8}
      >
        {loading ? (
          <ActivityIndicator size="small" color="#FFF" />
        ) : (
          <Text style={styles.buttonText}>Salvar</Text>
        )}
      </TouchableOpacity>

      <TouchableOpacity
        style={[styles.button, styles.backButton]}
        onPress={() => navigation.goBack()}
        activeOpacity={0.8}
        disabled={loading}
      >
        <Text style={[styles.buttonText, styles.backButtonText]}>← Voltar</Text>
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

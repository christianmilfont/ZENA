import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function PerfilScreen({ navigation }) {
  const [usuario, setUsuario] = useState(null);

  useEffect(() => {
    const carregarUsuario = async () => {
      const data = await AsyncStorage.getItem('usuarioLogado');
      if (data) setUsuario(JSON.parse(data));
    };
    carregarUsuario();
  }, []);

  const logout = async () => {
    await AsyncStorage.removeItem('usuarioLogado');
    navigation.replace('Login');
  };

  if (!usuario) {
    return (
      <View style={styles.container}>
        <Text>Carregando...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>👤 Perfil do Usuário</Text>
      <Text style={styles.info}>Nome de Usuário: {usuario.username}</Text>
      <Text style={styles.info}>Função: {usuario.role}</Text>

      <TouchableOpacity style={[styles.button, styles.logout]} onPress={logout}>
        <Text style={styles.buttonText}>🚪 Sair</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Home')}>
        <Text style={styles.buttonText}>← Voltar ao Início</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={[styles.button, styles.edit]}
        onPress={() => navigation.navigate('EditarPerfil', { usuario })}
      >
        <Text style={styles.buttonText}>✏️ Editar Perfil</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    justifyContent: 'center',
    backgroundColor: '#F9FAFB',
  },
  title: {
    fontSize: 22,
    fontWeight: '700',
    marginBottom: 20,
    textAlign: 'center',
    color: '#111827',
  },
  info: {
    fontSize: 16,
    marginBottom: 10,
    color: '#374151',
  },
  button: {
    marginTop: 15,
    paddingVertical: 14,
    paddingHorizontal: 24,
    borderRadius: 12,
    backgroundColor: '#2563EB',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowOffset: { width: 0, height: 2 },
    shadowRadius: 4,
    elevation: 3,
  },
  logout: {
    backgroundColor: '#DC2626',
  },
  edit: {
    backgroundColor: '#3B82F6',
  },
  buttonText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: '600',
  },
});

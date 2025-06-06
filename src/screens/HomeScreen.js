import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Image, ScrollView } from 'react-native';

export default function HomeScreen({ navigation }) {
  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>ZENA</Text>
      <Text style={styles.tagline}>Estação Climática Inteligente</Text>

      <Image
        source={{ uri: 'https://cdn-icons-png.flaticon.com/512/1116/1116453.png' }} // exemplo de ícone meteorológico que usei para esse projeto (usei a biblioteca de icons)
        style={styles.logo}
        resizeMode="contain"
      />

      <Text style={styles.description}>
        Bem-vindo ao ZENA!{'\n\n'}
        Nosso sistema monitora condições climáticas extremas como calor intenso, baixa umidade e tempestades, 
        alertando você em tempo real para garantir sua segurança.{'\n\n'}
        Também indicamos abrigos próximos com base na sua localização, facilitando o acesso rápido a locais seguros.
      </Text>

      <View style={styles.menu}>
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Login')}>
          <Text style={styles.buttonText}>👤 Login</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('DashBoard')}>
          <Text style={styles.buttonText}>📋 Ver Alertas</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Rotas')}>
          <Text style={styles.buttonText}>🗺️ Ver Rota para Abrigo</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Perfil')}>
          <Text style={styles.buttonText}>👤 Meu Perfil</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    padding: 24,
    backgroundColor: '#E8F0FE',
  },
  title: {
    fontSize: 48,
    fontWeight: '900',
    textAlign: 'center',
    color: '#1E40AF',
    marginBottom: 4,
  },
  tagline: {
    fontSize: 20,
    fontWeight: '600',
    textAlign: 'center',
    color: '#3B82F6',
    marginBottom: 24,
    fontStyle: 'italic',
  },
  logo: {
    width: 120,
    height: 120,
    alignSelf: 'center',
    marginBottom: 24,
  },
  description: {
    fontSize: 16,
    color: '#374151',
    textAlign: 'center',
    marginBottom: 32,
    lineHeight: 22,
  },
  menu: {
    gap: 16,
  },
  button: {
    backgroundColor: '#2563EB',
    paddingVertical: 16,
    borderRadius: 14,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOpacity: 0.15,
    shadowOffset: { width: 0, height: 3 },
    shadowRadius: 6,
    elevation: 6,
  },
  buttonText: {
    color: '#FFF',
    fontSize: 18,
    fontWeight: '700',
  },
});

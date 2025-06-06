import React, { useEffect, useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Alert, ActivityIndicator } from 'react-native';
import * as Location from 'expo-location';
import axios from 'axios';

export default function RotasScreen({ navigation }) {
  const [abrigoMaisProximo, setAbrigoMaisProximo] = useState(null);
  const [loading, setLoading] = useState(true);

  // Função para calcular distância entre dois pontos (Haversine)
  function calcularDistancia(lat1, lon1, lat2, lon2) {
    const toRad = (value) => (value * Math.PI) / 180;
    const R = 6371; // km

    const dLat = toRad(lat2 - lat1);
    const dLon = toRad(lon2 - lon1);
    const a =
      Math.sin(dLat / 2) ** 2 +
      Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
      Math.sin(dLon / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  useEffect(() => {
    (async () => {
      try {
        const { status } = await Location.requestForegroundPermissionsAsync();
        if (status !== 'granted') {
          Alert.alert('Erro', 'Permissão de localização negada');
          setLoading(false);
          return;
        }

        const location = await Location.getCurrentPositionAsync({});
        const { latitude, longitude } = location.coords;

        const response = await axios.get('http://192.168.0.10:5282/api/Abrigoes', {
          timeout: 10000
        });

        const abrigos = response.data;
        let abrigoProximo = null;
        let menorDistancia = Infinity;
        const RAIO_KM = 30;

        for (const abrigo of abrigos) {
          if (!abrigo.latitude || !abrigo.longitude) continue;

          const distancia = calcularDistancia(latitude, longitude, abrigo.latitude, abrigo.longitude);

          if (distancia < menorDistancia && distancia <= RAIO_KM) {
            menorDistancia = distancia;
            abrigoProximo = abrigo;
          }
        }

        setAbrigoMaisProximo(abrigoProximo);
      } catch (error) {
        console.error(error);
        Alert.alert('Erro', 'Não foi possível obter os dados dos abrigos');
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#2563EB" />
        <Text style={styles.loadingText}>Carregando abrigo mais próximo...</Text>
      </View>
    );
  }

  if (!abrigoMaisProximo) {
    return (
      <View style={styles.container}>
        <Text style={styles.emptyText}>Nenhum abrigo encontrado próximo à sua localização.</Text>
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Home')}>
          <Text style={styles.buttonText}>← Voltar ao Início</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>🚶‍♀️ Abrigo mais próximo encontrado:</Text>

      <Text style={styles.step}>📍 Nome: <Text style={styles.bold}>{abrigoMaisProximo.nome}</Text></Text>
      <Text style={styles.step}>📌 Endereço: <Text style={styles.bold}>{abrigoMaisProximo.endereco}</Text></Text>
      <Text style={styles.step}>🪪 Responsável: <Text style={styles.bold}>{abrigoMaisProximo.usuario?.username || 'Não informado'}</Text></Text>

      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Home')}>
        <Text style={styles.buttonText}>← Voltar ao Início</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    backgroundColor: '#F5F7FA',
    justifyContent: 'center',
    alignItems: 'center',
  },
  loadingText: {
    marginTop: 12,
    color: '#374151',
    fontSize: 16,
  },
  emptyText: {
    fontSize: 18,
    color: '#6B7280',
    marginBottom: 30,
    textAlign: 'center',
  },
  title: {
    fontSize: 22,
    fontWeight: '700',
    marginBottom: 20,
    color: '#1F2937',
    textAlign: 'center',
  },
  step: {
    fontSize: 16,
    color: '#374151',
    marginBottom: 12,
    lineHeight: 24,
    width: '100%',
  },
  bold: {
    fontWeight: '600',
    color: '#111827',
  },
  button: {
    marginTop: 30,
    backgroundColor: '#2563EB',
    paddingVertical: 14,
    paddingHorizontal: 28,
    borderRadius: 12,
    elevation: 3,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 2 },
  },
  buttonText: {
    color: '#FFF',
    fontWeight: '600',
    fontSize: 16,
  },
});

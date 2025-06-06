import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import api from '../api/axios-api'; 
import CardAlerta from '../components/CardAlerta'; 

export default function DashboardScreen({ navigation }) {
  const [alertas, setAlertas] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchAlertas() {
      try {
        const response = await api.get('/alertas');
        setAlertas(response.data);
      } catch (error) {
        console.error('Erro ao buscar alertas:', error.message);
      } finally {
        setLoading(false);
      }
    }

    fetchAlertas();
  }, []);

  const deletarAlerta = async (id) => {
    try {
      await api.delete(`/alertas/${id}`);
      setAlertas((prev) => prev.filter((alerta) => alerta.id !== id));
    } catch (error) {
      console.error('Erro ao deletar alerta:', error.message);
    }
  };

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#2563EB" />
        <Text style={{ marginTop: 12, color: '#374151' }}>Carregando alertas...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      
      <TouchableOpacity
        style={styles.backButton}
        onPress={() => navigation.navigate('Home')}
      >
        <Text style={styles.backButtonText}>← Voltar ao Início</Text>
      </TouchableOpacity>

      <Text style={styles.header}>📢 Alertas Recentes</Text>

      {alertas.length === 0 ? (
        <View style={styles.center}>
          <Text style={styles.emptyText}>Nenhum alerta encontrado.</Text>
        </View>
      ) : (
        <FlatList
          data={alertas}
          keyExtractor={(item) => item.id?.toString() ?? Math.random().toString()}
          renderItem={({ item }) => (
            <CardAlerta
              mensagem={item.mensagem}
              tipo={item.tipo}
              leituraId={item.leitura?.id}
              usuarioId={item.usuario?.id}
              onDelete={() => deletarAlerta(item.id)}
            />
          )}
          contentContainerStyle={styles.list}
          showsVerticalScrollIndicator={false}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F4F6',
    paddingTop: 60,
    paddingHorizontal: 16,
  },
  backButton: {
    alignSelf: 'flex-start',
    marginBottom: 12,
    paddingVertical: 8,
    paddingHorizontal: 14,
    backgroundColor: '#2563EB',
    borderRadius: 10,
    elevation: 3,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowOffset: { width: 0, height: 2 },
    shadowRadius: 4,
  },
  backButtonText: {
    color: '#FFF',
    fontSize: 14,
    fontWeight: '600',
  },
  header: {
    fontSize: 24,
    fontWeight: '700',
    marginBottom: 20,
    textAlign: 'center',
    color: '#111827',
  },
  list: {
    paddingBottom: 20,
  },
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  emptyText: {
    fontSize: 16,
    color: '#6B7280',
  },
});

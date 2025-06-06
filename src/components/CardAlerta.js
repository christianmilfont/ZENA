import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

export default function CardAlerta({ mensagem, tipo, leituraId, usuarioId, onDelete }) {
  return (
    <View style={styles.card}>
      <Text style={styles.tipo}>{tipo}</Text>
      <Text style={styles.mensagem}>{mensagem}</Text>
      <Text style={styles.info}>Leitura ID: {leituraId} | Usuário ID: {usuarioId}</Text>
      {onDelete && (
        <TouchableOpacity onPress={onDelete} style={styles.botaoExcluir}>
          <Text style={styles.textoExcluir}>🗑️ Excluir</Text>
        </TouchableOpacity>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: '#fff',
    borderRadius: 10,
    padding: 16,
    marginBottom: 12,
    elevation: 2,
  },
  tipo: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#EF4444',
  },
  mensagem: {
    fontSize: 14,
    marginVertical: 8,
  },
  info: {
    fontSize: 12,
    color: '#6B7280',
  },
  botaoExcluir: {
    marginTop: 10,
    backgroundColor: '#DC2626',
    padding: 8,
    borderRadius: 6,
    alignSelf: 'flex-start',
  },
  textoExcluir: {
    color: '#fff',
    fontWeight: 'bold',
  },
});

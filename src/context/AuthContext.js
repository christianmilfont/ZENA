// src/context/AuthContext.js
import React, { createContext, useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [authData, setAuthData] = useState({ token: null, username: null, loading: true });

  useEffect(() => {
    async function loadStorage() {
      const token = await AsyncStorage.getItem('@token');
      const username = await AsyncStorage.getItem('@username');
      setAuthData({ token, username, loading: false });
    }
    loadStorage();
  }, []);

  const signIn = async (token, username) => {
    await AsyncStorage.setItem('@token', token);
    await AsyncStorage.setItem('@username', username);
    setAuthData({ token, username, loading: false });
  };

  const signOut = async () => {
    await AsyncStorage.removeItem('@token');
    await AsyncStorage.removeItem('@username');
    setAuthData({ token: null, username: null, loading: false });
  };

  return (
    <AuthContext.Provider value={{ authData, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}

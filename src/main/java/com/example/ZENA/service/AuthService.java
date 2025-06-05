package com.example.ZENA.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.ZENA.model.User;
import com.example.ZENA.model.UserDetailsImpl;
import com.example.ZENA.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

   @Autowired
   private UserRepository repository;

   @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    return new UserDetailsImpl(user);
}
}

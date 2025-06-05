package com.example.ZENA.service;

import com.example.ZENA.model.User;
import com.example.ZENA.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Cacheable("users")
    public Page<User> listarPaginado(Pageable pageable) {
        return userRepo.findAll(pageable);
    }
    //public List<User> listarTodos() {
    //    return userRepo.findAll();
    //}

    public Optional<User> buscarPorId(String id) {
        return userRepo.findById(id);
    }
    public Optional<User> buscarPorNome(String username) {
        return userRepo.findByEmail(username);
    }

    public User criar(User user) {
        return userRepo.save(user);
    }

    public User atualizar(String id, User userAtualizado) {
        return userRepo.findById(id).map(user -> {
            user.setUsername(userAtualizado.getUsername());
            user.setEmail(userAtualizado.getEmail());
            user.setPassword(userAtualizado.getPassword());
            user.setRole(userAtualizado.getRole());
            return userRepo.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletar(String id) {
        userRepo.deleteById(id);
    }
}

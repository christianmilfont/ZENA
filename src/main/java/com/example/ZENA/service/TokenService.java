package com.example.ZENA.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ZENA.model.Token;
import com.example.ZENA.model.User;
import com.example.ZENA.repository.UserRepository;
import com.example.ZENA.model.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private Instant expiresAT = LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.ofHours(-3));
    private Algorithm algorithm = Algorithm.HMAC256("secret");

    @Autowired
    private UserRepository userRepository;

    public Token createToken(User user) {
        var jwt = JWT.create()
            .withSubject(user.getId().toString())
            .withClaim("email", user.getEmail())
            .withExpiresAt(expiresAT)
            .sign(algorithm);
        return new Token(jwt, user.getEmail());
    }

    public UserDetailsImpl getUserFromToken(String token) {
        DecodedJWT verifiedToken = JWT.require(algorithm).build().verify(token);

        String userId = verifiedToken.getSubject();
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + userId));
        
        return new UserDetailsImpl(user);
    }
}

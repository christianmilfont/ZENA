package com.example.ZENA.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ZENA.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class AuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException{
        // validar o header
        var header = request.getHeader("Authorization");
         // Se não houver header, siga com o filtro sem autenticar
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // validar Bearer 
        if (!header.startsWith("Bearer ")){
            response.setStatus(401);
            response.getWriter().write(""" 
                {"message": "Authorization deve iniciar com Bearer"}
                        """);
            return;
            
        }
        //verificar JWT
        var jwt = header.replace("Bearer ", "");
          // getUserFromToken deve retornar um UserImpl (que implementa UserDetails)
        var userDetails = tokenService.getUserFromToken(jwt);
// Cria a autenticação
        var authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );
        //autenticar usuario
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}

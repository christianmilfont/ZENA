package com.example.ZENA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ZENA.model.Alerta;

public interface AlertaRepository extends JpaRepository<Alerta, String> {
    
}

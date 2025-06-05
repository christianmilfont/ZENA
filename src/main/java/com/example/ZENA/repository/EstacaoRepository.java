package com.example.ZENA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.ZENA.model.Estacao;

public interface EstacaoRepository  extends JpaRepository<Estacao, String>, JpaSpecificationExecutor<Estacao>{
    
}

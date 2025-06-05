package com.example.ZENA.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALERTAS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String mensagem;
    private String tipo; // Ex: "urgente", "informativo"
    

     // ID do usuário a quem o alerta se destina
    @ManyToOne
    @JsonIgnore
    private User usuario;

    //Varios alertas podem ter uma leitura
    @ManyToOne
    @JsonIgnore
    private LeituraClimatica leitura;

   
}

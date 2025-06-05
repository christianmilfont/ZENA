package com.example.ZENA.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Essa estacao vai passar a leitura climatica e os dados processados
//por essa leitura (PODE OU NAO) gerar alertas
@Entity
@Table(name = "ESTACOES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Estacao {
    @Id 
    private String id;

    @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[A-Z].*", message = "Deve começar com letra maiúscula")
    private String nome;

    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;

    private boolean ativo;

    //Uma estacao pode ter varios usuarios
    @ManyToOne
    @JsonIgnore
    private User usuario;

    
}

package com.example.ZENA.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LEITURA_CLIMATICAS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeituraClimatica {
    @Id 
    private String id;

    private LocalDateTime dataHora;
    @NotNull(message = "A temperatura é obrigatória")
    private double temperatura;
    @NotNull(message = "A umidade é obrigatória")
    private double umidade;
    @NotNull(message = "A pressão é obrigatória")
    private double pressao;
    @NotNull(message = "A velocidade do vento é obrigatória")
    private double velocidadeVento;
    @NotBlank(message = "A direção do vento é obrigatória")
    private String direcaoVento;
    @NotNull(message = "A chuva é obrigatória")
    private double precipitacao;
    @NotBlank(message = "A condição climática é obrigatória")
    private String condicoesClimaticas;

    //Varias Leituras climaticas podem ser feitas em uma estacao
    @ManyToOne
    @JoinColumn(name = "estacoes_id")  // nome da coluna no banco que faz FK
    private Estacao estacao;
}

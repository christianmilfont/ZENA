package com.example.ZENA.DTO;

import java.time.LocalDateTime;

public class LeituraClimaticaDTO {
    public String estacaoId;
    public Double temperatura;
    public Double umidade;
    public Double pressao;

   
    public Double velocidadeVento;
    public String direcaoVento;
    public Double precipitacao;
    public String condicoesClimaticas;

    public LocalDateTime dataHora; // opcional
}

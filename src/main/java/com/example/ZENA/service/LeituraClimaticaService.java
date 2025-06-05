package com.example.ZENA.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ZENA.DTO.LeituraClimaticaDTO;
import com.example.ZENA.model.Alerta;
import com.example.ZENA.model.Estacao;
import com.example.ZENA.model.LeituraClimatica;
import com.example.ZENA.repository.AlertaRepository;
import com.example.ZENA.repository.EstacaoRepository;
import com.example.ZENA.repository.LeituraClimaticaRepository;

@Service
public class LeituraClimaticaService {

    @Autowired
    private LeituraClimaticaRepository leituraRepo;

    @Autowired
    private EstacaoRepository estacaoRepo;

    @Autowired
    private AlertaRepository alertaRepo;


   public LeituraClimatica registraLeitura(LeituraClimaticaDTO dto) {
    Estacao estacao = estacaoRepo.findById(dto.estacaoId)
        .orElseThrow(() -> new RuntimeException("Estação não encontrada com id " + dto.estacaoId));

    LeituraClimatica leitura = new LeituraClimatica();
// Gerar ID único (UUID)
    leitura.setId(UUID.randomUUID().toString());
    leitura.setEstacao(estacao);
    leitura.setTemperatura(dto.temperatura);
    leitura.setUmidade(dto.umidade);
    leitura.setPressao(dto.pressao);
    leitura.setVelocidadeVento(dto.velocidadeVento);
    leitura.setDirecaoVento(dto.direcaoVento);
    leitura.setPrecipitacao(dto.precipitacao);
    leitura.setCondicoesClimaticas(dto.condicoesClimaticas);
    leitura.setDataHora(dto.dataHora != null ? dto.dataHora : LocalDateTime.now());

    leituraRepo.save(leitura);

    if (dto.temperatura != null && dto.temperatura > 38) {
        alertaRepo.save(
            Alerta.builder()
                .mensagem("Calor extremo")
                .tipo("temperatura acima de 38 graus")
                .leitura(leitura)
                .build()
        );
    }
    if (dto.umidade != null && dto.umidade < 20) {
        alertaRepo.save(
            Alerta.builder()
                .mensagem("Umidade baixa")
                .tipo("umidade abaixo de 20%")
                .leitura(leitura)
                .build()
        );
    }
    if (dto.pressao != null && dto.pressao < 1000) {
        alertaRepo.save(
            Alerta.builder()
                .mensagem("Baixa pressão")
                .tipo("Possível tempestade")
                .leitura(leitura)
                .build()
        );
    }
    return leitura;
}


    public List<LeituraClimatica> buscarTodas() {
        return leituraRepo.findAll();
    }
}

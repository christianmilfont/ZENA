package com.example.ZENA.service;

import com.example.ZENA.model.Alerta;
import com.example.ZENA.model.LeituraClimatica;
import com.example.ZENA.model.User;
import com.example.ZENA.repository.AlertaRepository;
import com.example.ZENA.repository.LeituraClimaticaRepository;
import com.example.ZENA.repository.UserRepository;
import com.example.ZENA.DTO.AlertaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepo;

    @Autowired
    private LeituraClimaticaRepository leituraRepo;

    @Autowired
    private UserRepository userRepo;

    public List<Alerta> listarTodos() {
        return alertaRepo.findAll();
    }

    public Optional<Alerta> buscarPorId(String id) {
    return alertaRepo.findById(id);
}
public Alerta criarFromDTO(AlertaDTO dto) {
    // Validar dados obrigatórios do DTO
    if (dto.leituraId == null) {
        throw new IllegalArgumentException("ID da leitura é obrigatório");
    }
    if (dto.usuarioId == null || dto.usuarioId.isEmpty()) {
        throw new IllegalArgumentException("ID do usuário é obrigatório");
    }

    // Buscar leitura climática pelo id
    LeituraClimatica leitura = leituraRepo.findById(dto.leituraId)
        .orElseThrow(() -> new RuntimeException("Leitura não encontrada"));

    // Buscar usuário pelo id (String)
    User usuario = userRepo.findById(dto.usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    // Criar o objeto alerta completo
    Alerta alerta = new Alerta();
    alerta.setMensagem(dto.mensagem);
    alerta.setTipo(dto.tipo);
    alerta.setLeitura(leitura);
    alerta.setUsuario(usuario);

    // Salvar no banco e retornar
    return alertaRepo.save(alerta);
}

    public Alerta criar(AlertaDTO dto) {
    if (dto.leituraId == null) {
        throw new IllegalArgumentException("ID da leitura é obrigatório");
    }
    if (dto.usuarioId == null) {
        throw new IllegalArgumentException("ID do usuário é obrigatório");
    }

    LeituraClimatica leitura = leituraRepo.findById(dto.leituraId)
        .orElseThrow(() -> new RuntimeException("Leitura não encontrada"));

    User usuario = userRepo.findById(dto.usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    Alerta alerta = new Alerta();
    alerta.setMensagem(dto.mensagem);
    alerta.setTipo(dto.tipo);
    alerta.setLeitura(leitura);
    alerta.setUsuario(usuario);

    return alertaRepo.save(alerta);
}

    public Alerta atualizar(String id, AlertaDTO dto) {
    Alerta alerta = alertaRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
    alerta.setMensagem(dto.mensagem);
    alerta.setTipo(dto.tipo);
    return alertaRepo.save(alerta);
}

    public void deletar(String id) {
    alertaRepo.deleteById(id);
}
}

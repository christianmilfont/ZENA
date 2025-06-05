package com.example.ZENA.controller;

import com.example.ZENA.DTO.AlertaDTO;
import com.example.ZENA.model.Alerta;
import com.example.ZENA.service.AlertaService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    // Listar todos os alertas
    @Operation(summary = "Lista todos os alertas")
    @GetMapping
    public List<Alerta> listarTodos() {
        return alertaService.listarTodos();
    }

    // Buscar alerta por id
    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable String id) {
        return alertaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo alerta (recebe DTO)
    @PostMapping
    public ResponseEntity<Alerta> criar(@RequestBody AlertaDTO alertaDTO) {
        try {
            Alerta alerta = alertaService.criarFromDTO(alertaDTO);
            return ResponseEntity.ok(alerta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Atualizar um alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<Alerta> atualizar(@PathVariable String id, @RequestBody AlertaDTO dto) {
        try {
            Alerta alertaAtualizado = alertaService.atualizar(id, dto);
            return ResponseEntity.ok(alertaAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar alerta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

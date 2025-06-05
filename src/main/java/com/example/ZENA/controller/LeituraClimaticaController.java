package com.example.ZENA.controller;

import com.example.ZENA.DTO.LeituraClimaticaDTO;
import com.example.ZENA.model.LeituraClimatica;
import com.example.ZENA.service.LeituraClimaticaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leituras")
public class LeituraClimaticaController {

    @Autowired
    private LeituraClimaticaService leituraService;

    // Listar todas as leituras
    @GetMapping
    public List<LeituraClimatica> listarTodas() {
        return leituraService.buscarTodas();
    }

    // Registrar nova leitura via DTO
   @PostMapping
public ResponseEntity<?> registrarLeitura(@RequestBody LeituraClimaticaDTO dto) {
    try {
        LeituraClimatica leitura = leituraService.registraLeitura(dto);
        return ResponseEntity.ok(leitura);
    } catch (Exception e) {
        e.printStackTrace();  // imprime no console para debug
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

}

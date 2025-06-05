package com.example.ZENA.controller;

import com.example.ZENA.model.Estacao;
import com.example.ZENA.repository.EstacaoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estacoes")
@Tag(name = "Estacoes", description = "CRUD de estações climáticas")
public class EstacaoController {

    @Autowired
    private EstacaoRepository estacaoRepo;

    @GetMapping
    @Operation(summary = "Listar todas as estações")
    public List<Estacao> listarTodas() {
        return estacaoRepo.findAll();
    }
@GetMapping("/paginado")
@Operation(summary = "Listar estações com paginação, ordenação e filtros")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
})
public ResponseEntity<Page<Estacao>> listarComFiltros(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort,
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String localizacao,
        @RequestParam(required = false) Boolean ativo
) {
    String[] parts = sort.split(",");
if (parts.length != 2) {
    throw new IllegalArgumentException("Formato inválido para sort: use campo,direcao");
}
Sort.Order order = new Sort.Order(Sort.Direction.fromString(parts[1].toUpperCase()), parts[0]);
Sort sortOrder = Sort.by(order);


    Pageable pageable = PageRequest.of(page, size, sortOrder);

    Page<Estacao> resultado = estacaoRepo.findAll((root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null && !nome.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        if (localizacao != null && !localizacao.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("localizacao")), "%" + localizacao.toLowerCase() + "%"));
        }
        if (ativo != null) {
            predicates.add(cb.equal(root.get("ativo"), ativo));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }, pageable);

    return ResponseEntity.ok(resultado);
}
    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma estação por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estação encontrada"),
        @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    public ResponseEntity<Estacao> buscarPorId(@PathVariable String id) {
        return estacaoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova estação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estação criada com sucesso")
    })
    public ResponseEntity<Estacao> criar(@Valid @RequestBody Estacao estacao) {
        Estacao salva = estacaoRepo.save(estacao);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estação existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estação atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    public ResponseEntity<Estacao> atualizar(@Valid @PathVariable String id, @RequestBody Estacao corpo) {
        return estacaoRepo.findById(id)
            .map(existente -> {
                existente.setNome(corpo.getNome());
                existente.setLocalizacao(corpo.getLocalizacao());
                existente.setAtivo(corpo.isAtivo());
                existente.setUsuario(corpo.getUsuario());
                Estacao atualizada = estacaoRepo.save(existente);
                return ResponseEntity.ok(atualizada);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma estação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        if (estacaoRepo.existsById(id)) {
            estacaoRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

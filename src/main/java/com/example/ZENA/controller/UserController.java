package com.example.ZENA.controller;

import com.example.ZENA.DTO.LoginDTO;
import com.example.ZENA.DTO.LoginResponseDTO;
import com.example.ZENA.model.Token;
import com.example.ZENA.model.User;
import com.example.ZENA.service.TokenService;
import com.example.ZENA.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Lista usuários paginados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<User>> listarTodos(
            @Parameter(description = "Informações de paginação e ordenação")
            @PageableDefault(size = 10) Pageable pageable) {
        Page<User> page = userService.listarPaginado(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(
            @Parameter(description = "ID do usuário", required = true, in = ParameterIn.PATH)
            @PathVariable String id) {
        return userService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
            content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PostMapping
    public ResponseEntity<User> criar(
            @Parameter(description = "Objeto usuário para criação", required = true)
            @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User criado = userService.criar(user);
        return ResponseEntity.ok(criado);
    }

    @Operation(summary = "Faz login e gera token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, token gerado",
            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Dados para login", required = true)
            @RequestBody LoginDTO loginDTO) {
        
        Optional<User> usuarioOpt = userService.buscarPorNome(loginDTO.getUsername());

        if (usuarioOpt.isPresent()) {
            User usuario = usuarioOpt.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
                Token token = tokenService.createToken(usuario);
                LoginResponseDTO response = new LoginResponseDTO(token.token(), usuario.getUsername(), usuario.getRole());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body("{\"message\": \"Usuário ou senha inválidos\"}");
    }

    @Operation(summary = "Atualiza um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizar(
            @Parameter(description = "ID do usuário", required = true, in = ParameterIn.PATH)
            @PathVariable String id,
            @Parameter(description = "Dados do usuário para atualização", required = true)
            @RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User atualizado = userService.atualizar(id, user);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário", required = true, in = ParameterIn.PATH)
            @PathVariable String id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

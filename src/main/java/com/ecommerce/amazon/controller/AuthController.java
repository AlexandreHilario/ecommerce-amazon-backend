package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.auth.LoginRequestDTO;
import com.ecommerce.amazon.dto.auth.RegisterRequestDTO;
import com.ecommerce.amazon.dto.auth.ResetSenhaDTO;
import com.ecommerce.amazon.dto.auth.TokenResponseDTO;
import com.ecommerce.amazon.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<TokenResponseDTO> cadastrar(@RequestBody @Valid RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastrar(dto));
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody @Valid ResetSenhaDTO dto) {
        authService.redefinirSenha(dto);
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
} 


package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.auth.EsqueciSenhaDTO;
import com.ecommerce.amazon.dto.auth.LoginRequestDTO;
import com.ecommerce.amazon.dto.auth.RegisterRequestDTO;
import com.ecommerce.amazon.dto.auth.ResetSenhaDTO;
import com.ecommerce.amazon.dto.auth.TokenResponseDTO;
import com.ecommerce.amazon.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<TokenResponseDTO> cadastrar(@RequestBody @Valid RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastrar(dto));
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueceuSenha(@RequestBody @Valid EsqueciSenhaDTO dto) {
        authService.esqueceuSenha(dto);
        return ResponseEntity.ok("E-mail de redefinição enviado com sucesso.");
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody @Valid ResetSenhaDTO dto) {
        authService.redefinirSenha(dto);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}

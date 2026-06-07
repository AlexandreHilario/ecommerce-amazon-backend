package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.amazon.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.amazon.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
}

package com.ecommerce.amazon.controller;


import com.ecommerce.amazon.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.amazon.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criarUsuario")
    public UsuarioResponseDTO addUsuario(@RequestBody UsuarioRequestDTO usuario) {
        return usuarioService.novoUsuario(usuario);
    }

    @GetMapping("/usuario/{id}")
    public Usuario getUser(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PutMapping("/atualizarUsuario/{id}")
    public Usuario updateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        return usuarioService.updateUsuario(id, usuario);
    }

    @DeleteMapping("usuario/{id}")
    public ResponseEntity.BodyBuilder deletarUsuario(@PathVariable Long id){
        boolean deletado = usuarioService.deleteById(id);

        if(deletado)
            return ResponseEntity.status(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND);
    }


}


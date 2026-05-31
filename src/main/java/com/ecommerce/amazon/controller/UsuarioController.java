package com.ecommerce.amazon.controller;


import com.ecommerce.amazon.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.amazon.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criarUsuario")
    public UsuarioResponseDTO addUsuario(@RequestBody UsuarioRequestDTO usuario){
        return usuarioService.novoUsuario(usuario);
    }

    @GetMapping("/usuario/{email}")
    public Usuario getUser(@PathVariable String email){
        return  usuarioService.findByEmail(email);
    }
    



}

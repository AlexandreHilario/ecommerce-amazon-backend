package com.ecommerce.amazon.controller;


import com.ecommerce.amazon.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.amazon.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.amazon.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {


    @PostMapping("/criarUsuario")
    public UsuarioResponseDTO addUsuario(@RequestBody UsuarioRequestDTO usuario){
        return UsuarioService.novoUsuario(usuario);
    }

}

package com.ecommerce.amazon.dto.usuario;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String nomeUsuario;
    private int idadeUsuario;
    private String emailUsuario;
    private String senhaUsuario;
    
}

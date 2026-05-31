package com.ecommerce.amazon.dto.usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponseDTO {
    private  long id;
    private String nomeUsuario;
    private String emailUsuario;
    private int idadeUsuario;
}

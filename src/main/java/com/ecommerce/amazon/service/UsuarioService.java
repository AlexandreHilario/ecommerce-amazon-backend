package com.ecommerce.amazon.service;

import com.ecommerce.amazon.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.amazon.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO novoUsuario(UsuarioRequestDTO usuario){
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNome(usuario.getNomeUsuario());
        novoUsuario.setIdade(usuario.getIdadeUsuario());
        novoUsuario.setEmail(usuario.getEmailUsuario());
        novoUsuario.setSenha(usuario.getSenhaUsuario());

        Usuario salvarUsuario = usuarioRepository.save(novoUsuario);

        return  new UsuarioResponseDTO(
                salvarUsuario.getId(),
                salvarUsuario.getNome(),
                salvarUsuario.getEmail(),
                salvarUsuario.getIdade()

        );
    }

    public Usuario findById(Long id){
        return usuarioRepository.findById(id).get();
    }

    public Usuario updateUsuario(Long id, Usuario novoUsuario){
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setNome(novoUsuario.getNome());
        usuario.setIdade(novoUsuario.getIdade());
        usuario.setSenha(novoUsuario.getSenha());

        return usuarioRepository.save(usuario);
    }

    public boolean deleteById(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}

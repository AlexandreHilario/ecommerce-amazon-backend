package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByUsuarioId(Long usuarioId);
    
    List<Venda> findByUsuarioIdAndStatus(Long usuarioId, StatusVenda status);

    List<Venda> findByUsuario(Usuario usuario);

    List<Venda> findByStatus(StatusVenda status);

    List<Venda> findByUsuarioAndStatus(Usuario usuario, StatusVenda status);
}
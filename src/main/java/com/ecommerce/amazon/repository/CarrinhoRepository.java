package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}

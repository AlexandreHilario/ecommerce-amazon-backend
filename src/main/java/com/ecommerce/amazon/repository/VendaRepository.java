package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByUsuarioId(Long usuarioId);
    List<Venda> findByUsuarioIdAndStatus(Long usuarioId, StatusVenda status);
    List<Venda> findByStatus(StatusVenda status);
}

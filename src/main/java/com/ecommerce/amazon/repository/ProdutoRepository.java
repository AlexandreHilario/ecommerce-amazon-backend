package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrue();
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}

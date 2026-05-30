package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {
    List<VendaProduto> findByVendaId(Long vendaId);
    List<VendaProduto> findByProdutoId(Long produtoId);
}

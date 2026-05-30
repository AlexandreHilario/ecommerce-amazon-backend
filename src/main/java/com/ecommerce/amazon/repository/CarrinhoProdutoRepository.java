package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.CarrinhoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarrinhoProdutoRepository extends JpaRepository<CarrinhoProduto, Long> {
    List<CarrinhoProduto> findByCarrinhoId(Long carrinhoId);
    Optional<CarrinhoProduto> findByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);
    void deleteByCarrinhoId(Long carrinhoId);
}

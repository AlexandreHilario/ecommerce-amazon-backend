package com.ecommerce.amazon.service;

import com.ecommerce.amazon.entity.Produto;
import com.ecommerce.amazon.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id).get();
    }

    public Produto createProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto updateProduto(Long id, Produto novoProduto) {

        Produto produto = produtoRepository.findById(id).get();

        produto.setNome(novoProduto.getNome());
        produto.setDescricao(novoProduto.getDescricao());
        produto.setPreco(novoProduto.getPreco());
        produto.setEstoque(novoProduto.getEstoque());
        produto.setAtivo(novoProduto.getAtivo());

        return produtoRepository.save(produto);
    }

    public boolean deleteById(Long id) {

        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
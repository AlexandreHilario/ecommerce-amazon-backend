package com.ecommerce.amazon.service;

import com.ecommerce.amazon.dto.produto.ProdutoRequestDTO;
import com.ecommerce.amazon.dto.produto.ProdutoResponseDTO;
import com.ecommerce.amazon.entity.Produto;
import com.ecommerce.amazon.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return converterParaDTO(produto);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {

        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .estoque(dto.getEstoque())
                .ativo(dto.getAtivo())
                .build();

        produto = produtoRepository.save(produto);

        return converterParaDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        produto.setAtivo(dto.getAtivo());

        produto = produtoRepository.save(produto);

        return converterParaDTO(produto);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .estoque(produto.getEstoque())
                .ativo(produto.getAtivo())
                .build();
    }
}

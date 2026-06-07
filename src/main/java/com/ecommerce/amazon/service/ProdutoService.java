package com.ecommerce.amazon.service;

import com.ecommerce.amazon.dto.produto.ProdutoRequestDTO;
import com.ecommerce.amazon.dto.produto.ProdutoResponseDTO;
import com.ecommerce.amazon.entity.Produto;
import com.ecommerce.amazon.exception.BusinessException;
import com.ecommerce.amazon.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream().map(this::converterParaDTO).toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return converterParaDTO(produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado")));
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto, MultipartFile imagem) {
        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .estoque(dto.getEstoque())
                .ativo(dto.getAtivo())
                .imagem(extrairBytes(imagem))
                .build();
        return converterParaDTO(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto, MultipartFile imagem) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        produto.setAtivo(dto.getAtivo());
        if (imagem != null && !imagem.isEmpty()) {
            produto.setImagem(extrairBytes(imagem));
        }
        return converterParaDTO(produtoRepository.save(produto));
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new BusinessException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoRepository.findByAtivoTrue().stream().map(this::converterParaDTO).toList();
    }

    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome).stream().map(this::converterParaDTO).toList();
    }

    private byte[] extrairBytes(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new BusinessException("Erro ao processar imagem.");
        }
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        String imagemBase64 = produto.getImagem() != null
                ? Base64.getEncoder().encodeToString(produto.getImagem())
                : null;
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .estoque(produto.getEstoque())
                .ativo(produto.getAtivo())
                .imagemBase64(imagemBase64)
                .build();
    }
}

package com.ecommerce.amazon.service;

import com.ecommerce.amazon.dto.carrinho.AdicionarProdutoDTO;
import com.ecommerce.amazon.dto.carrinho.AtualizarQuantidadeDTO;
import com.ecommerce.amazon.dto.carrinho.CarrinhoDTO;
import com.ecommerce.amazon.entity.Carrinho;
import com.ecommerce.amazon.entity.CarrinhoProduto;
import com.ecommerce.amazon.entity.Produto;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.exception.BusinessException;
import com.ecommerce.amazon.repository.CarrinhoProdutoRepository;
import com.ecommerce.amazon.repository.CarrinhoRepository;
import com.ecommerce.amazon.repository.ProdutoRepository;
import com.ecommerce.amazon.repository.UsuarioRepository;
import com.ecommerce.amazon.mapper.CarrinhoMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoProdutoRepository carrinhoProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarrinhoMapper carrinhoMapper;

    public CarrinhoDTO criarCarrinho(Long usuarioId) {
        if (carrinhoRepository.existsByUsuarioId(usuarioId)) {
            throw new BusinessException("Usuário já possui carrinho.");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado: " + usuarioId));
        Carrinho carrinho = carrinhoRepository.save(Carrinho.builder().usuario(usuario).build());
        return CarrinhoDTO.builder()
                .id(carrinho.getId())
                .usuarioId(usuarioId)
                .quantidadeItens(0)
                .total(BigDecimal.ZERO)
                .build();
    }

    public CarrinhoDTO buscarPorUsuario(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new BusinessException("Carrinho não encontrado para o usuário: " + usuarioId));
        List<CarrinhoProduto> itens = carrinhoProdutoRepository.findByCarrinhoId(carrinho.getId());
        BigDecimal total = itens.stream()
                .map(i -> i.getProduto().getPreco().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return CarrinhoDTO.builder()
                .id(carrinho.getId())
                .usuarioId(usuarioId)
                .quantidadeItens(itens.size())
                .total(total)
                .build();
    }

    public CarrinhoDTO adicionarProduto(Long carrinhoId, AdicionarProdutoDTO dto) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new BusinessException("Carrinho não encontrado: " + carrinhoId));
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new BusinessException("Produto não encontrado: " + dto.getProdutoId()));

        carrinhoProdutoRepository
                .findByCarrinhoIdAndProdutoId(carrinhoId, dto.getProdutoId())
                .ifPresentOrElse(
                        item -> {
                            item.setQuantidade(item.getQuantidade() + dto.getQuantidade());
                            carrinhoProdutoRepository.save(item);
                        },
                        () -> carrinhoProdutoRepository.save(
                                CarrinhoProduto.builder()
                                        .carrinho(carrinho)
                                        .produto(produto)
                                        .quantidade(dto.getQuantidade())
                                        .build()
                        )
                );

        return buscarPorUsuario(carrinho.getUsuario().getId());
    }

    public void removerProduto(Long carrinhoId, Long produtoId) {
        CarrinhoProduto item = carrinhoProdutoRepository
                .findByCarrinhoIdAndProdutoId(carrinhoId, produtoId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado no carrinho."));
        carrinhoProdutoRepository.delete(item);
    }

    public void atualizarQuantidade(Long carrinhoId, Long produtoId, AtualizarQuantidadeDTO dto) {
        CarrinhoProduto item = carrinhoProdutoRepository
                .findByCarrinhoIdAndProdutoId(carrinhoId, produtoId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado no carrinho."));
        item.setQuantidade(dto.getQuantidade());
        carrinhoProdutoRepository.save(item);
    }

    public void limparCarrinho(Long carrinhoId) {
        carrinhoProdutoRepository.deleteByCarrinhoId(carrinhoId);
    }
}

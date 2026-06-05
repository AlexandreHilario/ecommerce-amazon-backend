package com.ecommerce.amazon.service;

import com.ecommerce.amazon.dto.carrinho.AdicionarProdutoDTO;
import com.ecommerce.amazon.dto.carrinho.AtualizarQuantidadeDTO;
import com.ecommerce.amazon.dto.carrinho.CarrinhoDTO;
import com.ecommerce.amazon.entity.Carrinho;
import com.ecommerce.amazon.entity.CarrinhoProduto;
import com.ecommerce.amazon.entity.Produto;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.repository.CarrinhoProdutoRepository;
import com.ecommerce.amazon.repository.CarrinhoRepository;
import com.ecommerce.amazon.repository.ProdutoRepository;
import com.ecommerce.amazon.repository.UsuarioRepository;
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

    public Carrinho criarCarrinho(Long usuarioId) {

        if (carrinhoRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Usuário já possui carrinho");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Carrinho carrinho = Carrinho.builder()
                .usuario(usuario)
                .build();

        return carrinhoRepository.save(carrinho);
    }

    public CarrinhoDTO buscarPorUsuario(Long usuarioId) {

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Carrinho não encontrado"));

        List<CarrinhoProduto> itens =
                carrinhoProdutoRepository.findByCarrinhoId(
                        carrinho.getId()
                );

        BigDecimal total = BigDecimal.ZERO;

        for (CarrinhoProduto item : itens) {
            total = total.add(
                    item.getProduto()
                            .getPreco()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantidade()
                                    )
                            )
            );
        }

        return CarrinhoDTO.builder()
                .id(carrinho.getId())
                .usuarioId(usuarioId)
                .quantidadeItens(itens.size())
                .total(total)
                .build();
    }

    public Carrinho adicionarProduto(
            Long carrinhoId,
            AdicionarProdutoDTO dto
    ) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() ->
                        new RuntimeException("Carrinho não encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        CarrinhoProduto itemExistente =
                carrinhoProdutoRepository
                        .findByCarrinhoIdAndProdutoId(
                                carrinhoId,
                                dto.getProdutoId()
                        )
                        .orElse(null);

        if (itemExistente != null) {

            itemExistente.setQuantidade(
                    itemExistente.getQuantidade() + dto.getQuantidade()
            );

            carrinhoProdutoRepository.save(itemExistente);

            return carrinho;
        }

        CarrinhoProduto item = CarrinhoProduto.builder()
                .carrinho(carrinho)
                .produto(produto)
                .quantidade(dto.getQuantidade())
                .build();

        carrinhoProdutoRepository.save(item);

        return carrinho;
    }

    public void removerProduto(
            Long carrinhoId,
            Long produtoId
    ) {

        CarrinhoProduto item =
                carrinhoProdutoRepository
                        .findByCarrinhoIdAndProdutoId(
                                carrinhoId,
                                produtoId
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Produto não encontrado no carrinho"));

        carrinhoProdutoRepository.delete(item);
    }

    public void atualizarQuantidade(
            Long carrinhoId,
            Long produtoId,
            AtualizarQuantidadeDTO dto
    ) {

        CarrinhoProduto item =
                carrinhoProdutoRepository
                        .findByCarrinhoIdAndProdutoId(
                                carrinhoId,
                                produtoId
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Produto não encontrado no carrinho"));

        item.setQuantidade(dto.getQuantidade());

        carrinhoProdutoRepository.save(item);
    }

    public void limparCarrinho(Long carrinhoId) {

        carrinhoProdutoRepository.deleteByCarrinhoId(carrinhoId);
    }
}

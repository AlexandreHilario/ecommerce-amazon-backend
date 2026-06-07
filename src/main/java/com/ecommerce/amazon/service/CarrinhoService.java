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
import com.ecommerce.amazon.mapper.CarrinhoMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

        private final CarrinhoRepository carrinhoRepository;
        private final CarrinhoProdutoRepository carrinhoProdutoRepository;
        private final ProdutoRepository produtoRepository;
        private final UsuarioRepository usuarioRepository;
        private final CarrinhoMapper carrinhoMapper;

        public Carrinho criarCarrinho(Long usuarioId) {

                if (carrinhoRepository.existsByUsuarioId(usuarioId)) {
                        throw new RuntimeException("Usuário já possui carrinho");
                }

                Usuario usuario = usuarioRepository.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                Carrinho carrinho = carrinhoMapper.toEntity(usuario);

                return carrinhoRepository.save(carrinho);
        }

        public CarrinhoDTO buscarPorUsuario(Long usuarioId) {

                Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

                List<CarrinhoProduto> itens = carrinhoProdutoRepository.findByCarrinhoId(
                                carrinho.getId());

                return carrinhoMapper.toDTO(carrinho, itens);
        }

        public Carrinho adicionarProduto(
                        Long carrinhoId,
                        AdicionarProdutoDTO dto) {

                if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                        throw new RuntimeException("A quantidade deve ser maior que zero");
                }

                Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

                Produto produto = produtoRepository.findById(dto.getProdutoId())
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                CarrinhoProduto itemExistente = carrinhoProdutoRepository
                                .findByCarrinhoIdAndProdutoId(
                                                carrinhoId,
                                                dto.getProdutoId())
                                .orElse(null);

                if (itemExistente != null) {

                        itemExistente.setQuantidade(
                                        itemExistente.getQuantidade() + dto.getQuantidade());

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
                        Long produtoId) {

                CarrinhoProduto item = carrinhoProdutoRepository
                                .findByCarrinhoIdAndProdutoId(
                                                carrinhoId,
                                                produtoId)
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));

                carrinhoProdutoRepository.delete(item);
        }

        public void atualizarQuantidade(
                        Long carrinhoId,
                        Long produtoId,
                        AtualizarQuantidadeDTO dto) {

                if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
                        throw new RuntimeException("A quantidade deve ser maior que zero");
                }

                CarrinhoProduto item = carrinhoProdutoRepository
                                .findByCarrinhoIdAndProdutoId(
                                                carrinhoId,
                                                produtoId)
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));

                item.setQuantidade(dto.getQuantidade());

                carrinhoProdutoRepository.save(item);
        }

        public void limparCarrinho(Long carrinhoId) {

                carrinhoProdutoRepository.deleteByCarrinhoId(carrinhoId);
        }
}

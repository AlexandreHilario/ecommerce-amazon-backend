package com.ecommerce.amazon.service;

import com.ecommerce.amazon.entity.Carrinho;
import com.ecommerce.amazon.entity.CarrinhoProduto;
import com.ecommerce.amazon.entity.Produto;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.VendaProduto;
import com.ecommerce.amazon.entity.enums.StatusVenda;

import com.ecommerce.amazon.exception.BusinessException;
import com.ecommerce.amazon.repository.CarrinhoProdutoRepository;
import com.ecommerce.amazon.repository.CarrinhoRepository;
import com.ecommerce.amazon.repository.ProdutoRepository;
import com.ecommerce.amazon.repository.UsuarioRepository;
import com.ecommerce.amazon.repository.VendaProdutoRepository;
import com.ecommerce.amazon.repository.VendaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;



@Service
@RequiredArgsConstructor
public class VendaService {

   

    private final VendaRepository vendaRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoProdutoRepository carrinhoProdutoRepository;

    public Venda buscarPorId(Long id) {

        return vendaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Venda não encontrada"));
    }

    public List<Venda> buscarHistorico(Long usuarioId) {

        return vendaRepository.findByUsuarioId(usuarioId);
    }

    public Venda atualizarStatus(Long vendaId,
                                 StatusVenda novoStatus) {

        Venda venda = buscarPorId(vendaId);

        venda.setStatus(novoStatus);

        return vendaRepository.save(venda);
    }

    private void baixarEstoque(
            Produto produto,
            Integer quantidade
    ) {

        if (produto.getEstoque() < quantidade) {
            throw new BusinessException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        produto.setEstoque(
                produto.getEstoque() - quantidade
        );

        produtoRepository.save(produto);
    }

    public Venda finalizarCompra(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new BusinessException("Carrinho não encontrado"));

        List<CarrinhoProduto> itensCarrinho =
                carrinhoProdutoRepository.findByCarrinhoId(
                        carrinho.getId()
                );

        if (itensCarrinho.isEmpty()) {
                throw new BusinessException("Carrinho vazio");
        }

        Venda venda = Venda.builder()
                .usuario(usuario)
                .valorTotal(BigDecimal.ZERO)
                .status(StatusVenda.PENDENTE)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CarrinhoProduto item : itensCarrinho) {

            Produto produto = item.getProduto();

            baixarEstoque(
                    produto,
                    item.getQuantidade()
            );

            VendaProduto vendaProduto = VendaProduto.builder()
                    .venda(venda)
                    .produto(produto)
                    .quantidade(item.getQuantidade())
                    .precoUnitario(produto.getPreco())
                    .build();

            venda.getItens().add(vendaProduto);

            total = total.add(
                    produto.getPreco().multiply(
                            BigDecimal.valueOf(item.getQuantidade())
                    )
            );
        }

        venda.setValorTotal(total);

        Venda vendaSalva = vendaRepository.save(venda);

        carrinhoProdutoRepository.deleteByCarrinhoId(
                carrinho.getId()
        );

        return vendaSalva;
    }
}
  
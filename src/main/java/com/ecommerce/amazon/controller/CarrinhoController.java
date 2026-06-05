package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.carrinho.AdicionarProdutoDTO;
import com.ecommerce.amazon.dto.carrinho.AtualizarQuantidadeDTO;
import com.ecommerce.amazon.dto.carrinho.CarrinhoDTO;
import com.ecommerce.amazon.entity.Carrinho;
import com.ecommerce.amazon.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping("/usuario/{usuarioId}")
    public Carrinho criarCarrinho(
            @PathVariable Long usuarioId
    ) {
        return carrinhoService.criarCarrinho(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public CarrinhoDTO buscarPorUsuario(
            @PathVariable Long usuarioId
    ) {
        return carrinhoService.buscarPorUsuario(usuarioId);
    }

    @PostMapping("/{carrinhoId}/produtos")
    public Carrinho adicionarProduto(
            @PathVariable Long carrinhoId,
            @RequestBody AdicionarProdutoDTO dto
    ) {
        return carrinhoService.adicionarProduto(
                carrinhoId,
                dto
        );
    }

    @DeleteMapping("/{carrinhoId}/produtos/{produtoId}")
    public void removerProduto(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId
    ) {
        carrinhoService.removerProduto(
                carrinhoId,
                produtoId
        );
    }

    @PutMapping("/{carrinhoId}/produtos/{produtoId}")
    public void atualizarQuantidade(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestBody AtualizarQuantidadeDTO dto
    ) {
        carrinhoService.atualizarQuantidade(
                carrinhoId,
                produtoId,
                dto
        );
    }

    @DeleteMapping("/{carrinhoId}/limpar")
    public void limparCarrinho(
            @PathVariable Long carrinhoId
    ) {
        carrinhoService.limparCarrinho(carrinhoId);
    }
}

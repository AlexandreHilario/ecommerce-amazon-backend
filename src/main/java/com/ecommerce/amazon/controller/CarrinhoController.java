package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.carrinho.AdicionarProdutoDTO;
import com.ecommerce.amazon.dto.carrinho.AtualizarQuantidadeDTO;
import com.ecommerce.amazon.dto.carrinho.CarrinhoDTO;
import com.ecommerce.amazon.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrinhos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarrinhoDTO> criarCarrinho(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoService.criarCarrinho(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarrinhoDTO> buscarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carrinhoService.buscarPorUsuario(usuarioId));
    }

    @PostMapping("/{carrinhoId}/produtos")
    public ResponseEntity<CarrinhoDTO> adicionarProduto(
            @PathVariable Long carrinhoId,
            @RequestBody AdicionarProdutoDTO dto) {
        return ResponseEntity.ok(carrinhoService.adicionarProduto(carrinhoId, dto));
    }

    @DeleteMapping("/{carrinhoId}/produtos/{produtoId}")
    public ResponseEntity<Void> removerProduto(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId) {
        carrinhoService.removerProduto(carrinhoId, produtoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{carrinhoId}/produtos/{produtoId}")
    public ResponseEntity<Void> atualizarQuantidade(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestBody AtualizarQuantidadeDTO dto) {
        carrinhoService.atualizarQuantidade(carrinhoId, produtoId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{carrinhoId}/limpar")
    public ResponseEntity<Void> limparCarrinho(@PathVariable Long carrinhoId) {
        carrinhoService.limparCarrinho(carrinhoId);
        return ResponseEntity.noContent().build();
    }
}

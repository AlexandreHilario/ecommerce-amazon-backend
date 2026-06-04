package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.produto.ProdutoRequestDTO;
import com.ecommerce.amazon.dto.produto.ProdutoResponseDTO;
import com.ecommerce.amazon.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(
            @PathVariable Long id
    ) {
        return produtoService.buscarPorId(id);
    }

    @PostMapping
    public ProdutoResponseDTO criar(
            @RequestBody ProdutoRequestDTO dto
    ) {
        return produtoService.criar(dto);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO dto
    ) {
        return produtoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(
            @PathVariable Long id
    ) {
        produtoService.deletar(id);
    }

    @GetMapping("/ativos")
    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoService.listarAtivos();
    }

    @GetMapping("/buscar")
    public List<ProdutoResponseDTO> buscarPorNome(
            @RequestParam String nome
    ) {
        return produtoService.buscarPorNome(nome);
    }
}
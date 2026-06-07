package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.produto.ProdutoRequestDTO;
import com.ecommerce.amazon.dto.produto.ProdutoResponseDTO;
import com.ecommerce.amazon.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ProdutoResponseDTO criar(
            @RequestPart("dados") ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return produtoService.criar(dto, imagem);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ProdutoResponseDTO atualizar(
            @PathVariable Long id,
            @RequestPart("dados") ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return produtoService.atualizar(id, dto, imagem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ativos")
    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoService.listarAtivos();
    }

    @GetMapping("/buscar")
    public List<ProdutoResponseDTO> buscarPorNome(@RequestParam String nome) {
        return produtoService.buscarPorNome(nome);
    }
}

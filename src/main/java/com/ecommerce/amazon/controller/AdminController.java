package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.dto.produto.ProdutoRequestDTO;
import com.ecommerce.amazon.dto.produto.ProdutoResponseDTO;
import com.ecommerce.amazon.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.amazon.dto.venda.VendaResponseDTO;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import com.ecommerce.amazon.mapper.VendaMapper;
import com.ecommerce.amazon.service.ProdutoService;
import com.ecommerce.amazon.service.UsuarioService;
import com.ecommerce.amazon.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;
    private final VendaMapper vendaMapper;

    // ─── Usuários ───────────────────────────────────────────────────────────

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Produtos ───────────────────────────────────────────────────────────

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @PostMapping(value = "/produtos", consumes = "multipart/form-data")
    public ResponseEntity<ProdutoResponseDTO> criarProduto(
            @RequestPart("dados") ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return ResponseEntity.ok(produtoService.criar(dto, imagem));
    }

    @PutMapping(value = "/produtos/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestPart("dados") ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto, imagem));
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Vendas ─────────────────────────────────────────────────────────────

    @GetMapping("/vendas")
    public ResponseEntity<List<VendaResponseDTO>> listarVendas() {
        return ResponseEntity.ok(vendaMapper.toResponseDTOList(vendaService.listarTodas()));
    }

    @GetMapping("/vendas/{id}")
    public ResponseEntity<VendaResponseDTO> buscarVenda(@PathVariable Long id) {
        return ResponseEntity.ok(vendaMapper.toResponseDTO(vendaService.buscarPorId(id)));
    }

    @PatchMapping("/vendas/{id}/status")
    public ResponseEntity<VendaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusVenda status) {
        return ResponseEntity.ok(vendaMapper.toResponseDTO(vendaService.atualizarStatus(id, status)));
    }
}

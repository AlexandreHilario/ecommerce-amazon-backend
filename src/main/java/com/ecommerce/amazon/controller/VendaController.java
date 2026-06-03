package com.ecommerce.amazon.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import com.ecommerce.amazon.service.VendaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping("/{id}")
    public Venda buscarPorId(@PathVariable Long id) {
        return vendaService.buscarPorId(id);
    }

    @GetMapping("/historico/{usuarioId}")
    public List<Venda> buscarHistorico(
            @PathVariable Long usuarioId) {

        return vendaService.buscarHistorico(usuarioId);
    }

    @PatchMapping("/{id}/status")
    public Venda atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusVenda status) {

        return vendaService.atualizarStatus(id, status);
    }

    @PostMapping("/finalizar/{usuarioId}")
    public Venda finalizarCompra(
            @PathVariable Long usuarioId) {

        return vendaService.finalizarCompra(usuarioId);
    }
}
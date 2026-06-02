package com.ecommerce.amazon.controller;

import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import com.ecommerce.amazon.service.VendaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
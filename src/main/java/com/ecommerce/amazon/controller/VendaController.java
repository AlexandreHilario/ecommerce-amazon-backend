package com.ecommerce.amazon.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ecommerce.amazon.dto.venda.VendaResponseDTO;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import com.ecommerce.amazon.mapper.VendaMapper;
import com.ecommerce.amazon.service.VendaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;
    private final VendaMapper vendaMapper;     

    @GetMapping("/{id}")
    public VendaResponseDTO buscarPorId(@PathVariable Long id) {
        return vendaMapper.toResponseDTO(
                vendaService.buscarPorId(id)
        );
    }

    @GetMapping("/historico/{usuarioId}")
    public List<VendaResponseDTO> buscarHistorico(@PathVariable Long usuarioId) {
        return vendaMapper.toResponseDTOList(
                vendaService.buscarHistorico(usuarioId)
        );
    }

    @PatchMapping("/{id}/status")
    public VendaResponseDTO atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusVenda status) {
        return vendaMapper.toResponseDTO(
                vendaService.atualizarStatus(id, status)
        );
    }

    @PostMapping("/finalizar/{usuarioId}")
    public VendaResponseDTO finalizarCompra(@PathVariable Long usuarioId) {
        return vendaMapper.toResponseDTO(
                vendaService.finalizarCompra(usuarioId)
        );
    }
    @GetMapping("/listar")
    public List<VendaResponseDTO> listarTodas() {
        return vendaMapper.toResponseDTOList(
                vendaService.listarTodas()
        );
    }
}
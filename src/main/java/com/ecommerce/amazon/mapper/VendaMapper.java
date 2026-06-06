package com.ecommerce.amazon.mapper;

import com.ecommerce.amazon.dto.venda.VendaItemResponseDTO;
import com.ecommerce.amazon.dto.venda.VendaResponseDTO;
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.VendaProduto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class VendaMapper {

    public VendaResponseDTO toResponseDTO(Venda venda) {

        List<VendaItemResponseDTO> itensDTO = venda.getItens()
                .stream()
                .map(this::toItemDTO)
                .toList();

        return VendaResponseDTO.builder()
                .vendaId(venda.getId())
                .usuarioId(venda.getUsuario().getId())
                .nomeUsuario(venda.getUsuario().getNome())
                .valorTotal(venda.getValorTotal())
                .status(venda.getStatus().name())
                .criadoEm(venda.getCriadoEm())
                .itens(itensDTO)
                .build();
    }

    private VendaItemResponseDTO toItemDTO(VendaProduto item) {

        return VendaItemResponseDTO.builder()
                .produtoId(item.getProduto().getId())
                .nomeProduto(item.getProduto().getNome())
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .subtotal(
                        item.getPrecoUnitario()
                                .multiply(BigDecimal.valueOf(item.getQuantidade()))
                )
                .build();
    }
    public List<VendaResponseDTO> toResponseDTOList(List<Venda> vendas) {
        return vendas.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
package com.ecommerce.amazon.mapper;

import com.ecommerce.amazon.dto.venda.VendaProdutoResponseDTO;
import com.ecommerce.amazon.dto.venda.VendaResponseDTO;
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.VendaProduto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component  // Spring gerencia esse objeto, pode injetar em qualquer lugar
public class VendaMapper {

    // Converte uma Venda inteira em VendaResponseDTO
    public VendaResponseDTO toResponseDTO(Venda venda) {

        List<VendaProdutoResponseDTO> itensDTO = venda.getItens()
                .stream()
                .map(this::toItemDTO)   // para cada item, chama toItemDTO
                .toList();

        return VendaResponseDTO.builder()
                .id(venda.getId())
                .usuarioId(venda.getUsuario().getId())
                .nomeUsuario(venda.getUsuario().getNome())  // ajuste se o campo tiver outro nome
                .valorTotal(venda.getValorTotal())
                .status(venda.getStatus())
                .criadoEm(venda.getCriadoEm())
                .itens(itensDTO)
                .build();
    }

    // Converte uma lista de Vendas em lista de DTOs
    public List<VendaResponseDTO> toResponseDTOList(List<Venda> vendas) {
        return vendas.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Converte um item VendaProduto em VendaProdutoResponseDTO
    private VendaProdutoResponseDTO toItemDTO(VendaProduto item) {
        return VendaProdutoResponseDTO.builder()
                .produtoId(item.getProduto().getId())
                .nomeProduto(item.getProduto().getNome())  // ajuste se o campo tiver outro nome
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .subtotal(item.getPrecoUnitario()
                        .multiply(java.math.BigDecimal.valueOf(item.getQuantidade())))
                .build();
    }
}
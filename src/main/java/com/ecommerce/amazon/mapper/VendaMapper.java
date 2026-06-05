package com.ecommerce.amazon.mapper;

import com.ecommerce.amazon.dto.venda.VendaProdutoResponseDTO;
import com.ecommerce.amazon.dto.venda.VendaResponseDTO;
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.VendaProduto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component  
public class VendaMapper {

   
    public VendaResponseDTO toResponseDTO(Venda venda) {

        List<VendaProdutoResponseDTO> itensDTO = venda.getItens()
                .stream()
                .map(this::toItemDTO)
                .toList();

        return VendaResponseDTO.builder()
                .id(venda.getId())
                .usuarioId(venda.getUsuario().getId())
                .nomeUsuario(venda.getUsuario().getNome())  
                .valorTotal(venda.getValorTotal())
                .status(venda.getStatus())
                .criadoEm(venda.getCriadoEm())
                .itens(itensDTO)
                .build();
    }


    public List<VendaResponseDTO> toResponseDTOList(List<Venda> vendas) {
        return vendas.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    
    private VendaProdutoResponseDTO toItemDTO(VendaProduto item) {
        return VendaProdutoResponseDTO.builder()
                .produtoId(item.getProduto().getId())
                .nomeProduto(item.getProduto().getNome()) 
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .subtotal(item.getPrecoUnitario()
                        .multiply(java.math.BigDecimal.valueOf(item.getQuantidade())))
                .build();
    }
}
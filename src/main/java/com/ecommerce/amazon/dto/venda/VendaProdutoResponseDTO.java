package com.ecommerce.amazon.dto.venda;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Getter
@Setter
@ToString 
@EqualsAndHashCode
@Builder
public class VendaProdutoResponseDTO {

    private Long produtoId;
    private String nomeProduto;   
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal; 
}
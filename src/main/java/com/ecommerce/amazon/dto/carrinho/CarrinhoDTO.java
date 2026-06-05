package com.ecommerce.amazon.dto.carrinho;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CarrinhoDTO {

    private Long id;
    private Long usuarioId;
    private Integer quantidadeItens;
    private BigDecimal total;

}

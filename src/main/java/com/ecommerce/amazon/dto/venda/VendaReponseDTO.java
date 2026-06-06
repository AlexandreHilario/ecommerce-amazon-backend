package com.ecommerce.amazon.dto.venda;

import lombok.Data;

@Data  
public class VendaResponseDTO {

    private Long id;
    private Long usuarioId;
    private String nomeUsuario;   
    private BigDecimal valorTotal;
    private StatusVenda status;
    private LocalDateTime criadoEm;
    private List<VendaProdutoResponseDTO> itens; 
}
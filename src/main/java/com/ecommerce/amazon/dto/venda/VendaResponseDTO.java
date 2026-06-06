package com.ecommerce.amazon.dto.venda;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class VendaResponseDTO {
    private Long vendaId;
    private Long usuarioId;
    private String nomeUsuario;
    private BigDecimal valorTotal;
    private String status;
    private LocalDateTime criadoEm;

    private List<VendaItemResponseDTO> itens;
}
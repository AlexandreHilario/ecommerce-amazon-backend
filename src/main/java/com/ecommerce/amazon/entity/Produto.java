package com.ecommerce.amazon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos", indexes = @Index(name = "idx_produto_nome", columnList = "nome"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(columnDefinition = "text")
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false, columnDefinition = "int4 DEFAULT 0")
    @Builder.Default
    private Integer estoque = 0;

    @Column(columnDefinition = "bytea")
    private byte[] imagem;

    @Column(columnDefinition = "bool DEFAULT true")
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;
}

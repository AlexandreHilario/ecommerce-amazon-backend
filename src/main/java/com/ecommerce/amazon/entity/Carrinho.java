package com.ecommerce.amazon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "carrinho_produtos",
    uniqueConstraints = @UniqueConstraint(name = "uk_carrinho_produto", columnNames = {"carrinho_id", "produto_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrinhoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrinho_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cp_carrinho"))
    private Carrinho carrinho;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cp_produto"))
    private Produto produto;

    @Column(nullable = false, columnDefinition = "int4 DEFAULT 1")
    @Builder.Default
    private Integer quantidade = 1;
}
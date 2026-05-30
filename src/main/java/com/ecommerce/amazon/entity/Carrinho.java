package com.ecommerce.amazon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true,
                foreignKey = @ForeignKey(name = "fk_carrinho_usuario"))
    private Usuario usuario;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CarrinhoProduto> itens = new ArrayList<>();
}

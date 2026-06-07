package com.ecommerce.amazon.mapper;

import com.ecommerce.amazon.dto.carrinho.CarrinhoDTO;
import com.ecommerce.amazon.entity.Carrinho;
import com.ecommerce.amazon.entity.CarrinhoProduto;
import com.ecommerce.amazon.entity.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CarrinhoMapper {

    public Carrinho toEntity(Usuario usuario) {
        return Carrinho.builder()
                .usuario(usuario)
                .build();
    }

    public CarrinhoDTO toDTO(
            Carrinho carrinho,
            List<CarrinhoProduto> itens
    ) {
        BigDecimal total = BigDecimal.ZERO;

        for (CarrinhoProduto item : itens) {
            total = total.add(
                    item.getProduto()
                            .getPreco()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantidade()
                                    )
                            )
            );
        }

        return CarrinhoDTO.builder()
                .id(carrinho.getId())
                .usuarioId(carrinho.getUsuario().getId())
                .quantidadeItens(itens.size())
                .total(total)
                .build();
    }
}

package com.ecommerce.amazon.service;

import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import com.ecommerce.amazon.repository.ProdutoRepository;
import com.ecommerce.amazon.repository.UsuarioRepository;
import com.ecommerce.amazon.repository.VendaProdutoRepository;
import com.ecommerce.amazon.repository.VendaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public Venda buscarPorId(Long id) {

        return vendaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Venda não encontrada"));
    }

    public List<Venda> buscarHistorico(Long usuarioId) {

        return vendaRepository.findByUsuarioId(usuarioId);
    }

    public Venda atualizarStatus(Long vendaId,
                                 StatusVenda novoStatus) {

        Venda venda = buscarPorId(vendaId);

        venda.setStatus(novoStatus);

        return vendaRepository.save(venda);
    }
}
  

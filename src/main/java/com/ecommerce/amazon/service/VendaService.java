package com.ecommerce.amazon.service;

import com.ecommerce.amazon.repository.VendaRepository;
import com.ecommerce.amazon.repository.VendaProdutoRepository;
import com.ecommerce.amazon.repository.ProdutoRepository;
import com.ecommerce.amazon.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

}
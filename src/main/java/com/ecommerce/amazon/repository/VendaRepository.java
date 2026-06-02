package com.ecommerce.amazon.repository;

<<<<<<< HEAD
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByUsuarioId(Long usuarioId);
    List<Venda> findByUsuarioIdAndStatus(Long usuarioId, StatusVenda status);
    List<Venda> findByStatus(StatusVenda status);
=======
package com.ecommerce.amazon.repository;

import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.entity.Venda;
import com.ecommerce.amazon.entity.enums.StatusVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByUsuario(Usuario usuario);

    List<Venda> findByStatus(StatusVenda status);

    List<Venda> findByUsuarioAndStatus(Usuario usuario, StatusVenda status);
>>>>>>> a60b535 (salvando progresso atual)
}

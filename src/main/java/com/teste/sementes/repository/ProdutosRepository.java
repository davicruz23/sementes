package com.teste.sementes.repository;

import com.teste.sementes.domain.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    List<Produtos> findByUsuarioId(Long usuarioId);
}

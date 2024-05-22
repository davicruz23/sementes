package com.teste.sementes.repository;

import com.teste.sementes.domain.Usuario;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

public interface UsuarioRepository extends IGenericRepository<Usuario> {

    Optional<Usuario> findByUsuario(String usuario);
}

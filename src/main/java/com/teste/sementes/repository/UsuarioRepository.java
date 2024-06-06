package com.teste.sementes.repository;

import com.teste.sementes.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends IGenericRepository<Usuario>, JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    UserDetails findByLogin(String login);

    UserDetails frindByCpf(String cpf);

    Usuario findByCpf(String cpf);
}

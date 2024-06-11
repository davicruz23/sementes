package com.teste.sementes.repository;

import com.teste.sementes.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends IGenericRepository<Usuario>, JpaRepository<Usuario, Long> {


    UserDetails findByLogin(String login);

    UserDetails findByCpf(String cpf);

    boolean findByUsuario(String usuario);
}

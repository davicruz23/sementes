package com.teste.sementes.service;

import com.teste.sementes.domain.Endereco;
import com.teste.sementes.domain.Usuario;
import com.teste.sementes.repository.EnderecoRepository;
import com.teste.sementes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository> {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Usuario addEnderecoToUsuario(Long usuarioId, Endereco endereco) {
        // Recuperar o usuário pelo ID
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuarioId));

        // Associar o endereço ao usuário
        endereco.setUsuario(usuario);
        usuario.setEndereco(endereco);

        // Salvar o endereço
        enderecoRepository.save(endereco);
        return usuario;
    }
}

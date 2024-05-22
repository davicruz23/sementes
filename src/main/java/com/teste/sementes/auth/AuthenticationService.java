package com.teste.sementes.auth;

import lombok.RequiredArgsConstructor;
import com.teste.sementes.domain.Role;
import com.teste.sementes.domain.Usuario;
import com.teste.sementes.repository.UsuarioRepository;
import com.teste.sementes.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.teste.sementes.auth.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse register(RegisterRequest request){
        var user = Usuario.builder()
                .nomeCompleto(request.getNomeCompleto())
                .cpf(request.getCpf())
                .telefone(request.getTelefone())
                .usuario(request.getUsuario()) // Corrigido para usar o método getUsuario()
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(Role.USER)
                .build();

        repository.save(user);
        var token =  tokenService.generateToken(user).toString();
        return AuthenticationResponse.builder().token(token).build();
    }

}

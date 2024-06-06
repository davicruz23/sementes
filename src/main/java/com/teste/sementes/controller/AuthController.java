package com.teste.sementes.controller;

import com.teste.sementes.domain.AuthDto;
import com.teste.sementes.domain.LoginResponseDTO;
import com.teste.sementes.domain.RegisterDTO;
import com.teste.sementes.domain.Usuario;
import com.teste.sementes.infra.security.TokenService;
import com.teste.sementes.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDto data) {
        try {
            System.out.println("Received login request for user: " + data.usuario());

            // Verificação se o usuário existe
            Usuario usuario = (Usuario) repository.findByLogin(data.usuario());
            if (usuario == null) {
                System.out.println("User not found: " + data.usuario());
                return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid credentials", null));
            }

            // Verificação de senha manualmente
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(data.senha(), usuario.getSenha())) {
                System.out.println("Invalid password for user: " + data.usuario());
                return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid credentials", null));
            }

            System.out.println("User authenticated: " + data.usuario());

            // Autenticação
            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(data.usuario(), data.senha());

            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            System.out.println("Generating token for user: " + data.usuario());
            String token = tokenService.generateToken((Usuario) auth.getPrincipal());

            // Retornando a resposta com token e ID do usuário
            return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getId()));
        } catch (AuthenticationException e) {
            System.out.println("Authentication exception: " + e.getMessage());
            return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid credentials", null));
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            return ResponseEntity.status(500).body(new LoginResponseDTO("An unexpected error occurred", null));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        System.out.println("Received registration request for user: " + data.usuario());

        // Verifique se o usuário já existe pelo login
        if (this.repository.findByLogin(data.usuario()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Verifique se o CPF já existe
        if (this.repository.findByCpf(data.cpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Criptografa a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.usuario(), encryptedPassword, data.role());

        // Adicione os campos adicionais ao novo usuário
        newUser.setNomecompleto(data.nomecompleto());
        newUser.setCpf(data.cpf());
        newUser.setTelefone(data.telefone());

        this.repository.save(newUser);

        System.out.println("User registered successfully: " + data.usuario());

        return ResponseEntity.ok().build();
    }



}

package com.teste.sementes.controller;

import com.teste.sementes.auth.AuthenticationResponse;
import com.teste.sementes.auth.AuthenticationService;
import com.teste.sementes.auth.RegisterRequest;
import com.teste.sementes.config.LoginDTO;
import com.teste.sementes.repository.UsuarioRepository;
import com.teste.sementes.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService service;
    private final UsuarioRepository repository;

    @Autowired
    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, AuthenticationService service, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/login")
    public AuthenticationResponse token(@RequestBody LoginDTO loginDTO) {
        System.out.println("entrou no login");
        System.out.println(loginDTO.usuario());
        System.out.println(repository.findByUsuario(loginDTO.usuario()).orElseThrow());

        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.usuario(), loginDTO.senha())
                );

        UserDetails userDetails = repository.findByUsuario(loginDTO.usuario()).orElseThrow();
        return tokenService.generateToken(userDetails);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
}

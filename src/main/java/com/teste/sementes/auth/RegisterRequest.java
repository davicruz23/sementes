package com.teste.sementes.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String usuario;
    private String senha;

}
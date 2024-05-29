package com.teste.sementes.domain;

public record RegisterDTO(String nomecompleto, String cpf, String telefone, String usuario, String senha, Role role) {
}
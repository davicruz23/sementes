package com.teste.sementes.dto;

import com.teste.sementes.domain.Produtos;
import java.util.List;

public class UsuarioProdutoDto {
    private String nome;
    private List<Produtos> produtos;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }
}

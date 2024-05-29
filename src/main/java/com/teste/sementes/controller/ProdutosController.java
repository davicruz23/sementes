package com.teste.sementes.controller;

import com.teste.sementes.domain.Produtos;
import com.teste.sementes.service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @PostMapping("/{usuarioId}")
    public Produtos createProduto(@PathVariable Long usuarioId, @RequestBody Produtos produto) {
        return produtosService.save(produto, usuarioId);
    }

    @PutMapping("/{id}")
    public Produtos updateProduto(@PathVariable Long id, @RequestBody Produtos produto) {
        return produtosService.update(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable Long id) {
        produtosService.delete(id);
    }

    @GetMapping("/{id}")
    public Produtos getProdutoById(@PathVariable Long id) {
        return produtosService.findById(id);
    }

    @GetMapping("/{usuarioId}/produtos")
    public List<Produtos> getAllProdutosByUsuarioId(@PathVariable Long usuarioId) {
        return produtosService.findAllByUsuarioId(usuarioId);
    }
}

package com.teste.sementes.controller;

import com.teste.sementes.domain.Endereco;
import com.teste.sementes.domain.Usuario;
import com.teste.sementes.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;
    private final ModelMapper mapper;

    public UsuarioController(UsuarioService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Usuario.DtoResponse> list() {
        return service.list().stream()
                .map(usuario -> {
                    Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
                    response.generateLinks(usuario.getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Usuario.DtoResponse getById(@PathVariable Long id) {
        Usuario usuario = service.getById(id);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());
        return response;
    }

    @PutMapping("/{id}")
    public Usuario.DtoResponse update(@RequestBody Usuario.DtoRequest dtoRequest, @PathVariable Long id) {
        Usuario usuario = Usuario.DtoRequest.convertToEntity(dtoRequest, mapper);
        Usuario updatedUsuario = service.update(usuario, id);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(updatedUsuario, mapper);
        response.generateLinks(updatedUsuario.getId());
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/endereco")
    public ResponseEntity<Usuario.DtoResponse> addEnderecoToUsuario(@PathVariable Long id, @RequestBody Endereco.DtoRequest enderecoDto) {
        Endereco endereco = Endereco.DtoRequest.convertToEntity(enderecoDto, mapper);
        Usuario usuario = service.addEnderecoToUsuario(id, endereco);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());
        return ResponseEntity.ok(response);
    }
}

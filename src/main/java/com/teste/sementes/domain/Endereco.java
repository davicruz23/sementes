package com.teste.sementes.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teste.sementes.domain.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import com.teste.sementes.controller.EnderecoController;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Endereco extends AbstractEntity implements Serializable{

    String cep;
    String rua;
    String cidade;
    String estado;

    //1-1
    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Endereco endereco) {
            this.cep = endereco.cep;
            this.rua = endereco.rua;
            this.cidade = endereco.cidade;
            this.estado = endereco.estado;

        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Cep em branco")
        String cep;
        @NotBlank(message = "Rua em branco")
        String rua;
        @NotBlank(message = "Cidade em branco")
        String cidade;
        @NotBlank(message = "Estado em branco")
        String estado;

        public static Endereco convertToEntity(Endereco.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Endereco.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<Endereco.DtoResponse> {
        String cep;
        String cidade;
        String nCasa;

        public static Endereco.DtoResponse convertToDto(Endereco e, ModelMapper mapper) {
            return mapper.map(e, Endereco.DtoResponse.class);
        }

        public void generateLinks(Long id) {
            add(linkTo(EnderecoController.class).slash(id).withSelfRel());
            add(linkTo(EnderecoController.class).withRel("endereco"));
            add(linkTo(EnderecoController.class).slash(id).withRel("delete"));
        }
    }
}

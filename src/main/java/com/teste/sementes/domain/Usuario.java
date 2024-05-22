package com.teste.sementes.domain;

import com.teste.sementes.controller.UsuarioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor // Adicionando o construtor padrão
@Builder
@Entity
public class Usuario extends AbstractEntity implements UserDetails {

    private String nomeCompleto;
    @Column(unique = true)
    private String cpf;
    private String telefone;
    @Column(unique = true)
    private String usuario;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Role role;

    //1-1
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Endereco endereco;

    //1-N
    @OneToMany(mappedBy = "usuario", orphanRemoval=true)
    private List<Produtos> produtos = new ArrayList<>();

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Usuario usuario) {
            this.nomeCompleto = usuario.nomeCompleto;
            this.senha = usuario.senha;
            this.usuario = usuario.telefone;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Usuário com nome em branco")
        private String nomeCompleto;
        @NotBlank(message = "Cpf em branco")
        private String cpf;
        @NotBlank(message = "Telefone em branco")
        private String telefone;
        @NotBlank(message = "Usuario em branco")
        private String usuario;
        @NotBlank(message = "Senha em branco")
        private String senha;

        public static Usuario convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Usuario.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        private String nomeCompleto;
        private String cpf;
        private String telefone;
        private String usuario;
        private String senha;

        public static DtoResponse convertToDto(Usuario p, ModelMapper mapper) {
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id) {
            add(linkTo(UsuarioController.class).slash(id).withSelfRel());
            add(linkTo(UsuarioController.class).withRel("pessoas"));
            add(linkTo(UsuarioController.class).slash(id).withRel("delete"));
        }
    }
}

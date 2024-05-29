package com.teste.sementes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teste.sementes.controller.UsuarioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usuario extends AbstractEntity implements UserDetails {

    private String nomecompleto;
    @Column(unique = true)
    private String cpf;
    private String telefone;
    @Column(unique = true)
    private String usuario;
    private String senha;
    @Column(unique = true, nullable = false)
    private String login;

    private Role role;

    //1-1
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Endereco endereco;

    //1-N
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produtos> produtos;

    public Usuario(String login, String senha, Role role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Usuario login) {
            this.nomecompleto = login.nomecompleto;
            this.senha = login.senha;
            this.telefone = login.telefone;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.USER)
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha; // Corrigido aqui
    }

    @Override
    public String getUsername() {
        return this.login; // Corrigido aqui
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nomecompleto='" + nomecompleto + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", usuario='" + usuario + '\'' +
                ", role=" + role +
                '}';
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Usuário com nome em branco")
        private String nomecompleto;
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
        private String nomecompleto;
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

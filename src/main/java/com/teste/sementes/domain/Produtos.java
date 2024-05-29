package com.teste.sementes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
public class Produtos extends AbstractEntity implements UserDetails {

    @NotBlank
    String nome;
    @NotBlank
    String tipo;
    Integer quantidade;

    //1-N
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

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
    }

    @Data
    public static class DtoRequest {

        @NotBlank(message = "Digite o nome")
        String nome;
        @NotBlank(message = "Digite a tipo")
        String tipo;
        @NotBlank(message = "Digite a quantidade")
        Integer quantidade;
        Long usuarioId;

        public static Produtos convertToEntity(Produtos.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Produtos.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {

        String nome;
        String tipo;
        Integer quantidade;

        public static Produtos.DtoResponse convertToDto(Produtos s, ModelMapper mapper) {
            return mapper.map(s, Produtos.DtoResponse.class);
        }
    }
}

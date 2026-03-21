package sptech.school.CRUD.interfaces.dto.Usuario;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UsuarioDetalhesDto implements UserDetails {


    private final Long id;
    private final String nome;
    private final String email;
    private final String password;

    @Override
    public String getUsername(){return  email;}
    @Override
    public String getPassword(){return password;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){return null;}

    @Override
    public boolean isAccountNonExpired(){return true;}

    @Override
    public boolean isAccountNonLocked(){return true;}

    @Override
    public boolean isCredentialsNonExpired(){return true;}

    @Override
    public boolean isEnabled(){return true;}
}

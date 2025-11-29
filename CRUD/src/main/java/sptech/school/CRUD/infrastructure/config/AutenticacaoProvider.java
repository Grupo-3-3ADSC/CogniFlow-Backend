package sptech.school.CRUD.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.CRUD.application.service.usuario.AutenticacaoService;

@Builder
@AllArgsConstructor
public class AutenticacaoProvider implements AuthenticationProvider {

    private final AutenticacaoService usuarioAutorizacaoService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException{

        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

System.out.println("\n=== DEBUG AUTH PROVIDER ===");
        System.out.println("1. Username recebido do Front: " + username);
        System.out.println("2. Senha recebida do Front: " + password);        

UserDetails userDetails = this.usuarioAutorizacaoService.loadUserByUsername(username);

System.out.println("3. Usuário encontrado no Banco (Email): " + userDetails.getUsername());
        System.out.println("4. Hash da senha no Banco: " + userDetails.getPassword());

        if(this.passwordEncoder.matches(password,userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        else {
            throw new BadCredentialsException("Usuário ou senha inválidos...");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

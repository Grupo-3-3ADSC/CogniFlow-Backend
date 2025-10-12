package sptech.school.CRUD.application.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioDetalhesDto;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Lançando exceção UsernameNotFoundException!");
            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado", username));
        }
        UsuarioModel usuario = usuarioOpt.get();

        return UsuarioDetalhesDto.builder()
                .id(Long.valueOf(usuario.getId()))
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .build();
    }


}

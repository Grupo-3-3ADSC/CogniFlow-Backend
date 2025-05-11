package sptech.school.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.dto.UsuarioDetalhesDto;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(username);

        if(usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado", username));
        }
        UsuarioModel usuario = usuarioOpt.get();

        return new UsuarioDetalhesDto(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }
}

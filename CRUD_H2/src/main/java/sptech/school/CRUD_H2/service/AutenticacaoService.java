package sptech.school.CRUD_H2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sptech.school.CRUD_H2.Model.UsuarioModel;
import sptech.school.CRUD_H2.Repository.UsuarioRepository;
import sptech.school.CRUD_H2.dto.UsuarioDetalhesDto;

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

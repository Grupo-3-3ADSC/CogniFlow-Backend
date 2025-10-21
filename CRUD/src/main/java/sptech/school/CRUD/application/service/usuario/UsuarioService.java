package sptech.school.CRUD.application.service.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.infrastructure.config.GerenciadorTokenJwt;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.interfaces.dto.Usuario.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<UsuarioModel> getAllByAtivo() {
        return usuarioRepository.findByAtivoTrueComCargo();
    }

    public List<UsuarioModel> getAllByInativo() {
        return usuarioRepository.findByAtivoFalseComCargo();
    }

    public List<UsuarioFullDto> getAll() {

        List<UsuarioModel> usuarios = usuarioRepository.findAll();

        return UsuarioMapper.toListagemFullDto(usuarios);
    }

    public UsuarioModel getById(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Usuario de id %d não encontrado".formatted(id)));
    }

    public UsuarioTokenDto autenticar(UsuarioModel usuario){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getPassword()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        UsuarioModel usuarioAutenticado =
                usuarioRepository.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

                if(!usuarioAutenticado.getAtivo()){
                    throw new UsernameNotFoundException("Usuário inativo, por favor contatar " +
                            "um gestor para liberar acesso.");
                }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public UsuarioModel buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }

    public Optional<UsuarioDeleteDto> deletarUsuarios(Integer id) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        UsuarioModel usuario = usuarioOpt.get();

        usuarioRepository.delete(usuario);

        UsuarioDeleteDto dto = UsuarioDeleteDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .password(null)
                .build();

        return Optional.of(dto);
    }

    public void salvarResetToken(String email, String resetToken, String jti) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (!usuario.getAtivo()) {
            throw new IllegalStateException("Usuário inativo não pode receber token de reset");
        }

        // Extrai a expiração do token JWT
        LocalDateTime expiracao = gerenciadorTokenJwt.extrairExpiracao(resetToken);

        // Salva o token no banco
        usuario.setReset_token(resetToken);
        usuario.setReset_token_expira(expiracao);
        usuario.setUpdatedAt(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

}
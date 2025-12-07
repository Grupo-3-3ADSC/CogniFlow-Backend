package sptech.school.CRUD.application.service.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.infrastructure.config.GerenciadorTokenJwt;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.interfaces.dto.Usuario.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final StringRedisTemplate redis;
    private final PasswordEncoder passwordEncoder;

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

    public Optional<UsuarioModel> buscarPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioTokenDto autenticar(UsuarioModel usuario){

        // 1. Busca usuário (para garantir que existe e checar se está ativo depois)
        UsuarioModel usuarioAutenticado = usuarioRepository.findByEmail(usuario.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                );

        // 2. Monta o token de credenciais
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getPassword()
        );

        Authentication authentication;

        // 3. TENTA autenticar. Se a senha não bater, o Spring lança BadCredentialsException
        try {
            authentication = this.authenticationManager.authenticate(credentials);
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            // AQUI: Transformamos o erro técnico do Spring em uma mensagem amigável para o front (Erro 400)
            throw new RequisicaoInvalidaException("Senha incorreta, verifique suas credenciais.");
        }

        // 4. Verificação extra de inativo
        if(!usuarioAutenticado.getAtivo()){
            throw new UsernameNotFoundException("Usuário inativo, por favor contatar um gestor.");
        }

        // 5. Autentica no contexto e gera token
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
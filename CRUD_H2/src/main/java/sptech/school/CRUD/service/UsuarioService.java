package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.config.GerenciadorTokenJwt;
import sptech.school.CRUD.dto.UsuarioMapper;
import sptech.school.CRUD.dto.UsuarioTokenDto;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    public List<UsuarioModel> getAll() {
        return usuarioRepository.findByAtivoTrue();
    }

    public UsuarioModel getById(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new EntidadeNaoEncontrado("Usuario de id %d não encontrado".formatted(id)));
    }

    public UsuarioModel cadastrarUsuarioComum(UsuarioModel usuario) {
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
            throw new RuntimeException("Senha deve ter pelo menos 6 caracteres");
        }

        // Continua o cadastro normalmente
        CargoModel cargo = cargoRepository.findByNome("comum");
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(senhaCriptografada);
        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel cadastrarUsuarioGestor(UsuarioModel usuario) {
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
            throw new RuntimeException("Senha deve ter pelo menos 6 caracteres");
        }


        // Continua o cadastro normalmente
        CargoModel cargo = cargoRepository.findByNome("gestor");

        if (cargo == null) {
            return null;
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(senhaCriptografada);
        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel put(UsuarioModel usuarioParaAtualizar, int id) {

        if ((usuarioParaAtualizar.getNome() == null || usuarioParaAtualizar.getNome().trim().isEmpty()) &&
                (usuarioParaAtualizar.getEmail() == null || usuarioParaAtualizar.getEmail().trim().isEmpty())) {
            throw new RuntimeException("Nome e email não podem estar vazios");
        }

        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontrado("Usuario de id %d não encontrado".formatted(id)));

        if (usuarioParaAtualizar.getNome() == null || usuarioParaAtualizar.getNome().trim().isEmpty()) {
            usuarioParaAtualizar.setNome(usuarioExistente.getNome());
        }

        if (usuarioParaAtualizar.getEmail() == null || usuarioParaAtualizar.getEmail().trim().isEmpty()) {
            usuarioParaAtualizar.setEmail(usuarioExistente.getEmail());
        }

        if (usuarioRepository.existsById(id)) {
            usuarioParaAtualizar.setId(id);
            usuarioParaAtualizar.setUpdatedAt(LocalDateTime.now());
            UsuarioModel usuario = usuarioRepository.save(usuarioParaAtualizar);
            return usuario;
        }else {
            throw new EntidadeNaoEncontrado("Usuario de id %d não encontrado".formatted(id));
        }
    }

    public Optional<UsuarioModel> delete(int id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            usuario.get().setUpdatedAt(LocalDateTime.now());
            usuario.get().setAtivo(false);
            usuarioRepository.save(usuario.get());
            return usuario;
        }

        return Optional.empty();

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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

}

package sptech.school.CRUD.application.service.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.config.GerenciadorTokenJwt;
import sptech.school.CRUD.infrastructure.persistence.cargo.CargoRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioAtivoDto;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioMapper;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioPatchDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtualizarUsuarioService {

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CargoRepository cargoRepository;

    public UsuarioModel atualizarSenha(String email, String password, String resetToken) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (!usuario.getAtivo()) {
            throw new IllegalStateException("Usuário inativo não pode alterar senha");
        }

        if (!gerenciadorTokenJwt.isResetTokenValid(resetToken, email)) {
            throw new IllegalStateException("Token inválido ou expirado");
        }

        if (!resetToken.equals(usuario.getReset_token())) {
            throw new IllegalStateException("Token não corresponde ao registrado");
        }

        if (usuario.getReset_token_expira() == null ||
                usuario.getReset_token_expira().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirado");
        }

        String jti = gerenciadorTokenJwt.extractJti(resetToken);

        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setUpdatedAt(LocalDateTime.now());

        usuario.setReset_token(null);
        usuario.setReset_token_expira(null);

        return usuarioRepository.save(usuario);
    }

    public UsuarioAtivoDto desativarUsuario(Integer id, UsuarioAtivoDto dto){
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(id);

        if(usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        UsuarioModel usuario = usuarioOpt.get();

        if(dto.getAtivo() != null && !dto.getAtivo()){
            usuario.setAtivo(false);
        }
        else {
            usuario.setAtivo(true);
        }

        UsuarioModel usuarioAtualizado = usuarioRepository.save(usuario);

        return UsuarioMapper.toActiveDto(usuarioAtualizado);

    }

    public UsuarioModel put(UsuarioModel usuarioParaAtualizar, int id) {
        if (usuarioRepository.existsById(id)) {
            // Validações de dados obrigatórios
//            boolean nomeVazio = usuarioParaAtualizar.getNome() == null || usuarioParaAtualizar.getNome().trim().isEmpty();
//            boolean emailVazio = usuarioParaAtualizar.getEmail() == null || usuarioParaAtualizar.getEmail().trim().isEmpty();

            if (usuarioParaAtualizar == null) {
                throw new RequisicaoInvalidaException("O corpo da requisição está vazio.");
            }



            if (usuarioParaAtualizar.getNome() == null || usuarioParaAtualizar.getNome().trim().isEmpty()) {
                throw new RequisicaoInvalidaException("Nome não pode estar vazio");
            }
            if (usuarioParaAtualizar.getEmail() == null || usuarioParaAtualizar.getEmail().trim().isEmpty()) {
                throw new RequisicaoInvalidaException("Email não pode estar vazio");
            }

            if (usuarioRepository.existsByEmail(usuarioParaAtualizar.getEmail())) {
                throw new RequisicaoConflitanteException("Email já cadastrado.");
            }
            if (usuarioParaAtualizar.getPassword() != null) {
                if (usuarioParaAtualizar.getPassword().isBlank()) {
                    throw new RequisicaoInvalidaException("Senha não pode ser nulo ou vazio.");
                }
                if (usuarioParaAtualizar.getPassword().length() < 6) {
                    throw new RecursoNaoEncontradoException("Senha deve ter pelo menos 6 caracteres.");
                }
            }


            usuarioParaAtualizar.setId(id);
            usuarioParaAtualizar.setUpdatedAt(LocalDateTime.now());
            UsuarioModel usuario = usuarioRepository.save(usuarioParaAtualizar);
            return usuario;
        }else {
            throw new RecursoNaoEncontradoException("Usuario de id %d não encontrado".formatted(id));
        }
    }

    public UsuarioModel patch(int id, UsuarioPatchDto dto){
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(id);

        if(usuarioOpt.isEmpty()){
            return null;
        }

        UsuarioModel usuario = usuarioOpt.get();

        if(dto.getNome() != null){
            usuario.setNome(dto.getNome());
        }

        if(dto.getEmail() != null){
            usuario.setEmail(dto.getEmail());
        }

        if(dto.getCargo() != null && dto.getCargo().getId() != null){
            CargoModel cargo = cargoRepository.findById(dto.getCargo().getId())
                    .orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
            usuario.setCargo(cargo);
        }

        return usuarioRepository.save(usuario);
    }
}

package sptech.school.CRUD.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.repository.CargoRepository;
import sptech.school.CRUD.domain.repository.UsuarioRepository;
import sptech.school.CRUD.infrastructure.config.GerenciadorTokenJwt;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;

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

    public UsuarioModel cadastrarUsuarioComum(UsuarioModel usuario) {

        if (usuario == null) {
            throw new RequisicaoInvalidaException("O corpo da requisição está vazio.");
        }

        if (usuario.getNome().length() <= 3){
            throw new RequisicaoInvalidaException("Senha deve ter pelo menos 3 caracteres");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RequisicaoConflitanteException("Email já cadastrado.");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new RequisicaoInvalidaException("Senha não pode ser nulo ou vazio.");
        }
        if (usuario.getPassword().length() < 6) {
            throw new RequisicaoInvalidaException("Senha deve ter pelo menos 6 caracteres.");
        }

        CargoModel cargo = cargoRepository.findByNome("comum");

        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(senhaCriptografada);
        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel cadastrarUsuarioGestor(UsuarioModel usuario) {


        if (usuario == null) {
            throw new RequisicaoInvalidaException("O corpo da requisição está vazio.");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RequisicaoConflitanteException("Email já cadastrado.");
        }
        if (usuario.getNome().length() <= 3){
            throw new RequisicaoInvalidaException("O nome deve ter pelo menos 3 caracteres");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new RequisicaoInvalidaException("Senha não pode ser nulo ou vazio.");
        }

        if (usuario.getPassword().length() < 6) {
            throw new RequisicaoInvalidaException("Senha deve ter pelo menos 6 caracteres.");
        }

        CargoModel cargo = cargoRepository.findByNome("gestor");

        if(cargo == null) {
            return null;
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(senhaCriptografada);
        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
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

    public Boolean uploadFoto(Integer id, MultipartFile file){
        if(file == null || file.isEmpty()){
            return false;
        }

        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()){
            UsuarioModel usuario = usuarioOpt.get();
            try {
                usuario.setFoto(file.getBytes());
            }catch (Exception e){
                return false;
            }
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    public Optional<byte[]> buscarFoto(Integer id){
        return usuarioRepository.findById(id)
                .map(UsuarioModel::getFoto);
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

    public UsuarioModel atualizarSenha(Integer id, String password){
        // Validação de entrada
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode estar vazia");
        }

        // Buscar usuário
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + id));

        // Verificar se usuário está ativo
        if (!usuario.getAtivo()) {
            throw new IllegalStateException("Não é possível atualizar senha de usuário inativo");
        }

        // Criptografar a senha (MUITO IMPORTANTE!)
        String senhaCriptografada = passwordEncoder.encode(password);

        // Atualizar senha e timestamp
        usuario.setPassword(senhaCriptografada);
        usuario.setUpdatedAt(LocalDateTime.now());

        // Salvar no banco
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
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



}
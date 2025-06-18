package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.config.GerenciadorTokenJwt;
import sptech.school.CRUD.dto.Usuario.UsuarioMapper;
import sptech.school.CRUD.dto.Usuario.UsuarioPatchDto;
import sptech.school.CRUD.dto.Usuario.UsuarioTokenDto;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<UsuarioModel> getAll() {
        return usuarioRepository.findByAtivoTrue();
    }

    public UsuarioModel getById(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new EntidadeNaoEncontrado("Usuario de id %d não encontrado".formatted(id)));
    }

    public UsuarioModel cadastrarUsuarioComum(UsuarioModel usuario) {
        CargoModel cargo = cargoRepository.findByNome("comum");

        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(senhaCriptografada);
        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel cadastrarUsuarioGestor(UsuarioModel usuario) {

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
            usuarioParaAtualizar.setId(id);
            usuarioParaAtualizar.setUpdatedAt(LocalDateTime.now());
            UsuarioModel usuario = usuarioRepository.save(usuarioParaAtualizar);
            return usuario;
        }else {
            throw new EntidadeNaoEncontrado("Usuario de id %d não encontrado".formatted(id));
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

        if(dto.getCargo() != null){
            usuario.setCargo(dto.getCargo());
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioModel> delete(int id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        usuario.get().setUpdatedAt(LocalDateTime.now());
        usuario.get().setAtivo(false);
        usuarioRepository.save(usuario.get());
        return usuario;
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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

}
package sptech.school.CRUD.application.service.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.cargo.CargoRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;

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
}

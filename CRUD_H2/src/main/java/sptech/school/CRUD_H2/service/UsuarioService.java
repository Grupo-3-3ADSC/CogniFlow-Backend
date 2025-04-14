package sptech.school.CRUD_H2.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Model.UsuarioModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;
import sptech.school.CRUD_H2.Repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;


    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public List<UsuarioModel> getAll() {
        return usuarioRepository.findByAtivoTrue();
    }

    public Optional<UsuarioModel> getById(int id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel cadastrarUsuarioComum(UsuarioModel usuario) {
        CargoModel cargo = cargoRepository.findByNome("comum");

        if(cargo == null) {
            return null;
        }

        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel cadastrarUsuarioGestor(UsuarioModel usuario) {

        CargoModel cargo = cargoRepository.findByNome("gestor");

        if(cargo == null) {
            return null;
        }

        usuario.setCargo(cargo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel put(UsuarioModel usuarioParaAtualizar, int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioParaAtualizar.setId(id);
            usuarioParaAtualizar.setUpdatedAt(LocalDateTime.now());

            UsuarioModel usuario = usuarioRepository.save(usuarioParaAtualizar);
            return usuario;
        }

        return null;
    }

    public Optional<UsuarioModel> delete(int id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return Optional.empty();
        }

        usuario.get().setUpdatedAt(LocalDateTime.now());
        usuario.get().setAtivo(false);
        usuarioRepository.save(usuario.get());

        return usuario;
    }


}

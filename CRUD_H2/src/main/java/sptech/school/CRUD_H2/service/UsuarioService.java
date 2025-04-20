package sptech.school.CRUD_H2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Model.UsuarioModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;
import sptech.school.CRUD_H2.Repository.UsuarioRepository;
import sptech.school.CRUD_H2.exception.EntidadeNaoEncontrado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;


    public List<UsuarioModel> getAll() {
        return usuarioRepository.findByAtivoTrue();
    }

    public UsuarioModel getById(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuario de id %d não encontrado".formatted(id)));
    }

    public UsuarioModel cadastrarUsuarioComum(UsuarioModel usuario) {
        CargoModel cargo = cargoRepository.findByNome("comum");

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
        }else {
            throw new EntidadeNaoEncontrado("Usuario de id %d não encontrado".formatted(id));
        }
    }

    public Optional<UsuarioModel> delete(int id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        usuario.get().setUpdatedAt(LocalDateTime.now());
        usuario.get().setAtivo(false);
        usuarioRepository.save(usuario.get());
        return usuario;


    }


}

package sptech.school.CRUD_H2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;
import sptech.school.CRUD_H2.Repository.UsuarioRepository;
import sptech.school.CRUD_H2.Model.UsuarioModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar() {

        List<UsuarioModel> usuariosAtivos = usuarioRepository.findByAtivoTrue();

        if(usuariosAtivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(usuariosAtivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getById(@PathVariable Integer id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrarComum(
            @RequestBody UsuarioModel usuarioParaCadastro
    ) {
        CargoModel cargo = cargoRepository.findByNome("comum");

        if(cargo == null) {
            return ResponseEntity.status(400).build();
        }

        usuarioParaCadastro.setCargo(cargo);
        UsuarioModel usuarioSalvo = usuarioRepository.save(usuarioParaCadastro);

        return ResponseEntity.status(201).body(usuarioSalvo);
    }

    @PostMapping("/gestor")
    public ResponseEntity<UsuarioModel> cadastrarGestor(
            @RequestBody UsuarioModel usuarioParaCadastro
    ) {
        CargoModel cargo = cargoRepository.findByNome("gestor");

        if(cargo == null) {
            return ResponseEntity.status(400).build();
        }

        usuarioParaCadastro.setCargo(cargo);
        UsuarioModel usuarioSalvo = usuarioRepository.save(usuarioParaCadastro);

        return ResponseEntity.status(201).body(usuarioSalvo);
    }

    @PutMapping("/{id}")
        public ResponseEntity<UsuarioModel> atualizar(
            @PathVariable Integer id,
            @RequestBody UsuarioModel usuarioParaAtualizar
         )
        {
        if (usuarioRepository.existsById(id)) {
            usuarioParaAtualizar.setId(id);
            usuarioParaAtualizar.setUpdatedAt(LocalDateTime.now());

            UsuarioModel usuario = usuarioRepository.save(usuarioParaAtualizar);
            return ResponseEntity.ok().body(usuario);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioModel> deletar(
            @PathVariable Integer id
    ){
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()){
            usuario.get().setUpdatedAt(LocalDateTime.now());
            usuario.get().setAtivo(false);
            usuarioRepository.save(usuario.get());

            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }

}

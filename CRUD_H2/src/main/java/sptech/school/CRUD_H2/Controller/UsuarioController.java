package sptech.school.CRUD_H2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar() {

        List<UsuarioModel> usuariosAtivos = usuarioRepository.findByAtivoTrue();

        if(usuariosAtivos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(usuariosAtivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrar(
            @RequestBody UsuarioModel usuarioParaCadastro
    ) {
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

        return ResponseEntity.notFound().build();
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

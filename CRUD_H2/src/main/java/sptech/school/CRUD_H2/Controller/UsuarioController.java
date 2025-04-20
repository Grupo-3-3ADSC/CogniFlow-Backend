package sptech.school.CRUD_H2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;
import sptech.school.CRUD_H2.Repository.UsuarioRepository;
import sptech.school.CRUD_H2.Model.UsuarioModel;
import sptech.school.CRUD_H2.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar() {

        List<UsuarioModel> usuariosAtivos = usuarioService.getAll();

        if(usuariosAtivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(usuariosAtivos);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getById(@PathVariable Integer id) {
        return ResponseEntity.ok((usuarioService.getById(id)));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrarComum(
            @RequestBody UsuarioModel usuarioParaCadastro
    ) {
        UsuarioModel usuario = usuarioService.cadastrarUsuarioComum(usuarioParaCadastro);

        if(usuario == null) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body(usuario);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/gestor")
    public ResponseEntity<UsuarioModel> cadastrarGestor(
            @RequestBody UsuarioModel usuarioParaCadastro
    ) {
        UsuarioModel usuario = usuarioService.cadastrarUsuarioGestor(usuarioParaCadastro);

        if(usuario == null) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body(usuario);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/{id}")
        public ResponseEntity<UsuarioModel> atualizar(
            @PathVariable Integer id,
            @RequestBody UsuarioModel usuarioParaAtualizar
         )
        {
            UsuarioModel usuario = usuarioService.put(usuarioParaAtualizar, id);

            if(usuario == null) {
                return ResponseEntity.notFound().build();
            }

        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioModel> deletar(
            @PathVariable Integer id
    ){
        Optional<UsuarioModel> usuario = usuarioService.delete(id);

        if (usuario.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();

    }

}

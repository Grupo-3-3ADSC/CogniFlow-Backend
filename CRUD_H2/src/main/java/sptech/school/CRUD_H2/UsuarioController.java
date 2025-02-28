package sptech.school.CRUD_H2;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {

        List<Usuario> usuariosAtivos = usuarioRepository.findByAtivoTrue();

        if(usuariosAtivos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(usuariosAtivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(
            @RequestBody Usuario usuarioParaCadastro
    ) {
        Usuario usuarioSalvo = usuarioRepository.save(usuarioParaCadastro);
        return ResponseEntity.status(201).body(usuarioSalvo);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Usuario> atualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuarioParaAtualizar
         )
        {
        if (usuarioRepository.existsById(id)) {
            usuarioParaAtualizar.setId(id);
            Usuario usuario = usuarioRepository.save(usuarioParaAtualizar);
            return ResponseEntity.ok().body(usuario);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletar(
            @PathVariable Integer id
    ){
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()){

            usuario.get().setAtivo(false);
            usuarioRepository.save(usuario.get());
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }

}

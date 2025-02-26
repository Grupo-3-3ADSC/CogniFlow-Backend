package sptech.school.CRUD_H2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario cadastrar(
            @RequestBody Usuario usuarioParaCadastro
    ) {
        Usuario usuarioSalvo = usuarioRepository.save(usuarioParaCadastro);
        return usuarioSalvo;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @PutMapping("/{id}")
        public Usuario atualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuarioParaAtualizar
         )
        {
        if (usuarioRepository.existsById(id)) {
            usuarioParaAtualizar.setId(id);
            return usuarioRepository.save(usuarioParaAtualizar);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public String deletar(
            @PathVariable Integer id
    ){
        if (usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return "removido com sucesso";
        }
        return "passe o id correto";
    }

}

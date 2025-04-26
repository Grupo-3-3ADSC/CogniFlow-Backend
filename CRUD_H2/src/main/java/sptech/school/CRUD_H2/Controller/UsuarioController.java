package sptech.school.CRUD_H2.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;
import sptech.school.CRUD_H2.Repository.UsuarioRepository;
import sptech.school.CRUD_H2.Model.UsuarioModel;
import sptech.school.CRUD_H2.dto.UsuarioCadastroDto;
import sptech.school.CRUD_H2.dto.UsuarioLoginDto;
import sptech.school.CRUD_H2.dto.UsuarioMapper;
import sptech.school.CRUD_H2.dto.UsuarioTokenDto;
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


    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioModel>> listar() {

        List<UsuarioModel> usuariosAtivos = usuarioService.getAll();

        if(usuariosAtivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(usuariosAtivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getById(@PathVariable Integer id) {
        return ResponseEntity.ok((usuarioService.getById(id)));
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> cadastrarComum(
            @RequestBody @Valid UsuarioCadastroDto usuarioParaCadastro
    ) {
       final UsuarioModel usuario = UsuarioMapper.of(usuarioParaCadastro);

       this.usuarioService.cadastrarUsuarioComum(usuario);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/gestor")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> cadastrarGestor(
            @RequestBody @Valid UsuarioCadastroDto usuarioParaCadastro
    ) {
        final UsuarioModel usuario = UsuarioMapper.of(usuarioParaCadastro);

        this.usuarioService.cadastrarUsuarioGestor(usuario);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){

        final UsuarioModel usuario = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

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

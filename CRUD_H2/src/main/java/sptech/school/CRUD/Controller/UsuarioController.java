package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;
import sptech.school.CRUD.dto.UsuarioCadastroDto;
import sptech.school.CRUD.dto.UsuarioLoginDto;
import sptech.school.CRUD.dto.UsuarioMapper;
import sptech.school.CRUD.dto.UsuarioTokenDto;
import sptech.school.CRUD.service.UsuarioService;

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
    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })

    public ResponseEntity<UsuarioModel> getById(@PathVariable Integer id) {
        try {
            UsuarioModel usuario = usuarioService.getById(id);
            return ResponseEntity.ok(usuario);
        } catch (EntidadeNaoEncontrado ex) {

            System.out.println("Erro ao buscar usuário: " + ex.getMessage());
            throw ex;
        }
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

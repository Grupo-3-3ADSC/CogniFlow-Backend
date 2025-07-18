package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.dto.Usuario.*;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;
import sptech.school.CRUD.service.UsuarioService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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


//    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioModel> getById(@PathVariable Integer id) {
        UsuarioModel usuario = usuarioService.getById(id);
        if (usuario != null){

            return ResponseEntity.ok(usuario);
        } else {
            throw new RuntimeException("usuario nao encontrado");
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
    @SecurityRequirement(name = "Bearer")
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

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioModel> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody UsuarioPatchDto campos
            ){
        UsuarioModel usuario = usuarioService.patch(id,campos);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioModel> deletar(
            @PathVariable Integer id
    ){
        Optional<UsuarioModel> usuario = usuarioService.delete(id);

        if (usuario.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}/upload-foto")
    public ResponseEntity<UsuarioModel> postarFoto (
            @PathVariable Integer id,
            @RequestParam("foto")MultipartFile file
            ) {
        Boolean sucesso = usuarioService.uploadFoto(id, file);

        if(sucesso){
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();

    }

    @GetMapping("/{id}/foto")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<byte[]> getFoto(@PathVariable Integer id) {
        return usuarioService.buscarFoto(id)

                .map(foto -> ResponseEntity.ok()
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(foto))
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}/senha") // Adicione o path completo
    public ResponseEntity<Void> atualizarSenha(
            @PathVariable Integer id, // Corrigido: Integer em vez de String
            @RequestBody @Valid UsuarioSenhaAtualizada request // DTO para receber a senha
    ){
        usuarioService.atualizarSenha(id, request.getPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-por-email/{email}")
    public ResponseEntity<UsuarioModel> buscarPorEmail(@PathVariable String email) {
        UsuarioModel usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }
}

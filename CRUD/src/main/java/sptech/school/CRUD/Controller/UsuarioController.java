package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.config.GerenciadorTokenJwt;
import sptech.school.CRUD.dto.Usuario.*;
import sptech.school.CRUD.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.service.UsuarioService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Tag(name = "Usuario", description = "Endpoints de Usuario")
@RestController
@RequestMapping("/usuarios")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Entidade relacionada não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito de dados (ex: rastreabilidade duplicada)")
})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listarAtivos")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioListagemDto>> listarAtivos() {
        List<UsuarioModel> usuariosAtivos = usuarioService.getAllByAtivo();
        List<UsuarioListagemDto> lista = UsuarioMapper.toListagemDtos(usuariosAtivos);
        return ResponseEntity.ok(lista); // nunca retorna 204, sempre []
    }

    @GetMapping("/listarInativos")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioListagemDto>> listarInativos() {
        List<UsuarioModel> usuarios = usuarioService.getAllByInativo();
        List<UsuarioListagemDto> dtos = UsuarioMapper.toListagemDtos(usuarios);
        return ResponseEntity.ok(dtos); // idem aqui
    }

    @GetMapping("/listarTodos")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioFullDto>> listarTodos() {
        List<UsuarioFullDto> usuarios = usuarioService.getAll();
        return ResponseEntity.ok(usuarios); // idem
    }


    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })


    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioListagemDto> getById(@PathVariable Integer id) {
        UsuarioModel usuario = usuarioService.getById(id);

        if (usuario != null){
            return ResponseEntity.ok(UsuarioMapper.toListagemDto(usuario));
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

    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto){

        final UsuarioModel usuario = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioAtualizadoDto> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid UsuarioAtualizadoDto usuarioParaAtualizar
    )
    {
        UsuarioModel usuario = usuarioService.put(UsuarioMapper.toEntity(usuarioParaAtualizar, id), id);

        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody UsuarioPatchDto campos
            ){
        UsuarioModel usuario = usuarioService.patch(id,campos);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-foto")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> postarFoto (
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

    @PutMapping("/{email}/senha")
    public ResponseEntity<Void> atualizarSenha(
            @PathVariable String email,
            @RequestBody @Valid UsuarioSenhaAtualizada request, // DTO para receber a senha
            @RequestHeader(value = "Authorization", required = false) String resetTokenHeader
    ){
        if (resetTokenHeader == null || !resetTokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String resetToken = resetTokenHeader.substring(7); // remove "Bearer "
        usuarioService.atualizarSenha(email, request.getPassword(), resetToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-por-email/{email}")

    public ResponseEntity<UsuarioListagemDto> buscarPorEmail(@PathVariable String email) {
        UsuarioModel usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(UsuarioMapper.toEmailDto(usuario));

    }

    @PatchMapping("/desativarUsuario/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioAtivoDto> desativarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioAtivoDto dto
    ){

        UsuarioAtivoDto usuario = usuarioService.desativarUsuario(id,dto);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioDeleteDto> deletarUsuario(@PathVariable Integer id) {
        Optional<UsuarioDeleteDto> dto = usuarioService.deletarUsuarios(id);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

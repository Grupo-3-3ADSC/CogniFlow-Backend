package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.application.service.estoque.AtualizarEstoqueService;
import sptech.school.CRUD.application.service.estoque.EstoqueService;
import sptech.school.CRUD.application.service.estoque.RetirarEstoqueService;
import sptech.school.CRUD.interfaces.dto.Estoque.*;

import java.util.List;

@Tag(name = "Estoque", description = "Endpoints de Estoque")
@RequiredArgsConstructor
@RestController
@RequestMapping("/estoque")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Entidade relacionada não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito de dados (ex: rastreabilidade duplicada)")
})

public class EstoqueController {

    @Autowired
    private final EstoqueService estoqueService;
    @Autowired
    private final RetirarEstoqueService retirarEstoqueService;
    @Autowired
    private final AtualizarEstoqueService atualizarService;

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoque() {
            return ResponseEntity.ok(estoqueService.buscarEstoque());

    }

    @PostMapping("/adicionar")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> adicionarEstoque(@RequestBody @Valid AtualizarEstoqueDto dto) {
        EstoqueListagemDto resposta = atualizarService.atualizarEstoque(dto);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/retirar")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> retirarEstoque(@RequestBody @Valid RetirarEstoqueDto dto) {
        EstoqueListagemDto resposta = retirarEstoqueService.retirarEstoque(dto);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/materiais/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> buscarMaterialPorId(@PathVariable int id) {
        return ResponseEntity.ok(estoqueService.buscarMaterialPorId(id));
    }

    @PatchMapping("/atualizarInfo")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> atualizarInfo(@RequestBody @Valid AtualizarInfoEstoqueDto dto){
        EstoqueListagemDto resposta = atualizarService.atualizarInfo(dto);
        return ResponseEntity.ok(resposta);
    }

}

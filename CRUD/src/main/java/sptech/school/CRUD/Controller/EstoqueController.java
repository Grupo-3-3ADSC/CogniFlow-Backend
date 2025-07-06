package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.dto.Estoque.*;
import sptech.school.CRUD.service.EstoqueService;

import java.util.List;
import java.util.stream.Collectors;
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


    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoque() {
            return ResponseEntity.ok(estoqueService.buscarEstoque());

    }

    @PostMapping("/adicionar")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> adicionarEstoque(@RequestBody @Valid AtualizarEstoqueDto dto) {
        EstoqueListagemDto resposta = estoqueService.atualizarEstoque(dto);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/retirar")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> retirarEstoque(@RequestBody @Valid RetirarEstoqueDto dto) {
        EstoqueListagemDto resposta = estoqueService.retirarEstoque(dto);
        return ResponseEntity.ok(resposta);
    }

}

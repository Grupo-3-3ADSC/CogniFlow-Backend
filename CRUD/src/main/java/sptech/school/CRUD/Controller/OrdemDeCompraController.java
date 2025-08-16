package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.dto.OrdemDeCompra.ListagemOrdemDeCompra;
import sptech.school.CRUD.dto.OrdemDeCompra.MudarQuantidadeAtualDto;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraMapper;
import sptech.school.CRUD.service.OrdemDeCompraService;

import java.util.List;
@Tag(name = "Ordem de Compra", description = "Endpoints de Ordem de Compra")
@RestController
@RequestMapping("/ordemDeCompra")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Entidade relacionada não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito de dados (ex: rastreabilidade duplicada)")
})
@RequiredArgsConstructor
public class OrdemDeCompraController {

    private final OrdemDeCompraService ordemDeCompraService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ListagemOrdemDeCompra> cadastrarOrdemDeCompra(@Valid @RequestBody OrdemDeCompraCadastroDto ordemDeCompra){

        OrdemDeCompraModel model = ordemDeCompraService.cadastroOrdemDeCompra(ordemDeCompra);
        ListagemOrdemDeCompra resposta = OrdemDeCompraMapper.toListagemDto(model);
        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> listarOrdemDeCompra() {
        return ResponseEntity.ok(ordemDeCompraService.getAll());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ListagemOrdemDeCompra> buscarPorId(@PathVariable Integer id) {
        OrdemDeCompraModel ordem = ordemDeCompraService.getById(id);
        return ResponseEntity.ok(OrdemDeCompraMapper.toListagemDto(ordem));
    }
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ListagemOrdemDeCompra> mudarQuantidadeAtual(@PathVariable Integer id, @Valid @RequestBody MudarQuantidadeAtualDto dto){

        OrdemDeCompraModel ordem = ordemDeCompraService.mudarQuantidadeAtual(id, dto);
        return  ResponseEntity.ok(OrdemDeCompraMapper.toListagemDto(ordem));
    }
}

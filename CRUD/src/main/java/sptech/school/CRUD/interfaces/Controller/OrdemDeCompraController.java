package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.application.service.ordemDeCompra.OrdemDeCompraService;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.*;

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
    public ResponseEntity<List<OrdemDeCompraModel>> cadastrarMultiplasOrdens(@RequestBody List<OrdemDeCompraCadastroDto> dtos) {
        List<OrdemDeCompraModel> ordensCadastradas = ordemDeCompraService.cadastrarMultiplasOrdens(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordensCadastradas);
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

    @GetMapping("/buscar/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ListagemOrdemDeCompra> buscarPorIdDto(@PathVariable Integer id) {
        ListagemOrdemDeCompra ordem = ordemDeCompraService.buscarPorIdDto(id);
        return ResponseEntity.ok(ordem);
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ListagemOrdemDeCompra> mudarQuantidadeAtual(@PathVariable Integer id, @Valid @RequestBody MudarQuantidadeAtualDto dto){

        OrdemDeCompraModel ordem = ordemDeCompraService.mudarQuantidadeAtual(id, dto);
        return  ResponseEntity.ok(OrdemDeCompraMapper.toListagemDto(ordem));
    }

    @GetMapping("/paginados")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PaginacaoHistoricoOrdemDeCompraDTO> getOrdemDeCompraPaginada(
            @RequestParam(defaultValue = "1") Integer pagina,
            @RequestParam(defaultValue = "6") Integer tamanho
    ){
        PaginacaoHistoricoOrdemDeCompraDTO ordens = ordemDeCompraService.ordemDeCompraPaginada(pagina, tamanho);

        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/relatorioFornecedor/{fornecedorId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> getRelatorioFornecedor(
            @PathVariable Integer fornecedorId,
            @RequestParam Integer ano
    ) {
        List<ListagemOrdemDeCompra> ordens = ordemDeCompraService.getRelatorioFornecedor(fornecedorId, ano);

        if (ordens.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(ordens); // 200
    }

    @GetMapping("/material/{estoqueId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> listarPorMaterial(
            @PathVariable Integer estoqueId,
            @RequestParam(required = false) Integer ano
    ) {
        List<ListagemOrdemDeCompra> ordens = ordemDeCompraService.getByMaterial(estoqueId, ano);
        return ResponseEntity.ok(ordens);
    }
}

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
import sptech.school.CRUD.application.service.ItemOrdemDeCompra.CadastrarMultiplasOrdensService;
import sptech.school.CRUD.application.service.notificacao.NotificationService;
import sptech.school.CRUD.application.service.ordemDeCompra.CadastrarOrdemDeCompraService;
import sptech.school.CRUD.application.service.ordemDeCompra.MudarQuantidadeAtualService;
import sptech.school.CRUD.application.service.ordemDeCompra.PaginacaoOrdemDeCompraService;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.application.service.ordemDeCompra.OrdemDeCompraService;
import sptech.school.CRUD.infrastructure.adapter.Rabbit.RabbitProducer;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final CadastrarOrdemDeCompraService cadastroService;
    private final PaginacaoOrdemDeCompraService paginacaoService;
    private final RabbitProducer rabbitProducer;
    private final MudarQuantidadeAtualService mudarQuantidadeService;
    private final CadastrarMultiplasOrdensService cadastrarMultiplasOrdensService;
    private final NotificationService notificationService;


    // CORRIGIDO: Remover o caminho duplicado
    @PostMapping("/multiplas-ordens")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> cadastrarOrdemDeCompra(
            @Valid @RequestBody List<OrdemDeCompraCadastroDto> cadastroDtos) {

        List<ListagemOrdemDeCompra> respostas = cadastroDtos.stream()
                .map(dto -> cadastroService.cadastroOrdemDeCompra(dto))
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());

        // Opcional: Adicionar notificações

//        respostas.forEach(resposta -> {
//            notificationService.notificar(
//                "ordem_compra",
//                "CRIADO",
//                String.valueOf(resposta.getId()),
//                "Ordem de compra criada com sucesso!",
//                "Nova Ordem de Compra Criada",
//                "Uma nova ordem de compra foi registrada:\n\nID: " + resposta.getId() +
//                "\nFornecedor: " + resposta.getNomeFornecedor(),
//                "isaiasoliveirabjj@gmail.com"
//            );
//        });


        return ResponseEntity.status(HttpStatus.CREATED).body(respostas);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> listarTodas() {
        List<ListagemOrdemDeCompra> ordens = ordemDeCompraService.listarTodas();
        if (ordens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordens);
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
    public ResponseEntity<ListagemOrdemDeCompra> mudarQuantidadeAtual(
            @PathVariable Integer id,
            @Valid @RequestBody MudarQuantidadeAtualDto dto) {

        OrdemDeCompraModel ordem = mudarQuantidadeService.mudarQuantidadeAtual(id, dto);
        return ResponseEntity.ok(OrdemDeCompraMapper.toListagemDto(ordem));
    }

    @GetMapping("/paginados")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PaginacaoHistoricoOrdemDeCompraDTO> getOrdemDeCompraPaginada(
            @RequestParam(defaultValue = "1") Integer pagina,
            @RequestParam(defaultValue = "6") Integer tamanho) {

        PaginacaoHistoricoOrdemDeCompraDTO ordens = paginacaoService.ordemDeCompraPaginada(pagina, tamanho);
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/relatorioFornecedor/{fornecedorId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> getRelatorioFornecedor(
            @PathVariable Integer fornecedorId,
            @RequestParam Integer ano) {

        List<ListagemOrdemDeCompra> ordens = ordemDeCompraService.getRelatorioFornecedor(fornecedorId, ano);

        if (ordens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/material/{estoqueId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> listarPorMaterial(
            @PathVariable Integer estoqueId,
            @RequestParam(required = false) Integer ano) {

        List<ListagemOrdemDeCompra> ordens = ordemDeCompraService.getByMaterial(estoqueId, ano);
        return ResponseEntity.ok(ordens);
    }
}
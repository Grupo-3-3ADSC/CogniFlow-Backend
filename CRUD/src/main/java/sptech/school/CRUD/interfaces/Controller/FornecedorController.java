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
import sptech.school.CRUD.application.service.fornecedor.CadastroFornecedorService;
import sptech.school.CRUD.application.service.fornecedor.PaginacaoFornecedorService;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.interfaces.dto.Fornecedor.PaginacaoFornecedorDTO;
import sptech.school.CRUD.application.service.fornecedor.FornecedorService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Fornecedor", description = "Endpoints de Fornecedor")
@RestController
@RequestMapping("/fornecedores")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Entidade relacionada não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito de dados (ex: rastreabilidade duplicada)")
})

public class FornecedorController {

    private final FornecedorService fornecedorService;
    private final CadastroFornecedorService cadastroService;
    private final PaginacaoFornecedorService paginacaoService;

    public FornecedorController(FornecedorService fornecedorService,
                                CadastroFornecedorService cadastroService,
                                PaginacaoFornecedorService paginacaoService) {
        this.fornecedorService = fornecedorService;
        this.cadastroService = cadastroService;
        this.paginacaoService = paginacaoService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<FornecedorCadastroDto> cadastrarFornecedor(@RequestBody @Valid FornecedorCadastroDto fornecedorDto) {

        // Chama o service passando o DTO diretamente
        FornecedorCadastroDto novoFornecedor = cadastroService.cadastroFornecedor(fornecedorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
    }

    @GetMapping("/top5")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<FornecedorCompletoDTO>> getTop5Fornecedores() {
        List<FornecedorCompletoDTO> fornecedores = fornecedorService.buscarTop5Fornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<List<FornecedorCompletoDTO>> getFornecedorCompleto() {
        List<FornecedorCompletoDTO> fornecedores = fornecedorService.fornecedorCompleto();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/paginados")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PaginacaoFornecedorDTO> getFornecedorPaginado(@RequestParam(defaultValue = "1") Integer pagina,
                                                                        @RequestParam(defaultValue = "6") Integer tamanho){
        PaginacaoFornecedorDTO fornecedores = paginacaoService.fornecedorPaginado(pagina, tamanho);
        return ResponseEntity.ok(fornecedores);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<FornecedorCompletoDTO> deletarFornecedor(@PathVariable Integer id){
        Optional<FornecedorCompletoDTO> dto = fornecedorService.deletarFornecedor(id);

        if (dto.isPresent()) {
            return ResponseEntity.ok(dto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

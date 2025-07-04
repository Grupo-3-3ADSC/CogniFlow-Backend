package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.service.FornecedorService;

import java.util.List;
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

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity cadastrarFornecedor(@RequestBody @Valid FornecedorCadastroDto fornecedor){

        FornecedorModel novoFornecedor = fornecedorService.cadastroFornecedor(fornecedor);
        return ResponseEntity.status(201).body(novoFornecedor);
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

}

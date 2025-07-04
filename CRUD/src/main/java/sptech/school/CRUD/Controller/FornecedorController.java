package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.service.FornecedorService;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity cadastrarFornecedor(@RequestBody FornecedorCadastroDto fornecedor){
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

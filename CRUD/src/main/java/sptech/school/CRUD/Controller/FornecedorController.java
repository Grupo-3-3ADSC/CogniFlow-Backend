package sptech.school.CRUD.Controller;

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
    public ResponseEntity cadastrarFornecedor(@RequestBody FornecedorCadastroDto fornecedor){

        FornecedorModel novoFornecedor = fornecedorService.cadastroFornecedor(fornecedor);

        return ResponseEntity.status(201).body(novoFornecedor);

    }

    @GetMapping("/listarTudo")
    public ResponseEntity<List<FornecedorModel>> getAll() {

        return ResponseEntity.ok().body(fornecedorService.getAll());
    }

    @GetMapping("/top5")
    public ResponseEntity<List<FornecedorCompletoDTO>> getTop5Fornecedores() {
        List<FornecedorCompletoDTO> fornecedores = fornecedorService.buscarTop5Fornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorCompletoDTO>> getFornecedorCompleto() {
        List<FornecedorCompletoDTO> fornecedores = fornecedorService.fornecedorCompleto();
        return ResponseEntity.ok(fornecedores);
    }

}

package sptech.school.CRUD.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.dto.Fornecedor.FornecedorListagemDto;
import sptech.school.CRUD.service.FornecedorService;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<FornecedorListagemDto>> listarFornecedores(){
        List<FornecedorListagemDto> dtos = fornecedorService.listarFornecedoresDto();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/listarTudo")
    public ResponseEntity<List<FornecedorModel>> getAll() {

        return ResponseEntity.ok().body(fornecedorService.getAll());
    }

}

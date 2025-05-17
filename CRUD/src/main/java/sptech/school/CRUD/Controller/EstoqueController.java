package sptech.school.CRUD.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.service.EstoqueService;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoque(){

        List<EstoqueListagemDto> dtos = estoqueService.listagemEstoqueDtos();
        return ResponseEntity.ok().body(dtos);
    }


    @PostMapping
    public ResponseEntity<EstoqueModel> cadastrarEstoque(@RequestBody EstoqueModel estoque){

        EstoqueModel novoEstoque = estoqueService.cadastroEstoque(estoque);



        return ResponseEntity.status(201).body(novoEstoque);

    }



}

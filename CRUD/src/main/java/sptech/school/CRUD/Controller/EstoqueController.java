package sptech.school.CRUD.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.service.EstoqueService;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private final EstoqueService estoqueService;


    @GetMapping
    public List<EstoqueModel> listarEstoque(){
        return estoqueService.buscarEstoque();
    }


    @PostMapping
    public ResponseEntity<EstoqueModel> cadastrarEstoque(@RequestBody EstoqueModel estoque){

        EstoqueModel novoEstoque = estoqueService.cadastroEstoque(estoque);

        return ResponseEntity.status(201).body(novoEstoque);

    }
}

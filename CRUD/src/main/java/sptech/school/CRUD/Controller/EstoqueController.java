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
    @PostMapping("/adicionar")
    public ResponseEntity<EstoqueModel> adicionarEstoque(@RequestBody EstoqueModel request) {
        try {
            EstoqueModel estoque = estoqueService.atualizarEstoque(
                    request.getTipoMaterial(),
                    request.getQuantidadeAtual()

            );
            return ResponseEntity.ok(estoque);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/retirar/{tipoMaterial}/{quantidadeAtual}/{tipoTransferencia}")
    public ResponseEntity<EstoqueModel> retirarEstoque(
            @PathVariable String tipoMaterial,
            @PathVariable Integer quantidadeAtual,
            @PathVariable String tipoTransferencia) {
        try {
            EstoqueModel estoqueAtualizado = estoqueService.retirarEstoque(tipoMaterial, quantidadeAtual, tipoTransferencia);
            return ResponseEntity.ok(estoqueAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



}

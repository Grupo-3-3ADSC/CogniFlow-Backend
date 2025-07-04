package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.dto.Estoque.*;
import sptech.school.CRUD.service.EstoqueService;

import java.util.List;
import java.util.stream.Collectors;

import static sptech.school.CRUD.dto.Estoque.EstoqueMapper.toListagemDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private final EstoqueService estoqueService;


    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoque() {
            return ResponseEntity.ok(estoqueService.buscarEstoque());

    }

    @PostMapping("/adicionar")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> adicionarEstoque(@RequestBody AtualizarEstoqueDto dto) {

        EstoqueListagemDto resposta = estoqueService.atualizarEstoque(dto);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/retirar/{tipoMaterial}/{quantidadeAtual}/{tipoTransferencia}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> retirarEstoque(@RequestBody RetirarEstoqueDto dto) {
        EstoqueListagemDto resposta = estoqueService.retirarEstoque(dto);
        return ResponseEntity.ok(resposta);
    }

}

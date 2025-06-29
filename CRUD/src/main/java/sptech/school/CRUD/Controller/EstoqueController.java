package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.dto.Estoque.AtualizarEstoqueDto;
import sptech.school.CRUD.dto.Estoque.EstoqueCadastroDto;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;
import sptech.school.CRUD.service.EstoqueService;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> cadastrarEstoque(@RequestBody EstoqueCadastroDto dto) {
        EstoqueModel model = EstoqueMapper.toCadastro(dto);
        EstoqueModel salvo = estoqueService.cadastroEstoque(model);
        EstoqueListagemDto resposta = EstoqueMapper.toListagemDto(salvo);
        return ResponseEntity.status(201).body(resposta);
    }


    @PostMapping("/adicionar")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> adicionarEstoque(@RequestBody AtualizarEstoqueDto dto) {
        EstoqueModel atualizado = estoqueService.atualizarEstoque(
                dto.getTipoMaterial(),
                dto.getQuantidadeAtual()
        );
        return ResponseEntity.ok(EstoqueMapper.toListagemDto(atualizado));
    }
    @PutMapping("/retirar/{tipoMaterial}/{quantidadeAtual}/{tipoTransferencia}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EstoqueListagemDto> retirarEstoque(
            @PathVariable String tipoMaterial,
            @PathVariable Integer quantidadeAtual,
            @PathVariable String tipoTransferencia) {

        EstoqueModel atualizado = estoqueService.retirarEstoque(tipoMaterial, quantidadeAtual, tipoTransferencia);
        return ResponseEntity.ok(EstoqueMapper.toListagemDto(atualizado));
    }



}

package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.dto.OrdemDeCompra.ListagemOrdemDeCompra;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraMapper;
import sptech.school.CRUD.service.OrdemDeCompraService;

import java.util.List;

@RestController
@RequestMapping("/ordemDeCompra")
@RequiredArgsConstructor
public class OrdemDeCompraController {

    private final OrdemDeCompraService ordemDeCompraService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ListagemOrdemDeCompra> cadastrarOrdemDeCompra(@Valid @RequestBody OrdemDeCompraCadastroDto ordemDeCompra){

        OrdemDeCompraModel model = ordemDeCompraService.cadastroOrdemDeCompra(ordemDeCompra);
        ListagemOrdemDeCompra resposta = OrdemDeCompraMapper.toListagemDto(model);
        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ListagemOrdemDeCompra>> listarOrdemDeCompra() {
        return ResponseEntity.ok(ordemDeCompraService.getAll());
    }
}

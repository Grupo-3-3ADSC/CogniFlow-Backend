package sptech.school.CRUD.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.service.OrdemDeCompraService;

import java.util.List;

@RestController
@RequestMapping("/ordemDeCompra")
@RequiredArgsConstructor
public class OrdemDeCompraController {

    private final OrdemDeCompraService ordemDeCompraService;

    @PostMapping
    public ResponseEntity<OrdemDeCompraModel> cadastrarOrdemDeCompra(@RequestBody OrdemDeCompraCadastroDto ordemDeCompra){

        OrdemDeCompraModel novaOrdemDeCompraModel = ordemDeCompraService.cadastroOrdemDeCompra(ordemDeCompra);

        return ResponseEntity.status(201).body(novaOrdemDeCompraModel);
    }

    @GetMapping
    public ResponseEntity<List<OrdemDeCompraModel>> listarOrdemDeCompra(){
        return ResponseEntity.ok(ordemDeCompraService.getAll());
    }
}

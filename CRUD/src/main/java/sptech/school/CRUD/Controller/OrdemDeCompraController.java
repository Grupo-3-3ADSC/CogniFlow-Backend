package sptech.school.CRUD.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.service.OrdemDeCompraService;

@RestController
@RequestMapping("/ordemDeCompra")
@RequiredArgsConstructor
public class OrdemDeCompraController {

    private final OrdemDeCompraService ordemDeCompraService;

    @PostMapping
    public ResponseEntity<OrdemDeCompraModel> cadastrarOrdemDeCompra(@RequestBody OrdemDeCompraModel ordemDeCompra){

        OrdemDeCompraModel novaOrdemDeCompraModel = ordemDeCompraService.cadastroOrdemDeCompra(ordemDeCompra);

        return ResponseEntity.status(201).body(novaOrdemDeCompraModel);
    }
}

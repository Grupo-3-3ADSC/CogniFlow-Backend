package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.application.service.ItemOrdemDeCompra.ConjuntoOrdemDeCompraService;
import sptech.school.CRUD.domain.entity.ConjuntoOrdemDeCompraModel;
import sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra.ConjuntoOrdemDeCompraDTO;
import sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra.ConjuntoOrdemDeCompraListagemDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conjunto-ordem-compra")
@RequiredArgsConstructor
public class ConjuntoOrdemDeCompraController {

    private final ConjuntoOrdemDeCompraService service;

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ConjuntoOrdemDeCompraListagemDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ConjuntoOrdemDeCompraModel> buscarPorId(@PathVariable Integer id) {
        ConjuntoOrdemDeCompraModel conjunto = service.buscarPorId(id);
        return ResponseEntity.ok(conjunto);
    }


}
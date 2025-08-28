package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Repository.TransferenciaRepository;
import sptech.school.CRUD.dto.Estoque.AtualizarEstoqueDto;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.dto.Transferencia.TransferenciaListagemDto;
import sptech.school.CRUD.service.TransferenciaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    // Criar/realizar transferência
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<TransferenciaDto> realizarTransferencia(@RequestBody @Valid TransferenciaDto dto) {
        TransferenciaDto resposta = transferenciaService.realizarTransferencia(dto);
        return ResponseEntity.ok(resposta);
    }

    // Listar todas as transferências
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<TransferenciaListagemDto>> listarTransferencias() {
        List<TransferenciaListagemDto> lista = transferenciaService.buscarSetor();
        return ResponseEntity.ok(lista);
    }
}

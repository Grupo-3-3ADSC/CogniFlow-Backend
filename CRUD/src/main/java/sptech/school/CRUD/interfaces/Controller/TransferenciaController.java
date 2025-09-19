package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaListagemDto;
import sptech.school.CRUD.application.service.transferencia.TransferenciaService;
import sptech.school.CRUD.application.service.transferencia.RealizarTransferenciaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;
    private final RealizarTransferenciaService realizarTransferenciaService;

    // Criar/realizar transferência
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<TransferenciaDto> realizarTransferencia(@RequestBody @Valid TransferenciaDto dto) {
        TransferenciaDto resposta = realizarTransferenciaService.realizarTransferencia(dto);
        return ResponseEntity.ok(resposta);
    }

    // Listar todas as transferências
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<TransferenciaListagemDto>> listarTransferencias() {
        List<TransferenciaListagemDto> lista = transferenciaService.buscarSetor();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/material/{tipoMaterial}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<TransferenciaListagemDto>> listarTransferenciasPorMaterial(
            @PathVariable String tipoMaterial,
            @RequestParam(required = false) Integer ano) {

        List<TransferenciaListagemDto> lista = transferenciaService.buscarPorMaterialEano(tipoMaterial, ano);
        return ResponseEntity.ok(lista);
    }




}

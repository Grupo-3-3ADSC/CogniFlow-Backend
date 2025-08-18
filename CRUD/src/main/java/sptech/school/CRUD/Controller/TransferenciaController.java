package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.CRUD.Repository.TransferenciaRepository;
import sptech.school.CRUD.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.service.TransferenciaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private final TransferenciaService transferenciaService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<TransferenciaDto>> listarTipoTransferencia(){
        return ResponseEntity.ok(transferenciaService.buscarTipoTransferencia());
    }
}

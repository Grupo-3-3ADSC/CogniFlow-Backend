package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.application.service.CargoService;

import java.util.List;
@Tag(name = "Cargo", description = "Endpoints de Cargo")
@RestController
@RequestMapping("/cargos")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Entidade relacionada não encontrada"),
        @ApiResponse(responseCode = "409", description = "Conflito de dados (ex: rastreabilidade duplicada)")
})
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<CargoListagemDto>> getAll() {
        return ResponseEntity.ok().body(cargoService.getAll());
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<CargoListagemDto> post(@RequestBody @Valid CargoCadastraDto cargo) {

        CargoListagemDto cargoPost = cargoService.post(cargo);

        if(cargoPost == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(cargoPost);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<CargoListagemDto> getById(@PathVariable Integer id) {
        CargoListagemDto dto = cargoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}

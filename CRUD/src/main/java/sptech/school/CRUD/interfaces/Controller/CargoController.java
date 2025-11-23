package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.application.service.notificacao.NotificationService;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.application.service.cargo.CargoService;

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
    private final NotificationService notificationService;

    public CargoController(CargoService cargoService, NotificationService notificationService) {
        this.cargoService = cargoService;
        this.notificationService = notificationService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<CargoListagemDto>> getAll() {
        return ResponseEntity.ok().body(cargoService.getAll());
    }

<<<<<<< Updated upstream
=======
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<CargoListagemDto> post(@RequestBody @Valid CargoCadastraDto cargo) {

        CargoListagemDto cargoPost = cargoService.post(cargo);

        if (cargoPost == null) {
            return ResponseEntity.badRequest().build();
        }

        String mensagemToast = "Novo cargo cadastrado no sistema!";
        String mensagemEmail = "Novo cargo adicionado no sistema:\n\n" +
                "Cargo: " + cargoPost.getNome() + "\n";

        notificationService.notificar(
                "cadastro_cargo",
                "CRIADO",
                String.valueOf(cargoPost.getNome()),
                mensagemToast,
                "Novos materias no estoque",
                mensagemEmail
        );

        return ResponseEntity.status(201).body(cargoPost);
    }

>>>>>>> Stashed changes
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<CargoListagemDto> getById(@PathVariable Integer id) {
        CargoListagemDto dto = cargoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}

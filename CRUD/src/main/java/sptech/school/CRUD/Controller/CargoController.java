package sptech.school.CRUD.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.service.CargoService;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<CargoModel>> getAll() {
        return ResponseEntity.ok().body(cargoService.getAll());
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<CargoModel> post(@RequestBody CargoModel cargo) {

        CargoModel cargoPost = cargoService.post(cargo);

        if(cargoPost == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(cargoPost);
    }

}

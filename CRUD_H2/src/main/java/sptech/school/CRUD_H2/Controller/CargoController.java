package sptech.school.CRUD_H2.Controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;
import sptech.school.CRUD_H2.service.CargoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoModel>> getAll() {
        return ResponseEntity.ok().body(cargoService.getAll());
    }

    @PostMapping
    public ResponseEntity<CargoModel> post(@RequestBody CargoModel cargo) {

        CargoModel cargoPost = cargoService.post(cargo);

        if(cargoPost == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(cargoPost);
    }

}

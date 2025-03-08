package sptech.school.CRUD_H2.Controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public ResponseEntity<List<CargoModel>> getAll() {

        if(cargoRepository.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(cargoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<CargoModel> post(@RequestBody CargoModel cargo) {
        return ResponseEntity.status(201).body(cargoRepository.save(cargo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoModel> put(@PathVariable int id, @RequestBody CargoModel cargo) {

        if (cargoRepository.existsById(id)) {
            cargo.setId(id);
            cargo.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok().body(cargoRepository.save(cargo));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        if(cargoRepository.existsById(id)) {
            cargoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}

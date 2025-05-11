package sptech.school.CRUD.service;


import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Repository.CargoRepository;

import java.util.List;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoModel> getAll() {
        return cargoRepository.findAll();
    }

    public CargoModel post(CargoModel cargo) {

        if(cargo == null) {
            return null;
        }

        return cargoRepository.save(cargo);
    }


}

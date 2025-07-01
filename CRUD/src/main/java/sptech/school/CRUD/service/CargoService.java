package sptech.school.CRUD.service;


import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.dto.Cargo.CargoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoListagemDto> getAll() {
        return cargoRepository.findAll().stream()
                .map(CargoMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    public CargoListagemDto  post(CargoCadastraDto cargo) {
        if(cargo == null) {
            return null;
        }

        CargoModel entity = CargoMapper.toCadastro(cargo);
        CargoModel saved = cargoRepository.save(entity);
        return CargoMapper.toListagemDto(saved);
    }


}

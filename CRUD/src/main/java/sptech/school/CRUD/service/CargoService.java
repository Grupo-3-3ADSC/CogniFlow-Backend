package sptech.school.CRUD.service;


import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.dto.Cargo.CargoMapper;

import sptech.school.CRUD.exception.BadRequestException;
import sptech.school.CRUD.exception.CargoJaExistenteException;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;
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

    public CargoListagemDto post(CargoCadastraDto cargo) {
        if (cargo == null || cargo.getNome() == null || cargo.getNome().isBlank()) {
            throw new BadRequestException("O nome do cargo é obrigatório.");
        }

        CargoModel entity = CargoMapper.toCadastro(cargo);
        CargoModel saved = this.cadastrar(entity);

        return CargoMapper.toListagemDto(saved);
    }


    public CargoModel cadastrar(CargoModel cargo) {
        if (cargoRepository.existsByNomeIgnoreCase(cargo.getNome())) {
            throw new CargoJaExistenteException("Já existe um cargo com esse nome.");
        }

        return cargoRepository.save(cargo);
    }


}

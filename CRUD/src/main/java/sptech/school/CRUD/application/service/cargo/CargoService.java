package sptech.school.CRUD.application.service.cargo;


import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.infrastructure.persistence.cargo.CargoRepository;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoMapper;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;

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

    public CargoListagemDto buscarPorId(Integer id) {
        CargoModel cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo não encontrado com id " + id));
        return CargoMapper.toListagemDto(cargo);
    }

}

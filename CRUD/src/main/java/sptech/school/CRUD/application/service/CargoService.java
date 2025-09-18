package sptech.school.CRUD.application.service;


import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.repository.CargoRepository;
import sptech.school.CRUD.dto.Cargo.CargoCadastraDto;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;
import sptech.school.CRUD.dto.Cargo.CargoMapper;
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

    public CargoListagemDto post(CargoCadastraDto cargo) {
        if (cargo == null || cargo.getNome() == null || cargo.getNome().isBlank()) {
            throw new RequisicaoInvalidaException("O nome do cargo é obrigatório.");
        }

        CargoModel entity = CargoMapper.toCadastro(cargo);
        CargoModel saved = this.cadastrar(entity);

        return CargoMapper.toListagemDto(saved);
    }


    public CargoModel cadastrar(CargoModel cargo) {
        if (cargoRepository.existsByNomeIgnoreCase(cargo.getNome())) {
            throw new RequisicaoConflitanteException("Já existe um cargo com o nome:" + cargo.getNome());
        }

        return cargoRepository.save(cargo);
    }

    public CargoListagemDto buscarPorId(Integer id) {
        CargoModel cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo não encontrado com id " + id));
        return CargoMapper.toListagemDto(cargo);
    }

}

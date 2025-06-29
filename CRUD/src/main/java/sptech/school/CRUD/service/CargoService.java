package sptech.school.CRUD.service;



import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.exception.BadRequestException;
import sptech.school.CRUD.exception.EntidadeNaoEncontrado;
import java.util.List;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }



    public List<CargoModel> getAll() {
        List<CargoModel> cargos = cargoRepository.findAll();
        if (cargos.isEmpty()) {
            throw new EntidadeNaoEncontrado("Nenhum cargo encontrado.");
        }
        return cargos;
    }

    public CargoModel post(CargoModel cargo) {

        if (cargo == null) {
            throw new BadRequestException("O corpo da requisição está vazio.");
        }

        if (cargo.getNome() == null || cargo.getNome().trim().isEmpty()) {
            throw new BadRequestException("O nome do cargo é obrigatório.");
        }

        return cargoRepository.save(cargo);
    }


}

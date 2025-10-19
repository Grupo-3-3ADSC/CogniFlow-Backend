package sptech.school.CRUD.infrastructure.persistence.cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.CargoModel;

public interface CargoNomeRepository extends JpaRepository<CargoModel, Integer> {
    CargoModel findByNome(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}

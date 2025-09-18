package sptech.school.CRUD.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.CargoModel;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel, Integer> {
    CargoModel findByNome(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}

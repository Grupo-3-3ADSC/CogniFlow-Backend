package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.Model.CargoModel;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel, Integer> {
    CargoModel findByNome(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}

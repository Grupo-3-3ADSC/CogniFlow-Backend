package sptech.school.CRUD_H2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD_H2.Model.CargoModel;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel, Integer> {
    CargoModel findByNome(String nome);
}

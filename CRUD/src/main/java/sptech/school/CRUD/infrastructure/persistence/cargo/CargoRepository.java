package sptech.school.CRUD.infrastructure.persistence.cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.CargoModel;

@Repository
public interface CargoRepository extends CargoNomeRepository {
    @Override
    CargoModel findByNome(String nome);
    @Override
    boolean existsByNomeIgnoreCase(String nome);
}

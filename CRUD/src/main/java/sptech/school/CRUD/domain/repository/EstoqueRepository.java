package sptech.school.CRUD.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.EstoqueModel;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<EstoqueModel, Integer> {
    Optional<EstoqueModel> findByTipoMaterial(String tipoMaterial);

}

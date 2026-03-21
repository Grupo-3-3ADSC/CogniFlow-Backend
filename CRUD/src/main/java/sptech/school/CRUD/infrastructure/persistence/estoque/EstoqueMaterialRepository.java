package sptech.school.CRUD.infrastructure.persistence.estoque;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.EstoqueModel;

import java.util.Optional;

public interface EstoqueMaterialRepository extends JpaRepository<EstoqueModel, Integer> {
    Optional<EstoqueModel> findByTipoMaterial(String tipoMaterial);
}

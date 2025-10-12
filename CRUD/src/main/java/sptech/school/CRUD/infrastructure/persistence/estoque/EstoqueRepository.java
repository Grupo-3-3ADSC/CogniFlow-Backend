package sptech.school.CRUD.infrastructure.persistence.estoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.EstoqueModel;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends EstoqueMaterialRepository {
    @Override
    Optional<EstoqueModel> findByTipoMaterial(String tipoMaterial);
}

package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.EstoqueModel;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<EstoqueModel, Integer> {
    Optional<EstoqueModel> findByTipoMaterial(String tipoMaterial);
}

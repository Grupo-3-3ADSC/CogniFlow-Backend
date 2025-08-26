package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.TransferenciaModel;

import java.util.Optional;

public interface TransferenciaRepository extends JpaRepository<TransferenciaModel, Integer> {
    Optional<TransferenciaModel> findBySetor(String setor);
    Optional<TransferenciaModel> findByTipoMaterial(String tipoMaterial);
}

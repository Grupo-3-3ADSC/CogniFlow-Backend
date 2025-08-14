package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.TransferenciaModel;

import java.util.Optional;

public interface TransferenciaRepository extends JpaRepository<TransferenciaModel, Integer> {
    Optional<TransferenciaModel> findByTipoTransferencia(String tipoTransferencia);
}

package sptech.school.CRUD.infrastructure.persistence.transferencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.domain.entity.TransferenciaModel;

import java.util.List;

public interface TransferenciaMaterial extends JpaRepository<TransferenciaModel, Integer> {
    List<TransferenciaModel> findByTipoMaterial(String tipoMaterial);
}

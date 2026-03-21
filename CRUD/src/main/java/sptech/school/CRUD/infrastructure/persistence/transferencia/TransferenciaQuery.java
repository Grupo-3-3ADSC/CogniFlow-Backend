package sptech.school.CRUD.infrastructure.persistence.transferencia;

import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.domain.entity.TransferenciaModel;

import java.util.List;

public interface TransferenciaQuery {
    List<TransferenciaModel> findByTipoMaterialAndAno(@Param("tipoMaterial") String tipoMaterial, @Param("ano") Integer ano);
}

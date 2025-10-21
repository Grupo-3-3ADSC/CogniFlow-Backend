package sptech.school.CRUD.infrastructure.persistence.transferencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.CRUD.domain.entity.TransferenciaModel;

public interface TransferenciaPaginada {
    Page<TransferenciaModel> findTransferenciasPaginadas(Pageable pageable);
}

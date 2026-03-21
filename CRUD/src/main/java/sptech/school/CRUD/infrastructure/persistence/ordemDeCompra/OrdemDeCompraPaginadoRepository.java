package sptech.school.CRUD.infrastructure.persistence.ordemDeCompra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;

public interface OrdemDeCompraPaginadoRepository {
    Page<OrdemDeCompraModel> findOrdensDeCompraPaginadas(Pageable pageable);
}

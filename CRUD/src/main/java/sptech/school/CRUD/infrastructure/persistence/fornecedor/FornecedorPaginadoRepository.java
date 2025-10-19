package sptech.school.CRUD.infrastructure.persistence.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCompletoDTO;

public interface FornecedorPaginadoRepository {
    Page<FornecedorCompletoDTO> findFornecedoresPaginados(Pageable pageable);
}

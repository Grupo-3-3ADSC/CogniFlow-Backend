package sptech.school.CRUD.infrastructure.persistence.fornecedor;

import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCompletoDTO;

import java.util.List;

public interface FornecedorCompletoRepository {
    List<Object[]> findTop5FornecedoresCompletos();
    List<FornecedorCompletoDTO> findFornecedoresCompletos();
}

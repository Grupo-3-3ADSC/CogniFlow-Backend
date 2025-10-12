package sptech.school.CRUD.infrastructure.persistence.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.FornecedorModel;

import java.util.Optional;

public interface FornecedorCNPJRepository extends JpaRepository<FornecedorModel, Integer> {
    Optional<FornecedorModel> findByCnpj(String cnpj);
}

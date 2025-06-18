package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.FornecedorModel;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<FornecedorModel, Integer> {
    Optional<FornecedorModel> findByCnpj(String cnpj);
}

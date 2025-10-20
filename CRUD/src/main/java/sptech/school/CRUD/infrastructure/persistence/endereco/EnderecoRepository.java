package sptech.school.CRUD.infrastructure.persistence.endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.domain.entity.EnderecoModel;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
    @Modifying
    @Query(value = "DELETE FROM endereco_model WHERE fornecedor_id = :fornecedorId", nativeQuery = true)
    void deleteEnderecosByFornecedorId(@Param("fornecedorId") Integer fornecedorId);

    // SE IMPLEMENTAR MÉTODOS AQUI, FAVOR SEPARAR EM OUTRAS CLASSES E EXTENDER PARA NÃO ACOPLAR PROJETO AO SPRING
}

package sptech.school.CRUD.infrastructure.persistence.contato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.ContatoModel;

@Repository
public interface ContatoRepository extends ContatoEmailRepository{
    @Override
    boolean existsByEmail(String email);

    // Se houver contatos relacionados, adicione também:
    @Modifying
    @Query(value = "DELETE FROM contato_model WHERE fornecedor_id = :fornecedorId", nativeQuery = true)
    void deleteContatosByFornecedorId(@Param("fornecedorId") Integer fornecedorId);

}

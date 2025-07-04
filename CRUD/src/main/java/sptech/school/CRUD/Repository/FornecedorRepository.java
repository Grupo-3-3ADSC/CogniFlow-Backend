package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<FornecedorModel, Integer> {
    Optional<FornecedorModel> findByCnpj(String cnpj);
    @Query(value = """
        SELECT f.id as fornecedor_id, f.cnpj, f.razao_social, f.nome_fantasia, 
               e.id as endereco_id, e.cep, e.numero, e.complemento, 
               c.id as contato_id, c.telefone, c.email 
        FROM fornecedor_model f 
        LEFT JOIN endereco_model e ON f.id = e.fornecedor_id 
        LEFT JOIN contato_model c ON f.id = c.fornecedor_id 
        ORDER BY f.id DESC LIMIT 5
        """, nativeQuery = true)
    List<Object[]> findTop5FornecedoresCompletos();

    @Query(value = """
        SELECT f.id as fornecedor_id, f.cnpj, f.razao_social, f.nome_fantasia, 
               e.id as endereco_id, e.cep, e.numero, e.complemento, 
               c.id as contato_id, c.telefone, c.email 
        FROM fornecedor_model f 
        LEFT JOIN endereco_model e ON f.id = e.fornecedor_id 
        LEFT JOIN contato_model c ON f.id = c.fornecedor_id 
        ORDER BY f.id DESC
        """, nativeQuery = true)
    List<FornecedorCompletoDTO> findFornecedoresCompletos();


}

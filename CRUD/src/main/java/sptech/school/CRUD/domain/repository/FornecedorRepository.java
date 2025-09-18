package sptech.school.CRUD.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<FornecedorModel, Integer> {
    Optional<FornecedorModel> findByCnpj(String cnpj);
    @Query(value = """
        SELECT f.id as fornecedor_id, f.cnpj, f.ie, f.razao_social, f.nome_fantasia, 
               e.id as endereco_id, e.cep, e.numero, e.complemento, 
               c.id as contato_id, c.telefone, c.email
        FROM fornecedor_model f 
        LEFT JOIN endereco_model e ON f.id = e.fornecedor_id 
        LEFT JOIN contato_model c ON f.id = c.fornecedor_id 
        ORDER BY f.id DESC LIMIT 5
        """, nativeQuery = true)
    List<Object[]> findTop5FornecedoresCompletos();

    @Query("""
    SELECT new sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO(
        f.id, f.cnpj, f.ie, f.razaoSocial, f.nomeFantasia,
        e.id, e.cep, e.numero, e.complemento,
        c.id, c.telefone, c.email, c.responsavel, c.cargo
    )
    FROM FornecedorModel f
    LEFT JOIN EnderecoModel e ON e.fornecedor.id = f.id
    LEFT JOIN ContatoModel c ON c.fornecedor.id = f.id
    ORDER BY f.id DESC
    """)

    List<FornecedorCompletoDTO> findFornecedoresCompletos();

    @Query("""
    SELECT DISTINCT new sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO(
        f.id, f.cnpj, f.ie, f.razaoSocial, f.nomeFantasia,
        e.id, e.cep, e.numero, e.complemento,
        c.id, c.telefone, c.email, f.responsavel,
        f.cargo
        )
    FROM FornecedorModel f
    LEFT JOIN EnderecoModel e ON e.fornecedor.id = f.id
    LEFT JOIN ContatoModel c ON c.fornecedor.id = f.id
    ORDER BY f.id ASC
""")
    Page<FornecedorCompletoDTO> findFornecedoresPaginados(Pageable pageable);
}

package sptech.school.CRUD.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.dto.OrdemDeCompra.ListagemOrdemDeCompra;

import java.util.List;
import java.util.Optional;

public interface OrdemDeCompraRepository extends JpaRepository<OrdemDeCompraModel, Integer> {

    boolean existsByRastreabilidadeAndEstoqueId( String rastreabilidade,Integer estoqueId);

    @Query("SELECT o FROM OrdemDeCompraModel o " +
            "LEFT JOIN FETCH o.fornecedor " +
            "LEFT JOIN FETCH o.estoque " +
            "LEFT JOIN FETCH o.usuario " +
            "WHERE o.id = :id")
    Optional<OrdemDeCompraModel> findByIdComJoins(@Param("id") Integer id);

    @Query("SELECT o FROM OrdemDeCompraModel o ORDER BY o.id ASC")
    Page<OrdemDeCompraModel> findOrdensDeCompraPaginadas(Pageable pageable);

    @Query("SELECT o FROM OrdemDeCompraModel o " +
            "JOIN FETCH o.fornecedor f " +
            "WHERE o.fornecedor.id = :fornecedorId " +
            "AND o.dataDeEmissao IS NOT NULL " +
            "AND FUNCTION('YEAR', o.dataDeEmissao) = :ano")
    List<OrdemDeCompraModel> findByFornecedorIdAndAno(
            @Param("fornecedorId") Integer fornecedorId,
            @Param("ano") Integer ano
    );
}

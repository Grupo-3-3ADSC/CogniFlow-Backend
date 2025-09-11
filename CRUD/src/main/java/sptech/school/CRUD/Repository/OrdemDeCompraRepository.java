package sptech.school.CRUD.Repository;

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


    List<OrdemDeCompraModel> findByEstoqueId(Integer estoqueId);

    @Query("SELECT o FROM OrdemDeCompraModel o WHERE o.estoque.id = :estoqueId AND YEAR(o.dataDeEmissao) = :ano")
    List<OrdemDeCompraModel> findByEstoqueIdAndAno(@Param("estoqueId") Integer estoqueId, @Param("ano") Integer ano);

    @Query("SELECT new sptech.school.CRUD.dto.OrdemDeCompra.ListagemOrdemDeCompra(" +
            "o.id, o.prazoEntrega, o.ie, o.condPagamento, o.valorKg, o.rastreabilidade, " +
            "o.valorPeca, o.descricaoMaterial, o.valorUnitario, o.quantidade, o.ipi, " +
            "o.fornecedorId, o.estoqueId, o.usuarioId, f.nomeFantasia, " +
            "CONCAT(o.descricaoMaterial, ' ', e.tipoMaterial), " + // descricaoMaterialCompleta
            "o.dataDeEmissao, " +
            "e.tipoMaterial, " + // tipoMaterial
            "o.pendenciaAlterada) " +
            "FROM OrdemDeCompraModel o " +
            "LEFT JOIN o.fornecedor f " +
            "LEFT JOIN o.estoque e " +
            "WHERE o.id = :id")
    Optional<ListagemOrdemDeCompra> findByIdComJoinsDTO(@Param("id") Integer id);

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

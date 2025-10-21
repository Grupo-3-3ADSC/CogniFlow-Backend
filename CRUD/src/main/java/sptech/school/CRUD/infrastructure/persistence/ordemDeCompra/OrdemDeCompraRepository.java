package sptech.school.CRUD.infrastructure.persistence.ordemDeCompra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra;

import java.util.List;
import java.util.Optional;

public interface OrdemDeCompraRepository extends OrdemDeCompraJpaRepository,
OrdemDeCompraPaginadoRepository,
OrdemDeCompraQueryRepository
{

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
            "FROM OrdemDeCompraModel o " +
            "JOIN o.itens i " +
            "WHERE i.rastreabilidade = :rastreabilidade " +
            "AND i.estoque.id = :estoqueId")
    boolean existsByRastreabilidadeAndEstoqueId(
            @Param("rastreabilidade") String rastreabilidade,
            @Param("estoqueId") Integer estoqueId);


    // Busca ordem por ID com joins para fornecedor, usuário e itens
    @Query("SELECT o FROM OrdemDeCompraModel o " +
            "LEFT JOIN FETCH o.fornecedor " +
            "LEFT JOIN FETCH o.usuario " +
            "LEFT JOIN FETCH o.itens i " +
            "LEFT JOIN FETCH i.estoque " +
            "WHERE o.id = :id")
    Optional<OrdemDeCompraModel> findByIdComJoins(@Param("id") Integer id);

    // Paginação simples de todas as ordens
    @Query("SELECT o FROM OrdemDeCompraModel o ORDER BY o.id ASC")
    Page<OrdemDeCompraModel> findOrdensDeCompraPaginadas(Pageable pageable);

    // Busca ordens por estoque
    @Query("SELECT DISTINCT o FROM OrdemDeCompraModel o " +
            "JOIN o.itens i " +
            "WHERE i.estoque.id = :estoqueId")
    List<OrdemDeCompraModel> findByEstoqueId(@Param("estoqueId") Integer estoqueId);

    // Busca ordens por estoque e ano
    @Query("SELECT DISTINCT o FROM OrdemDeCompraModel o " +
            "JOIN o.itens i " +
            "WHERE i.estoque.id = :estoqueId AND FUNCTION('YEAR', o.dataDeEmissao) = :ano")
    List<OrdemDeCompraModel> findByEstoqueIdAndAno(@Param("estoqueId") Integer estoqueId, @Param("ano") Integer ano);

    // Busca ordem por ID e retorna DTO diretamente
    @Query("SELECT new sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra(" +
            "o.id, o.prazoEntrega, o.condPagamento, NULL, NULL, " +
            "NULL, NULL, NULL, NULL, NULL, " +
            "o.fornecedor.id, NULL, o.usuario.id, f.nomeFantasia, " +
            "NULL, o.dataDeEmissao, NULL, o.pendenciaAlterada) " +
            "FROM OrdemDeCompraModel o " +
            "LEFT JOIN o.fornecedor f " +
            "LEFT JOIN o.itens i " +
            "LEFT JOIN i.estoque e " +
            "WHERE o.id = :id")
    Optional<ListagemOrdemDeCompra> findByIdComJoinsDTO(@Param("id") Integer id);


    // Busca ordens de um fornecedor em determinado ano
    @Query("SELECT DISTINCT o FROM OrdemDeCompraModel o " +
            "JOIN FETCH o.fornecedor f " +
            "JOIN FETCH o.itens i " +
            "JOIN FETCH i.estoque e " +
            "WHERE o.fornecedor.id = :fornecedorId " +
            "AND o.dataDeEmissao IS NOT NULL " +
            "AND FUNCTION('YEAR', o.dataDeEmissao) = :ano")
    List<OrdemDeCompraModel> findByFornecedorIdAndAno(
            @Param("fornecedorId") Integer fornecedorId,
            @Param("ano") Integer ano
    );

    @Transactional
    @Modifying
    @Query("DELETE FROM OrdemDeCompraModel o WHERE o.fornecedor.id = :fornecedorId")
    void deleteByFornecedorId(@Param("fornecedorId") Integer fornecedorId);



}

package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.Model.OrdemDeCompraModel;

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

}

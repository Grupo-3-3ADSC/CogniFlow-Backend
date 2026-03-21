package sptech.school.CRUD.infrastructure.persistence.ordemDeCompra;

import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra;

import java.util.List;
import java.util.Optional;

public interface OrdemDeCompraQueryRepository {
    Optional<OrdemDeCompraModel> findByIdComJoins(@Param("id") Integer id);
    List<OrdemDeCompraModel> findByEstoqueIdAndAno(@Param("estoqueId") Integer estoqueId, @Param("ano") Integer ano);
    Optional<ListagemOrdemDeCompra> findByIdComJoinsDTO(@Param("id") Integer id);
    List<OrdemDeCompraModel> findByFornecedorIdAndAno(@Param("fornecedorId") Integer fornecedorId, @Param("ano") Integer ano);
}

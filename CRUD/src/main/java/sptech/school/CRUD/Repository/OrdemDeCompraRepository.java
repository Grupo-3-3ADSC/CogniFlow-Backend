package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.OrdemDeCompraModel;

public interface OrdemDeCompraRepository extends JpaRepository<OrdemDeCompraModel, Integer> {

    boolean existsByRastreabilidadeAndEstoqueId( String rastreabilidade,Integer estoqueId);


}

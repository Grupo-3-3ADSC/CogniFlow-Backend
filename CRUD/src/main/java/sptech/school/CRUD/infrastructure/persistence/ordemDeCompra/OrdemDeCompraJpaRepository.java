package sptech.school.CRUD.infrastructure.persistence.ordemDeCompra;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;

import java.util.List;

public interface OrdemDeCompraJpaRepository extends JpaRepository<OrdemDeCompraModel, Integer> {
    boolean existsByRastreabilidadeAndEstoqueId( String rastreabilidade,Integer estoqueId);
    List<OrdemDeCompraModel> findByEstoqueId(Integer estoqueId);
}

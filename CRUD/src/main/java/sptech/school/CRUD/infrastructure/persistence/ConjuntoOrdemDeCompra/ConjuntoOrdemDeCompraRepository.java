package sptech.school.CRUD.infrastructure.persistence.ConjuntoOrdemDeCompra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.CRUD.domain.entity.ConjuntoOrdemDeCompraModel;

import java.util.List;

public interface ConjuntoOrdemDeCompraRepository extends JpaRepository<ConjuntoOrdemDeCompraModel, Integer> {
    @Query("SELECT c FROM ConjuntoOrdemDeCompraModel c LEFT JOIN FETCH c.ordensDeCompra")
    List<ConjuntoOrdemDeCompraModel> findAllWithOrdens();
}

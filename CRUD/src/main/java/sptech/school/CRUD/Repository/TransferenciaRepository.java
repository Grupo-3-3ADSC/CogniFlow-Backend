package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.TransferenciaModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransferenciaRepository extends JpaRepository<TransferenciaModel, Integer> {
    Optional<TransferenciaModel> findBySetor(String setor);

//    Optional<TransferenciaModel> findByTipoMaterial(String tipoMaterial);

    List<TransferenciaModel> findByTipoMaterial(String tipoMaterial);

    // Buscar transferências de um material em um ano específico
    @Query("SELECT t FROM TransferenciaModel t WHERE t.tipoMaterial = :tipoMaterial AND YEAR(t.ultimaMovimentacao) = :ano")
    List<TransferenciaModel> findByTipoMaterialAndAno(@Param("tipoMaterial") String tipoMaterial,
                                                      @Param("ano") Integer ano);

}

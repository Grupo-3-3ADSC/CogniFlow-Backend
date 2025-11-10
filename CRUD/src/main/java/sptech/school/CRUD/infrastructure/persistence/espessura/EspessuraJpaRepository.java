package sptech.school.CRUD.infrastructure.persistence.espessura;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.EspessuraModel;

import java.util.List;

public interface EspessuraJpaRepository extends JpaRepository<EspessuraModel, Integer> {
    List<EspessuraModel> findByEstoqueModelId(Integer estoqueId);
}

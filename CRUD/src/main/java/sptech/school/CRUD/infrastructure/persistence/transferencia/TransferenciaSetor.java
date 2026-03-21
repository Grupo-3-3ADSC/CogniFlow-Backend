package sptech.school.CRUD.infrastructure.persistence.transferencia;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.TransferenciaModel;

import java.util.Optional;

public interface TransferenciaSetor extends JpaRepository<TransferenciaModel,Integer> {
    Optional<TransferenciaModel> findBySetor(String setor);
}

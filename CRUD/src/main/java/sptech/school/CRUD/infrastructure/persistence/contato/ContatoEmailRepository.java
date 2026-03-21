package sptech.school.CRUD.infrastructure.persistence.contato;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.ContatoModel;

public interface ContatoEmailRepository extends JpaRepository<ContatoModel, Integer> {
    boolean existsByEmail(String email);
}

package sptech.school.CRUD.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.ContatoModel;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoModel, Integer> {
    boolean existsByEmail(String email);
}

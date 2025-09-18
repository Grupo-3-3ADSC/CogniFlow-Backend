package sptech.school.CRUD.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.ContatoModel;

public interface ContatoRepository extends JpaRepository<ContatoModel, Integer> {
    boolean existsByEmail(String email);
}

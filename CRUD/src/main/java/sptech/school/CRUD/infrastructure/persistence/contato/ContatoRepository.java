package sptech.school.CRUD.infrastructure.persistence.contato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.ContatoModel;

@Repository
public interface ContatoRepository extends ContatoEmailRepository{
    @Override
    boolean existsByEmail(String email);
}

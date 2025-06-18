package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.ContatoModel;

public interface ContatoRepository extends JpaRepository<ContatoModel, Integer> {
}

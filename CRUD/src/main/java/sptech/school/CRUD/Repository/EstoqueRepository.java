package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.EstoqueModel;

public interface EstoqueRepository extends JpaRepository<EstoqueModel, Integer> {
}

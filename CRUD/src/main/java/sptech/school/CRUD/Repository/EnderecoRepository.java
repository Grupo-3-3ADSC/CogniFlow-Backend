package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.EnderecoModel;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
}

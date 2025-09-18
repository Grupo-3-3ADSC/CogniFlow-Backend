package sptech.school.CRUD.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.EnderecoModel;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
}

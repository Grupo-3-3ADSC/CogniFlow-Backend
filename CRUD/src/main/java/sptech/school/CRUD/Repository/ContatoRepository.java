package sptech.school.CRUD.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.Model.ContatoModel;

public interface ContatoRepository extends JpaRepository<ContatoModel, Integer> {
    boolean existsByEmail(String email);
}

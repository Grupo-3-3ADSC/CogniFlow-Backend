package sptech.school.CRUD.infrastructure.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.UsuarioModel;

import java.util.Optional;

public interface UsuarioEmailRepository extends JpaRepository<UsuarioModel, Integer> {
    Optional<UsuarioModel> findByEmail(String email);
    boolean existsByEmail(String email);
}

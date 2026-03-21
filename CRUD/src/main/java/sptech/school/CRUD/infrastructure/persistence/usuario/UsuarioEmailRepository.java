package sptech.school.CRUD.infrastructure.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.CRUD.domain.entity.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface UsuarioEmailRepository extends JpaRepository<UsuarioModel, Integer> {
    Optional<UsuarioModel> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u.email FROM UsuarioModel u WHERE u.email IS NOT NULL")
    List<String> findAllEmails();
}

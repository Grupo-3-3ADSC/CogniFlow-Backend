package sptech.school.CRUD.infrastructure.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface DataUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    List<UsuarioModel> findByAtivoTrue();
    List<UsuarioModel> findByInativoTrue();
    boolean existsByEmail(String email);
    Optional<UsuarioModel> findByEmail(String email);
}

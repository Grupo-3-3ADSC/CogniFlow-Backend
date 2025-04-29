package sptech.school.CRUD_H2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD_H2.Model.UsuarioModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    List<UsuarioModel> findByAtivoTrue();
    Optional<UsuarioModel> findByEmail(String email);
}
    
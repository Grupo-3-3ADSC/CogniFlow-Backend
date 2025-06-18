package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.dto.Usuario.UsuarioAtualizadoDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    List<UsuarioModel> findByAtivoTrue();
    Optional<UsuarioModel> findByEmail(String email);

}
    
package sptech.school.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.dto.Usuario.UsuarioAtualizadoDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    @Query("SELECT u FROM UsuarioModel u JOIN FETCH u.cargo WHERE u.ativo = true")
    List<UsuarioModel> findByAtivoTrueComCargo();;

    Optional<UsuarioModel> findByEmail(String email);

    boolean existsByEmail(String email);
}
    
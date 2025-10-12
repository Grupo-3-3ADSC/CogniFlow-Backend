package sptech.school.CRUD.infrastructure.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.UsuarioModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends UsuarioAtivacaoRepository,UsuarioEmailRepository {
    @Query("SELECT u FROM UsuarioModel u JOIN FETCH u.cargo WHERE u.ativo = true")
    List<UsuarioModel> findByAtivoTrueComCargo();

    @Query("SELECT u FROM UsuarioModel u JOIN FETCH u.cargo WHERE u.ativo = false")
    List<UsuarioModel> findByAtivoFalseComCargo();

    @Override
    Optional<UsuarioModel> findByEmail(String email);

    @Override
    boolean existsByEmail(String email);

}
    
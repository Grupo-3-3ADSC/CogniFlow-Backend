package sptech.school.CRUD.domain.repository;

import sptech.school.CRUD.domain.entity.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    List<UsuarioModel> findByAtivoTrue();
    List<UsuarioModel> findByInativoTrue();
    List<UsuarioModel> findAll();
    Optional<UsuarioModel> findById(Integer id);
    void delete(UsuarioModel usuario);
    boolean existsByEmail(String email);
    Optional<UsuarioModel> findByEmail(String email);
}

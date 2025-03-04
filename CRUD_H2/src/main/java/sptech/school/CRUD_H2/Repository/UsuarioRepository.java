package sptech.school.CRUD_H2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD_H2.Model.UsuarioModel;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    List<UsuarioModel> findByAtivoTrue();
}

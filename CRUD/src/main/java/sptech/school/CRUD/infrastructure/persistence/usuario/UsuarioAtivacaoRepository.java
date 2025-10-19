package sptech.school.CRUD.infrastructure.persistence.usuario;

import sptech.school.CRUD.domain.entity.UsuarioModel;

import java.util.List;

public interface UsuarioAtivacaoRepository {
    List<UsuarioModel> findByAtivoTrueComCargo();
    List<UsuarioModel> findByAtivoFalseComCargo();
}

package sptech.school.CRUD.infrastructure.persistence.endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.EnderecoModel;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
    // SE IMPLEMENTAR MÉTODOS AQUI, FAVOR SEPARAR EM OUTRAS CLASSES E EXTENDER PARA NÃO ACOPLAR PROJETO AO SPRING
}

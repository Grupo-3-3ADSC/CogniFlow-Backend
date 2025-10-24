package sptech.school.CRUD.infrastructure.persistence.log;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.CRUD.domain.entity.LogModel;

public interface LogJpaRepository extends JpaRepository<LogModel, Integer> {
}

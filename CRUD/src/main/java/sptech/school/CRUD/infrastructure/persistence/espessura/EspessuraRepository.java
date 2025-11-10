package sptech.school.CRUD.infrastructure.persistence.espessura;

import sptech.school.CRUD.domain.entity.EspessuraModel;

import java.util.List;

public interface EspessuraRepository extends EspessuraJpaRepository{
    List<EspessuraModel> findByEstoqueModelId(Integer estoqueId);
}

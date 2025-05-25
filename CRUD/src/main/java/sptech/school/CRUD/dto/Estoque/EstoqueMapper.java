package sptech.school.CRUD.dto.Estoque;

import sptech.school.CRUD.Model.EstoqueModel;

public class EstoqueMapper {

    public static EstoqueListagemDto toListagemDto(EstoqueModel entity) {
        if (entity == null) {
            return null;
        }

        return new EstoqueListagemDto(
                entity.getTipoMaterial(),
                entity.getQuantidadeAtual(),
                entity.getQuantidadeMinima(),
                entity.getQuantidadeMaxima()
        );
    }

}

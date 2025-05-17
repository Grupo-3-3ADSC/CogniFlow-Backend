package sptech.school.CRUD.dto.Estoque;

import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.dto.Fornecedor.FornecedorListagemDto;

public class EstoqueMapper {

    public static EstoqueListagemDto toListagemDto(EstoqueModel entity){

        if(entity == null){
            return null;
        }

        return new EstoqueListagemDto(
                entity.getQuantidadeAtual(),
                entity.getQuantidadeMaxima(),
                entity.getQuantidadeMinima()
        );

    }
}

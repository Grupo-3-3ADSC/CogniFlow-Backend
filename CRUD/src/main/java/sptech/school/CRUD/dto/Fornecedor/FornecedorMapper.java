package sptech.school.CRUD.dto.Fornecedor;

import sptech.school.CRUD.Model.FornecedorModel;

public class FornecedorMapper {

    public static FornecedorListagemDto toListagemDto(FornecedorModel entity){

        if(entity == null){
            return null;
        }

        return new FornecedorListagemDto(
                entity.getNomeFantasia(),
                entity.getCnpj()
        );

    }
}

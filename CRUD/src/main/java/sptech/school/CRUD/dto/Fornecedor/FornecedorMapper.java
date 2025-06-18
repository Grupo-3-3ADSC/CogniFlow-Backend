package sptech.school.CRUD.dto.Fornecedor;

import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.FornecedorModel;

import java.time.LocalDateTime;

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
    public static FornecedorModel toCadastroModel(FornecedorCadastroDto dto) {
        if (dto == null) {
            return null;
        }


        FornecedorModel entity = new FornecedorModel();
        entity.setCnpj(dto.getCnpj());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setRazaoSocial(dto.getRazaoSocial());

        return entity;
    }
}

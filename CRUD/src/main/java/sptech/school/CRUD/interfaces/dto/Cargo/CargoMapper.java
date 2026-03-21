package sptech.school.CRUD.interfaces.dto.Cargo;

import sptech.school.CRUD.domain.entity.CargoModel;

public class CargoMapper {

    public static CargoListagemDto toListagemDto(CargoModel entity){

        if (entity == null){
            return null;
        }
        return CargoListagemDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();

    }

    public static CargoModel toCadastro(CargoCadastraDto dto){
        if(dto == null){
            return null;
        }

        CargoModel entity = new CargoModel();
        entity.setNome(dto.getNome());

        return entity;
    }

}

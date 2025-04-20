package sptech.school.CRUD_H2.dto;

import sptech.school.CRUD_H2.Model.UsuarioModel;

import java.util.List;

public class UsuarioMapper {

    public static UsuarioListagemDto toListagemDto(UsuarioModel entity){

        if (entity == null){
            return null;
        }
        return new UsuarioListagemDto(

                entity.getNome(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    public static List<UsuarioListagemDto> toListagemDtos(List<UsuarioModel> entities){
        if (entities == null){
            return null;
        }
        return entities.stream().map(UsuarioMapper::toListagemDto).toList();
    }

    public static UsuarioModel toEntity(UsuarioCadastroDto dto) {
        if (dto == null) {
            return null;
        }
        return new UsuarioModel(dto.getNome(), dto.getEmail(), dto.getPassword());
    }

    public static UsuarioModel toEntity(UsuarioAtualizadoDto dto, Integer id) {
        if (dto == null) {
            return null;
        }
        UsuarioModel model = new UsuarioModel(dto.getNome(), dto.getEmail(), dto.getPassword());
        model.setId(id);
        return model;
    }

}

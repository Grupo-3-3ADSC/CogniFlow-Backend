package sptech.school.CRUD.interfaces.dto.Log;

import sptech.school.CRUD.domain.entity.LogModel;

public class LogMapper {

    public static LogDto toDto(LogModel entity){
        if (entity == null) {
            return null;
        }

        return LogDto.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .fornecedorId(entity.getFornecedorId())
                .mensagem(entity.getMensagem())
                .build();
    }

    public static LogMensagemDto toMensagemDto(LogModel entity){
        if (entity == null) {
            return null;
        }

        return LogMensagemDto.builder()
                .mensagem(entity.getMensagem())
                .build();
    }
}

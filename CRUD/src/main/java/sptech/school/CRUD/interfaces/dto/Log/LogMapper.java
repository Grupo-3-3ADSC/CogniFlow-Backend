package sptech.school.CRUD.interfaces.dto.Log;

import sptech.school.CRUD.domain.entity.LogModel;

public class LogMapper {

    public static LogDto toDto(LogModel entity){
        if (entity == null) {
            return null;
        }

        return LogDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .mensagem(entity.getMensagem())
                .data(entity.getData())
                .build();
    }

    public static LogModel toEntity(LogDto dto){

        LogModel log = new LogModel();
        if(dto == null){
            return null;
        }

        log.setMensagem(dto.getMensagem());
        log.setData(dto.getData());

        return log;
    }

    public static LogMensagemDto toMensagemDto(LogModel entity){
        if (entity == null) {
            return null;
        }

        return LogMensagemDto.builder()
                .mensagem(entity.getMensagem())
                .data(entity.getData())
                .build();
    }
}

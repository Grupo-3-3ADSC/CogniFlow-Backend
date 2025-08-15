package sptech.school.CRUD.dto.Transferencia;

import sptech.school.CRUD.Model.TransferenciaModel;

public class TransferenciaMapper {

    public static TransferenciaDto toTransferenciaDto(TransferenciaModel entity){
        if (entity == null){
            return null;
        }

        return TransferenciaDto.builder()
                .id(entity.getId())
                .tipoTransferencia(entity.getTipoTransferencia())
                .build();
    }
}

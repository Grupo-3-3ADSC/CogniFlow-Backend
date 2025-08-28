package sptech.school.CRUD.dto.Transferencia;

import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.TransferenciaModel;
import sptech.school.CRUD.dto.Estoque.EstoqueCadastroDto;

import java.time.LocalDateTime;

public class TransferenciaMapper {

    public static TransferenciaListagemDto toTransferenciaListagemDto(TransferenciaModel entity){
        if (entity == null){
            return null;
        }

        return TransferenciaListagemDto.builder()
                .id(entity.getId())
                .ultimaMovimentacao(entity.getUltimaMovimentacao())
                .setor(entity.getSetor())
                .quantidadeTransferida(entity.getQuantidadeTransferida())
                .tipoMaterial(entity.getTipoMaterial())
                .build();
    }

    public static TransferenciaModel toTransferencia(TransferenciaDto dto) {
        if (dto == null) {
            return null;
        }

        TransferenciaModel entity = new TransferenciaModel();
        entity.setTipoMaterial(dto.getTipoMaterial());
        entity.setQuantidadeTransferida(dto.getQuantidadeTransferida());
        entity.setSetor(dto.getSetor());
        entity.setUltimaMovimentacao(LocalDateTime.now());

        return entity;
    }

    public static TransferenciaDto toTransferenciaDto(TransferenciaModel entity) {
        if (entity == null) return null;

        return TransferenciaDto.builder()
                .tipoMaterial(entity.getTipoMaterial())
                .quantidadeTransferida(entity.getQuantidadeTransferida())
                .setor(entity.getSetor())
                .ultimaMovimentacao(entity.getUltimaMovimentacao())
                .build();
    }

}

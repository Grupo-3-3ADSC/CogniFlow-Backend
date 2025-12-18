package sptech.school.CRUD.interfaces.dto.Transferencia;

import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.TransferenciaModel;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TransferenciaMapper {

    // Método atualizado que recebe o EstoqueModel como parâmetro
    public static TransferenciaModel toTransferencia(TransferenciaDto dto, EstoqueModel estoque) {
        if (dto == null) {
            return null;
        }

        TransferenciaModel entity = new TransferenciaModel();
        entity.setEstoque(estoque); // Usar o estoque fornecido
        entity.setQuantidadeTransferida(dto.getQuantidadeTransferida());
        entity.setSetor(dto.getSetor());
        entity.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        entity.setConfirmada(false);

        return entity;
    }

    // Método original sem estoque (pode manter para outros casos)
    public static TransferenciaModel toTransferencia(TransferenciaDto dto) {
        if (dto == null) {
            return null;
        }

        TransferenciaModel entity = new TransferenciaModel();
        // Não define estoque - deve ser definido externamente
        entity.setQuantidadeTransferida(dto.getQuantidadeTransferida());
        entity.setSetor(dto.getSetor());
        entity.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        entity.setConfirmada(false);

        return entity;
    }

    public static TransferenciaDto toTransferenciaDto(TransferenciaModel entity) {
        if (entity == null) return null;

        return TransferenciaDto.builder()
                .id(entity.getId())
                .tipoMaterial(entity.getEstoque() != null ? entity.getEstoque().getTipoMaterial() : null)
                .quantidadeTransferida(entity.getQuantidadeTransferida())
                .setor(entity.getSetor())
                .ultimaMovimentacao(entity.getUltimaMovimentacao())
                .confirmada(entity.getConfirmada())
                .build();
    }

    public static TransferenciaListagemDto toTransferenciaListagemDto(TransferenciaModel entity) {
        if (entity == null) return null;

        return TransferenciaListagemDto.builder()
                .id(entity.getId())
                .tipoMaterial(entity.getEstoque() != null ? entity.getEstoque().getTipoMaterial() : null)
                .quantidadeTransferida(entity.getQuantidadeTransferida())
                .setor(entity.getSetor())
                .ultimaMovimentacao(entity.getUltimaMovimentacao())
                .confirmada(entity.getConfirmada())
                .build();
    }
}
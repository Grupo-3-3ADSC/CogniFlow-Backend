package sptech.school.CRUD.dto.Estoque;

import sptech.school.CRUD.Model.EstoqueModel;

import java.time.LocalDateTime;

public class EstoqueMapper {

    public static EstoqueListagemDto toListagemDto(EstoqueModel entity) {
        if (entity == null) {
            return null;
        }

        return EstoqueListagemDto.builder()
                .tipoMaterial(entity.getTipoMaterial())
                .quantidadeAtual(entity.getQuantidadeAtual())
                .quantidadeMinima(entity.getQuantidadeMinima())
                .quantidadeMaxima(entity.getQuantidadeMaxima())
//                .tipoTransferencia(entity.getTipoTransferencia())
                .build();
    }
    public static EstoqueModel toEntity(EstoqueCadastroDto dto) {
        if (dto == null) {
            return null;
        }

        EstoqueModel entity = new EstoqueModel();
        entity.setTipoMaterial(dto.getTipoMaterial());
        entity.setQuantidadeAtual(dto.getQuantidadeAtual());
        entity.setQuantidadeMinima(dto.getQuantidadeMinima());
        entity.setQuantidadeMaxima(dto.getQuantidadeMaxima());
        entity.setUltimaMovimentacao(LocalDateTime.now());
//        entity.setTipoTransferencia(dto.getTipoTransferencia());

        return entity;
    }

}

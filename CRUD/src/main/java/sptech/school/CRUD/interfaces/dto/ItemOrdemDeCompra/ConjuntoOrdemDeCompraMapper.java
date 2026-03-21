package sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.ConjuntoOrdemDeCompraModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConjuntoOrdemDeCompraMapper {

    private final EstoqueRepository estoqueRepository;


    public static ConjuntoOrdemDeCompraListagemDto toListagemDto(ConjuntoOrdemDeCompraModel conjunto) {
        ConjuntoOrdemDeCompraListagemDto dto = new ConjuntoOrdemDeCompraListagemDto();
        dto.setId(conjunto.getId());
        dto.setOrdensDeCompra(
                conjunto.getOrdensDeCompra().stream()
                        .map(OrdemDeCompraMapper::toListagemDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    // Converte um DTO para ConjuntoOrdemDeCompraModel
    public ConjuntoOrdemDeCompraModel toModel(ConjuntoOrdemDeCompraDTO dto, List<OrdemDeCompraModel> ordens) {
        ConjuntoOrdemDeCompraModel model = new ConjuntoOrdemDeCompraModel();
        model.setId(dto.getId());
        model.setOrdensDeCompra(ordens);
        return model;
    }
}

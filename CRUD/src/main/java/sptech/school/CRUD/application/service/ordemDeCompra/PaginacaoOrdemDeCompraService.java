package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.infrastructure.persistence.OrdemDeCompraRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.PaginacaoHistoricoOrdemDeCompraDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaginacaoOrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;

    public PaginacaoHistoricoOrdemDeCompraDTO ordemDeCompraPaginada(Integer pagina, Integer tamanho){
        Pageable pageable = PageRequest.of(pagina, tamanho);

        Page<OrdemDeCompraModel> ordens = ordemDeCompraRepository.findOrdensDeCompraPaginadas(pageable);

        List<ListagemOrdemDeCompra> ordensMapeadas = ordens.getContent()
                .stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());

        PaginacaoHistoricoOrdemDeCompraDTO response = new PaginacaoHistoricoOrdemDeCompraDTO();
        response.setData(ordensMapeadas);
        response.setPaginaAtual(pagina);
        response.setPaginasTotais(ordens.getTotalPages());
        response.setTotalItems(ordens.getTotalElements());
        response.setHasNext(ordens.hasNext());
        response.setHasPrevious(ordens.hasPrevious());
        response.setPageSize(tamanho);

        return response;
    }
}

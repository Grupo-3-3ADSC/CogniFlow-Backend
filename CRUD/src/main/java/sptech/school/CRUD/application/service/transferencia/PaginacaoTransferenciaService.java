package sptech.school.CRUD.application.service.transferencia;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.TransferenciaModel;
import sptech.school.CRUD.infrastructure.persistence.TransferenciaRepository;
import sptech.school.CRUD.interfaces.dto.Transferencia.PaginacaoHistoricoTransferencia;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaListagemDto;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaginacaoTransferenciaService {

    private final TransferenciaRepository repository;

    public PaginacaoHistoricoTransferencia TransferenciaPaginada(Integer pagina, Integer tamanho){
        Pageable pageable = PageRequest.of(pagina, tamanho);

        Page<TransferenciaModel> transferencias = repository.findTransferenciasPaginadas(pageable);

        List<TransferenciaListagemDto> transferenciasMapeadas = transferencias.getContent()
                .stream()
                .map(TransferenciaMapper::toTransferenciaListagemDto)
                .collect(Collectors.toList());

        PaginacaoHistoricoTransferencia response = new PaginacaoHistoricoTransferencia();
        response.setData(transferenciasMapeadas);
        response.setPaginaAtual(pagina);
        response.setPaginasTotais(transferencias.getTotalPages());
        response.setTotalItems(transferencias.getTotalElements());
        response.setHasNext(transferencias.hasNext());
        response.setHasPrevious(transferencias.hasPrevious());
        response.setPageSize(tamanho);

        return response;
    }
}

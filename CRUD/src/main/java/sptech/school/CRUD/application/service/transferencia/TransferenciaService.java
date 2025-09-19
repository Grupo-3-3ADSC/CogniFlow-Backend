package sptech.school.CRUD.application.service.transferencia;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.TransferenciaModel;
import sptech.school.CRUD.domain.repository.EstoqueRepository;
import sptech.school.CRUD.domain.repository.TransferenciaRepository;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaListagemDto;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaMapper;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service

public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;

    // Listar todas as transferências
    public List<TransferenciaListagemDto> buscarSetor() {
        return transferenciaRepository.findAll().stream()
                .map(TransferenciaMapper::toTransferenciaListagemDto)
                .collect(Collectors.toList());
    }

    public List<TransferenciaListagemDto> buscarPorMaterialEano(String tipoMaterial, Integer ano) {
        List<TransferenciaModel> transferencias;

        if (ano == null) {
            transferencias = transferenciaRepository.findByTipoMaterial(tipoMaterial);
        } else {
            transferencias = transferenciaRepository.findByTipoMaterialAndAno(tipoMaterial, ano);
        }

        return transferencias.stream()
                .map(TransferenciaMapper::toTransferenciaListagemDto)
                .collect(Collectors.toList());
    }



}

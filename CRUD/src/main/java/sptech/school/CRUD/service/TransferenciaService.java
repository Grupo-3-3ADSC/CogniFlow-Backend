package sptech.school.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.TransferenciaModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.TransferenciaRepository;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;
import sptech.school.CRUD.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.dto.Transferencia.TransferenciaListagemDto;
import sptech.school.CRUD.dto.Transferencia.TransferenciaMapper;
import sptech.school.CRUD.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.exception.RequisicaoInvalidaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
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

    // Realizar uma transferência
    public TransferenciaDto realizarTransferencia(TransferenciaDto dto) {
        String tipoMaterial = dto.getTipoMaterial();
        Integer quantidadeTransferida = dto.getQuantidadeTransferida();

        if (tipoMaterial == null || tipoMaterial.isBlank()) {
            throw new RequisicaoInvalidaException("O tipo do material não pode ser nulo ou vazio.");
        }
        if (quantidadeTransferida == null || quantidadeTransferida <= 0) {
            throw new RequisicaoInvalidaException("A quantidade transferida deve ser maior que zero.");
        }

        // Converte DTO para entidade
        TransferenciaModel transferencia = TransferenciaMapper.toTransferencia(dto);

        // Salva no banco
        TransferenciaModel salvo = transferenciaRepository.save(transferencia);

        // Retorna DTO
        return TransferenciaMapper.toTransferenciaDto(salvo);
    }
}

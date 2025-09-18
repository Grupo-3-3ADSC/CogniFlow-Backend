package sptech.school.CRUD.application.service;

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
    private final EstoqueRepository estoqueRepository;

    // Listar todas as transferências
    public List<TransferenciaListagemDto> buscarSetor() {
        return transferenciaRepository.findAll().stream()
                .map(TransferenciaMapper::toTransferenciaListagemDto)
                .collect(Collectors.toList());
    }

    // Realizar uma transferência e atualizar o estoque
    @Transactional
    public TransferenciaDto realizarTransferencia(TransferenciaDto dto) {
        String tipoMaterial = dto.getTipoMaterial();
        Integer quantidadeTransferida = dto.getQuantidadeTransferida();

        if (tipoMaterial == null || tipoMaterial.isBlank()) {
            throw new RequisicaoInvalidaException("O tipo do material não pode ser nulo ou vazio.");
        }
        if (quantidadeTransferida == null || quantidadeTransferida <= 0) {
            throw new RequisicaoInvalidaException("A quantidade transferida deve ser maior que zero.");
        }

        // Buscar o material no estoque
        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material não encontrado no estoque"));

        // Verificar se há quantidade suficiente
        if (estoque.getQuantidadeAtual() < quantidadeTransferida) {
            throw new RequisicaoInvalidaException("Quantidade insuficiente no estoque");
        }

        // Atualizar o estoque
        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - quantidadeTransferida);
        estoque.setUltimaMovimentacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

        // Criar a transferência manualmente associando o estoque
        TransferenciaModel transferencia = new TransferenciaModel();
        transferencia.setEstoque(estoque); // CRUCIAL: associar o estoque salvo
        transferencia.setQuantidadeTransferida(quantidadeTransferida);
        transferencia.setSetor(dto.getSetor());
        transferencia.setUltimaMovimentacao(LocalDateTime.now());

        // Salvar a transferência
        TransferenciaModel salvo = transferenciaRepository.save(transferencia);

        // Retornar DTO
        return TransferenciaMapper.toTransferenciaDto(salvo);
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

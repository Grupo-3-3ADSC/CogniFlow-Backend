package sptech.school.CRUD.application.service.transferencia;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.TransferenciaModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.transferencia.TransferenciaRepository;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.interfaces.dto.Transferencia.TransferenciaMapper;

import java.time.LocalDateTime;
@Service
public class RealizarTransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final EstoqueRepository estoqueRepository;
    public RealizarTransferenciaService(TransferenciaRepository transferenciaRepository,
                                        EstoqueRepository estoqueRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.estoqueRepository = estoqueRepository;
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
        transferencia.setTipoMaterial(tipoMaterial);

        // Salvar a transferência
        TransferenciaModel salvo = transferenciaRepository.save(transferencia);

        // Retornar DTO
        return TransferenciaMapper.toTransferenciaDto(salvo);
    }

}

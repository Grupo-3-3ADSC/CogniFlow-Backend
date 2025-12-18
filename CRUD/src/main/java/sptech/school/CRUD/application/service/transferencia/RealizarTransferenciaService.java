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
import java.time.temporal.ChronoUnit;

@Service
public class RealizarTransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final EstoqueRepository estoqueRepository;

    public RealizarTransferenciaService(TransferenciaRepository transferenciaRepository,
                                        EstoqueRepository estoqueRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.estoqueRepository = estoqueRepository;
    }

    // Realizar uma transferência - subtrai da quantidade e adiciona ao setor imediatamente
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

        // Validar setor
        String setor = dto.getSetor();
        if (setor == null || !setor.matches("(?i)^(G1|G2|G3|G4)$")) {
            throw new RequisicaoInvalidaException("Setor inválido. Use G1, G2, G3 ou G4.");
        }

        // Buscar o material no estoque
        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material não encontrado no estoque"));

        Integer quantidadeAtual = estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0;

        // Verificar se há quantidade suficiente
        if (quantidadeAtual < quantidadeTransferida) {
            throw new RequisicaoInvalidaException("Quantidade insuficiente no estoque");
        }

        // SUBTRAIR da quantidade atual do estoque
        estoque.setQuantidadeAtual(quantidadeAtual - quantidadeTransferida);

        // ADICIONAR ao setor correto (G1, G2, G3 ou G4)
        String setorUpper = setor.toUpperCase();
        switch (setorUpper) {
            case "G1":
                Integer g1Atual = estoque.getG1() != null ? estoque.getG1() : 0;
                estoque.setG1(g1Atual + quantidadeTransferida);
                break;
            case "G2":
                Integer g2Atual = estoque.getG2() != null ? estoque.getG2() : 0;
                estoque.setG2(g2Atual + quantidadeTransferida);
                break;
            case "G3":
                Integer g3Atual = estoque.getG3() != null ? estoque.getG3() : 0;
                estoque.setG3(g3Atual + quantidadeTransferida);
                break;
            case "G4":
                Integer g4Atual = estoque.getG4() != null ? estoque.getG4() : 0;
                estoque.setG4(g4Atual + quantidadeTransferida);
                break;
            default:
                throw new RequisicaoInvalidaException("Setor inválido: " + setorUpper);
        }

        estoque.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        estoqueRepository.save(estoque);

        // Criar a transferência como já confirmada
        TransferenciaModel transferencia = new TransferenciaModel();
        transferencia.setEstoque(estoque);
        transferencia.setQuantidadeTransferida(quantidadeTransferida);
        transferencia.setSetor(setorUpper);
        transferencia.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        transferencia.setTipoMaterial(tipoMaterial);
        transferencia.setConfirmada(true);

        // Salvar a transferência
        TransferenciaModel salvo = transferenciaRepository.save(transferencia);

        // Retornar DTO
        return TransferenciaMapper.toTransferenciaDto(salvo);
    }

    // Confirmar uma transferência pendente (caso ainda exista alguma pendente)
    @Transactional
    public TransferenciaDto confirmarTransferencia(Integer transferenciaId) {
        TransferenciaModel transferencia = transferenciaRepository.findById(transferenciaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transferência não encontrada"));

        if (transferencia.getConfirmada() != null && transferencia.getConfirmada()) {
            throw new RequisicaoInvalidaException("Transferência já foi confirmada");
        }

        // Buscar o estoque DIRETAMENTE do banco para garantir dados atualizados
        EstoqueModel estoque = estoqueRepository.findById(transferencia.getEstoque().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));

        Integer quantidadeTransferida = transferencia.getQuantidadeTransferida();
        Integer quantidadeAtual = estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0;

        // Verificar se ainda há quantidade suficiente no estoque
        if (quantidadeAtual < quantidadeTransferida) {
            throw new RequisicaoInvalidaException("Quantidade insuficiente no estoque para confirmar");
        }

        // Subtrair da quantidade atual do estoque
        estoque.setQuantidadeAtual(quantidadeAtual - quantidadeTransferida);

        // Adicionar ao setor correto (G1, G2, G3 ou G4)
        String setor = transferencia.getSetor().toUpperCase();
        switch (setor) {
            case "G1":
                Integer g1Atual = estoque.getG1() != null ? estoque.getG1() : 0;
                estoque.setG1(g1Atual + quantidadeTransferida);
                break;
            case "G2":
                Integer g2Atual = estoque.getG2() != null ? estoque.getG2() : 0;
                estoque.setG2(g2Atual + quantidadeTransferida);
                break;
            case "G3":
                Integer g3Atual = estoque.getG3() != null ? estoque.getG3() : 0;
                estoque.setG3(g3Atual + quantidadeTransferida);
                break;
            case "G4":
                Integer g4Atual = estoque.getG4() != null ? estoque.getG4() : 0;
                estoque.setG4(g4Atual + quantidadeTransferida);
                break;
            default:
                throw new RequisicaoInvalidaException("Setor inválido: " + setor);
        }

        estoque.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        estoqueRepository.save(estoque);

        // Marcar transferência como confirmada
        transferencia.setConfirmada(true);
        transferencia.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        TransferenciaModel salvo = transferenciaRepository.save(transferencia);

        return TransferenciaMapper.toTransferenciaDto(salvo);
    }
}

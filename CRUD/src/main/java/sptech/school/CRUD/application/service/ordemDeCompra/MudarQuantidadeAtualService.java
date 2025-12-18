package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.MudarQuantidadeAtualDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MudarQuantidadeAtualService {

    private final EstoqueRepository estoqueRepository;
    private final OrdemDeCompraRepository ordemDeCompraRepository;

    /**
     * Confirma uma ordem de compra pendente.
     * Move a quantidade do PENDENTE para a QUANTIDADE ATUAL do estoque.
     */
    public OrdemDeCompraModel mudarQuantidadeAtual(Integer id, MudarQuantidadeAtualDto dto) {
        // Buscar a ordem de compra existente
        OrdemDeCompraModel ordemDeCompra = ordemDeCompraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de compra não encontrada"));

        // Buscar o estoque relacionado
        EstoqueModel estoque = estoqueRepository.findById(ordemDeCompra.getEstoqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));

        Integer quantidade = dto.getQuantidade();
        Integer pendenteAtual = Optional.ofNullable(estoque.getPendente()).orElse(0);
        Integer quantidadeAtual = Optional.ofNullable(estoque.getQuantidadeAtual()).orElse(0);

        // Se a ordem já foi confirmada (pendenciaAlterada = true), desfaz a confirmação
        if (ordemDeCompra.getPendenciaAlterada()) {
            // Desfazer: remove da quantidade atual e volta para o pendente
            estoque.setQuantidadeAtual(quantidadeAtual - quantidade);
            estoque.setPendente(pendenteAtual + quantidade);
            ordemDeCompra.setPendenciaAlterada(false);
        } else {
            // Confirmar: verifica se tem pendente suficiente
            if (pendenteAtual < quantidade) {
                throw new RequisicaoInvalidaException("Quantidade pendente insuficiente para confirmar");
            }

            // Move do pendente para a quantidade atual
            estoque.setPendente(pendenteAtual - quantidade);
            estoque.setQuantidadeAtual(quantidadeAtual + quantidade);
            ordemDeCompra.setPendenciaAlterada(true);
        }

        estoque.setUltimaMovimentacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        estoqueRepository.save(estoque);

        return ordemDeCompraRepository.save(ordemDeCompra);
    }
}

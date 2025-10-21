package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.ItemOrdemCompraModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.MudarQuantidadeAtualDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MudarQuantidadeAtualService {

    private final EstoqueRepository estoqueRepository;
    private final OrdemDeCompraRepository ordemDeCompraRepository;

    public OrdemDeCompraModel mudarQuantidadeAtual(Integer id, MudarQuantidadeAtualDto dto) {
        // Buscar a ordem de compra existente
        OrdemDeCompraModel ordemDeCompra = ordemDeCompraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de compra não encontrada"));

        // Atualizar os campos necessários
        //ordemDeCompra.setPendentes(dto.getPendentes());

        // Atualizar o estoque, se necessário
        EstoqueModel estoque = estoqueRepository.findById(ordemDeCompra.getEstoqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));

        Integer quantidadeAtual = Optional.ofNullable(estoque.getQuantidadeAtual()).orElse(0);

        if(ordemDeCompra.getPendenciaAlterada()){
            estoque.setQuantidadeAtual(quantidadeAtual - dto.getQuantidade());
            ordemDeCompra.setPendenciaAlterada(false);
            estoqueRepository.save(estoque);

            return ordemDeCompraRepository.save(ordemDeCompra);
        }

        estoque.setQuantidadeAtual(quantidadeAtual + dto.getQuantidade());
        ordemDeCompra.setPendenciaAlterada(true);
        estoqueRepository.save(estoque);

        return ordemDeCompraRepository.save(ordemDeCompra);
    }
}

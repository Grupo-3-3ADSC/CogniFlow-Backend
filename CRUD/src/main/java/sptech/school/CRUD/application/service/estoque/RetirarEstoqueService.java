package sptech.school.CRUD.application.service.estoque;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.EstoqueRepository;
import sptech.school.CRUD.interfaces.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.interfaces.dto.Estoque.EstoqueMapper;
import sptech.school.CRUD.interfaces.dto.Estoque.RetirarEstoqueDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RetirarEstoqueService {

    @Autowired
    private final EstoqueRepository estoqueRepository;

    public EstoqueListagemDto retirarEstoque(RetirarEstoqueDto dto) {
        String tipoMaterial = dto.getTipoMaterial();
        Integer quantidadeAtual = dto.getQuantidadeAtual();
        String tipoTransferencia = dto.getTipoTransferencia();

        if (dto.getTipoMaterial() == null || dto.getTipoMaterial().isBlank()){
            throw new RequisicaoInvalidaException("O tipo do material não pode ser nulo ou vazio.");
        }

        if (quantidadeAtual == null || quantidadeAtual <= 0) {
            throw new RequisicaoInvalidaException("A quantidade a ser retirada deve ser maior que zero.");
        }

        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material não encontrado no estoque: " + tipoMaterial));

        if (estoque.getQuantidadeAtual() < quantidadeAtual) {
            throw new RequisicaoInvalidaException("Quantidade insuficiente no estoque. Disponível: " +
                    estoque.getQuantidadeAtual() + ", Solicitado: " + quantidadeAtual);
        }

        // Atualiza a quantidade atual
        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - quantidadeAtual);
        estoque.setUltimaMovimentacao(LocalDateTime.now());

        // Incrementa o contador baseado no tipo de transferência
//        if ("Externa".equalsIgnoreCase(tipoTransferencia)) {
//            estoque.setExterno(estoque.getExterno() != null ? estoque.getExterno() + 1 : 1);
//            System.out.println("Incrementando contador externo para: " + estoque.getExterno());
//        } else if ("Interna".equalsIgnoreCase(tipoTransferencia)) {
//            estoque.setInterno(estoque.getInterno() != null ? estoque.getInterno() + 1 : 1);
//            System.out.println("Incrementando contador interno para: " + estoque.getInterno());
//        } else {
//            throw new IllegalArgumentException("Transferência deve ser do tipo 'INTERNA' ou 'EXTERNA'. Valor recebido: " + tipoTransferencia);
//        }

        EstoqueModel atualizado = estoqueRepository.save(estoque);
        return EstoqueMapper.toListagemDto(atualizado);
    }
}

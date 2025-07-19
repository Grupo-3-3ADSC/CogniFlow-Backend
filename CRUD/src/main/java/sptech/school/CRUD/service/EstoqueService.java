package sptech.school.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.AtualizarEstoqueDto;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;
import sptech.school.CRUD.dto.Estoque.RetirarEstoqueDto;
import sptech.school.CRUD.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.exception.RequisicaoInvalidaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
@Transactional
public class EstoqueService {

    @Autowired
    private final EstoqueRepository estoqueRepository;


    public List<EstoqueListagemDto> buscarEstoque() {
        return estoqueRepository.findAll().stream()
                .map(EstoqueMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    public EstoqueListagemDto atualizarEstoque(AtualizarEstoqueDto dto) {
        String tipoMaterial = dto.getTipoMaterial();
        Integer quantidade = dto.getQuantidadeAtual();

        // Validação: garantir que o tipoMaterial seja um dos pré-cadastrados
        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tipo de material não encontrado: " + tipoMaterial
                ));

        Integer novaQuantidade = estoque.getQuantidadeAtual() + quantidade;
        Integer max = estoque.getQuantidadeMaxima();
        Integer min = estoque.getQuantidadeMinima();

        if (max != null && novaQuantidade > max) {
            throw new RequisicaoInvalidaException("Quantidade Atual excede a Quantidade Máxima permitida");
        }
        if (min != null && novaQuantidade < min) {
            throw new RequisicaoInvalidaException("Quantidade Atual está abaixo da Quantidade Mínima permitida");
        }

        estoque.setQuantidadeAtual(novaQuantidade);
        estoque.setUltimaMovimentacao(LocalDateTime.now());

        EstoqueModel salvo = estoqueRepository.save(estoque);
        return EstoqueMapper.toListagemDto(salvo);
    }


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
        if ("Externa".equalsIgnoreCase(tipoTransferencia)) {
            estoque.setExterno(estoque.getExterno() != null ? estoque.getExterno() + 1 : 1);
            System.out.println("Incrementando contador externo para: " + estoque.getExterno());
        } else if ("Interna".equalsIgnoreCase(tipoTransferencia)) {
            estoque.setInterno(estoque.getInterno() != null ? estoque.getInterno() + 1 : 1);
            System.out.println("Incrementando contador interno para: " + estoque.getInterno());
        } else {
            throw new IllegalArgumentException("Transferência deve ser do tipo 'INTERNA' ou 'EXTERNA'. Valor recebido: " + tipoTransferencia);
        }

        EstoqueModel atualizado = estoqueRepository.save(estoque);
        return EstoqueMapper.toListagemDto(atualizado);
    }

}

package sptech.school.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

        if (tipoMaterial == null || tipoMaterial.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de material não pode ser nulo ou vazio");
        }
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        Optional<EstoqueModel> estoqueOpt = estoqueRepository.findByTipoMaterial(tipoMaterial);

        EstoqueModel estoque = estoqueOpt.orElseGet(() -> {
            EstoqueModel novo = new EstoqueModel();
            novo.setTipoMaterial(tipoMaterial);
            novo.setQuantidadeAtual(0); // começa do zero, vai somar abaixo
            novo.setQuantidadeMinima(10);
            novo.setQuantidadeMaxima(1000);
            novo.setInterno(0);
            novo.setExterno(0);
            return novo;
        });

        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + quantidade);
        estoque.setUltimaMovimentacao(LocalDateTime.now());

        EstoqueModel salvo = estoqueRepository.save(estoque);
        return EstoqueMapper.toListagemDto(salvo);
    }


    public EstoqueListagemDto retirarEstoque(RetirarEstoqueDto dto) {
        String tipoMaterial = dto.getTipoMaterial();
        Integer quantidadeAtual = dto.getQuantidadeAtual();
        String tipoTransferencia = dto.getTipoTransferencia();

        if (tipoMaterial == null || tipoMaterial.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de material não pode ser nulo ou vazio");
        }
        if (quantidadeAtual == null || quantidadeAtual <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        if (tipoTransferencia == null || tipoTransferencia.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de transferência não pode ser nulo ou vazio");
        }

        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RuntimeException("Material não encontrado no estoque: " + tipoMaterial));

        if (estoque.getQuantidadeAtual() < quantidadeAtual) {
            throw new RuntimeException("Quantidade insuficiente no estoque. Disponível: " +
                    estoque.getQuantidadeAtual() + ", Solicitado: " + quantidadeAtual);
        }

        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - quantidadeAtual);
        estoque.setUltimaMovimentacao(LocalDateTime.now());

        if ("Externa".equalsIgnoreCase(tipoTransferencia)) {
            estoque.setExterno(estoque.getExterno() != null ? estoque.getExterno() + 1 : 1);
        } else if ("Interna".equalsIgnoreCase(tipoTransferencia)) {
            estoque.setInterno(estoque.getInterno() != null ? estoque.getInterno() + 1 : 1);
        } else {
            throw new IllegalArgumentException("Tipo de transferência inválido: " + tipoTransferencia);
        }

        EstoqueModel atualizado = estoqueRepository.save(estoque);
        return EstoqueMapper.toListagemDto(atualizado);
    }

}

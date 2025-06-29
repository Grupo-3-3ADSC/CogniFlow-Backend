package sptech.school.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;

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


    public EstoqueModel cadastroEstoque(EstoqueModel estoque){
        return estoqueRepository.save(estoque);
    }



        public EstoqueModel atualizarEstoque(String tipoMaterial, Integer quantidade
        ) {
            // Validações
            if (tipoMaterial == null || tipoMaterial.trim().isEmpty()) {
                throw new IllegalArgumentException("Tipo de material não pode ser nulo ou vazio");
            }
            if (quantidade == null) {
                throw new IllegalArgumentException("Quantidade não pode ser nula");
            }

            Optional<EstoqueModel> estoqueOpt = estoqueRepository.findByTipoMaterial(tipoMaterial);

            EstoqueModel estoque;
            if (estoqueOpt.isPresent()) {
                // Atualiza estoque existente
                estoque = estoqueOpt.get();
                estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + quantidade);
            } else {
                // Cria novo estoque
                estoque = new EstoqueModel();
                estoque.setTipoMaterial(tipoMaterial);
                estoque.setQuantidadeAtual(quantidade);
                estoque.setQuantidadeMinima(10);
                estoque.setQuantidadeMaxima(1000);
                estoque.setInterno(0);
                estoque.setExterno(0);
            }


            estoque.setUltimaMovimentacao(LocalDateTime.now());
            return estoqueRepository.save(estoque);
        }


    public EstoqueModel retirarEstoque(String tipoMaterial, Integer quantidadeAtual, String tipoTransferencia) {
        System.out.println("=== DEBUG SERVICE ===");
        System.out.println("tipoMaterial: " + tipoMaterial);
        System.out.println("quantidadeAtual: " + quantidadeAtual);
        System.out.println("tipoTransferencia: " + tipoTransferencia);

        if (tipoMaterial == null || tipoMaterial.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de material não pode ser nulo ou vazio");
        }
        if (quantidadeAtual == null || quantidadeAtual <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        if (tipoTransferencia == null || tipoTransferencia.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de transferência não pode ser nulo ou vazio");
        }

        Optional<EstoqueModel> estoqueOpt = estoqueRepository.findByTipoMaterial(tipoMaterial);

        if (estoqueOpt.isEmpty()) {
            throw new RuntimeException("Material não encontrado no estoque: " + tipoMaterial);
        }

        EstoqueModel estoque = estoqueOpt.get();

        if (estoque.getQuantidadeAtual() < quantidadeAtual) {
            throw new RuntimeException("Quantidade insuficiente no estoque. Disponível: " +
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
            throw new IllegalArgumentException("Tipo de transferência inválido: " + tipoTransferencia);
        }

        EstoqueModel estoqueAtualizado = estoqueRepository.save(estoque);
        System.out.println("Estoque salvo com sucesso: " + estoqueAtualizado.getId());

        return estoqueAtualizado;
    }
}

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


    public List<EstoqueModel> buscarEstoque(){
        return estoqueRepository.findAll();
    }


    public EstoqueModel cadastroEstoque(EstoqueModel estoque){
        return estoqueRepository.save(estoque);
    }



        public EstoqueModel atualizarEstoque(String tipoMaterial, Integer quantidade) {
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
                estoque.setQuantidadeMinima(10); // Valor padrão
                estoque.setQuantidadeMaxima(1000); // Valor padrão
            }

            estoque.setUltimaMovimentacao(LocalDateTime.now());
            return estoqueRepository.save(estoque);
        }

        public EstoqueModel retirarEstoque(String tipoMaterial, Integer quantidade) {
            if (tipoMaterial == null || tipoMaterial.trim().isEmpty()) {
                throw new IllegalArgumentException("Tipo de material não pode ser nulo ou vazio");
            }
            if (quantidade == null || quantidade <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser positiva");
            }

            Optional<EstoqueModel> estoqueOpt = estoqueRepository.findByTipoMaterial(tipoMaterial);

            if (estoqueOpt.isEmpty()) {
                throw new RuntimeException("Material não encontrado no estoque: " + tipoMaterial);
            }

            EstoqueModel estoque = estoqueOpt.get();

            if (estoque.getQuantidadeAtual() < quantidade) {
                throw new RuntimeException("Quantidade insuficiente no estoque. Disponível: " +
                        estoque.getQuantidadeAtual() + ", Solicitado: " + quantidade);
            }

            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - quantidade);
            estoque.setUltimaMovimentacao(LocalDateTime.now());

            return estoqueRepository.save(estoque);
        }





}

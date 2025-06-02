package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.OrdemDeCompraRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;

    public OrdemDeCompraModel cadastroOrdemDeCompra(OrdemDeCompraModel ordemDeCompra){

        OrdemDeCompraModel ordemSalva = ordemDeCompraRepository.save(ordemDeCompra);


        EstoqueModel estoque = ordemSalva.getEstoque();

        if (estoque != null){
            Integer novaQuantidade = (estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0)
                    + ordemSalva.getQuantidade();
            estoque.setQuantidadeAtual(novaQuantidade);
            estoque.setUltimaMovimentacao(LocalDateTime.now());

            estoqueRepository.save(estoque);
        }
        return ordemSalva;
    }

    private void atulizarEstoque(String tipoMaterial, Integer quantidade){
        Optional<EstoqueModel> estoqueOpt = estoqueRepository.findByTipoMaterial(tipoMaterial);

        EstoqueModel estoque;
        if (estoqueOpt.isPresent()){

            estoque = estoqueOpt.get();
            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + quantidade);
            return;
        }else {
            estoque = new EstoqueModel();
            estoque.setTipoMaterial(tipoMaterial);
            estoque.setQuantidadeAtual(quantidade);
        }
        estoque.setUltimaMovimentacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

    }




}

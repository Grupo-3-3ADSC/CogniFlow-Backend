package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.OrdemDeCompraRepository;

import java.time.LocalDateTime;

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




}

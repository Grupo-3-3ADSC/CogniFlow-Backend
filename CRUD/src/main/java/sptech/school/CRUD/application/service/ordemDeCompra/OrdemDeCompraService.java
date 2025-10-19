package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final FornecedorRepository fornecedorRepository;

    public List<ListagemOrdemDeCompra> getAll() {
        return ordemDeCompraRepository.findAll()
                .stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    public OrdemDeCompraModel getById(Integer id) {
        return ordemDeCompraRepository.findByIdComJoins(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de compra não encontrada"));
    }

    public ListagemOrdemDeCompra buscarPorIdDto(Integer id) {
        return ordemDeCompraRepository.findByIdComJoinsDTO(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de compra não encontrada"));
    }


    public List<ListagemOrdemDeCompra> getByMaterial(Integer estoqueId, Integer ano) {
        List<OrdemDeCompraModel> ordens;

        if (ano != null) {
            ordens = ordemDeCompraRepository.findByEstoqueIdAndAno(estoqueId, ano);
        } else {
            ordens = ordemDeCompraRepository.findByEstoqueId(estoqueId);
        }

        return ordens.stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .toList();
    }


    public List<ListagemOrdemDeCompra> getRelatorioFornecedor(Integer fornecedorId, Integer ano) {
        // Verifica se o fornecedor existe
        fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));

        List<OrdemDeCompraModel> ordens = ordemDeCompraRepository.findByFornecedorIdAndAno(fornecedorId, ano);

        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem encontrada para fornecedor " + fornecedorId + " no ano " + ano);
        }

        return ordens.stream()
                .map(OrdemDeCompraMapper::toListagemDto) // converte para DTO
                .toList();
    }


}

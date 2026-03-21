package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Otimização para operações de leitura
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final FornecedorRepository fornecedorRepository;

    /**
     * Lista todas as ordens de compra
     * @return Lista de DTOs com todas as ordens
     */
    public List<ListagemOrdemDeCompra> listarTodas() {
        log.debug("Listando todas as ordens de compra");
        return ordemDeCompraRepository.findAll()
                .stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca ordem de compra por ID (retorna entidade)
     * @param id ID da ordem de compra
     * @return Entidade OrdemDeCompraModel
     * @throws RecursoNaoEncontradoException se não encontrar
     */
    public OrdemDeCompraModel getById(Integer id) {
        log.debug("Buscando ordem de compra com ID: {}", id);
        return ordemDeCompraRepository.findByIdComJoins(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        String.format("Ordem de compra com ID %d não encontrada", id)));
    }

    /**
     * Busca ordem de compra por ID (retorna DTO)
     * @param id ID da ordem de compra
     * @return DTO ListagemOrdemDeCompra
     * @throws RecursoNaoEncontradoException se não encontrar
     */
    public ListagemOrdemDeCompra buscarPorIdDto(Integer id) {
        log.debug("Buscando ordem de compra (DTO) com ID: {}", id);
        return ordemDeCompraRepository.findByIdComJoinsDTO(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        String.format("Ordem de compra com ID %d não encontrada", id)));
    }

    /**
     * Lista ordens de compra filtradas por material (estoque)
     * @param estoqueId ID do estoque/material
     * @param ano Ano para filtrar (opcional)
     * @return Lista de ordens filtradas
     */
    public List<ListagemOrdemDeCompra> getByMaterial(Integer estoqueId, Integer ano) {
        log.debug("Buscando ordens de compra por material. EstoqueId: {}, Ano: {}", estoqueId, ano);

        List<OrdemDeCompraModel> ordens;

        if (ano != null) {
            ordens = ordemDeCompraRepository.findByEstoqueIdAndAno(estoqueId, ano);
        } else {
            ordens = ordemDeCompraRepository.findByEstoqueId(estoqueId);
        }

        log.info("Encontradas {} ordens para o estoque ID: {}", ordens.size(), estoqueId);

        return ordens.stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    /**
     * Gera relatório de ordens de compra por fornecedor e ano
     * @param fornecedorId ID do fornecedor
     * @param ano Ano para o relatório
     * @return Lista de ordens do fornecedor no ano especificado
     * @throws RecursoNaoEncontradoException se o fornecedor não existir
     */
    public List<ListagemOrdemDeCompra> getRelatorioFornecedor(Integer fornecedorId, Integer ano) {
        log.debug("Gerando relatório de fornecedor. FornecedorId: {}, Ano: {}", fornecedorId, ano);

        // Valida se o fornecedor existe
        fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        String.format("Fornecedor com ID %d não encontrado", fornecedorId)));

        // Busca as ordens
        List<OrdemDeCompraModel> ordens = ordemDeCompraRepository.findByFornecedorIdAndAno(fornecedorId, ano);

        if (ordens.isEmpty()) {
            log.warn("Nenhuma ordem encontrada para fornecedor {} no ano {}", fornecedorId, ano);
        } else {
            log.info("Encontradas {} ordens para fornecedor {} no ano {}", ordens.size(), fornecedorId, ano);
        }

        return ordens.stream()
                .map(OrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());
    }
}
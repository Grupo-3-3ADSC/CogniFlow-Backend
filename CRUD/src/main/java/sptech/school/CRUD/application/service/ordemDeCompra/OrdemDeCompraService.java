package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.CRUD.domain.entity.*;
import sptech.school.CRUD.domain.repository.*;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ConjuntoOrdemDeCompraRepository conjuntoRepository;

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
    @Transactional
    public List<OrdemDeCompraModel> cadastrarMultiplasOrdens(List<OrdemDeCompraCadastroDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new RequisicaoInvalidaException("Nenhuma ordem de compra foi enviada.");
        }

        // 1️⃣ Cria o CONJUNTO primeiro (vazio)
        ConjuntoOrdemDeCompraModel conjunto = new ConjuntoOrdemDeCompraModel();
        conjunto = conjuntoRepository.save(conjunto);

        // 2️⃣ Processa cada DTO e cria as ordens
        List<OrdemDeCompraModel> ordens = new ArrayList<>();
        ConjuntoOrdemDeCompraModel conjuntoFinal = conjunto; // Para usar no loop

        for (OrdemDeCompraCadastroDto dto : dtos) {
            // Usa o Mapper para construir a entidade
            OrdemDeCompraModel ordemDeCompra = OrdemDeCompraMapper.toEntity(dto);

            // Busca e seta as entidades relacionadas
            FornecedorModel fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
            ordemDeCompra.setFornecedor(fornecedor);

            EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));
            ordemDeCompra.setEstoque(estoque);

            UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
            ordemDeCompra.setUsuario(usuario);

            // Validação de rastreabilidade
            if (ordemDeCompraRepository.existsByRastreabilidadeAndEstoqueId(dto.getRastreabilidade(), dto.getEstoqueId())) {
                throw new RequisicaoConflitanteException("Rastreabilidade já cadastrada para este estoque");
            }

            ordemDeCompra.setPendenciaAlterada(false);

            // 3️⃣ ASSOCIA a ordem ao conjunto
            ordemDeCompra.setConjuntoOrdemDeCompra(conjuntoFinal);

            ordens.add(ordemDeCompra);
        }

        // 4️⃣ Salva todas as ordens de uma vez (batch insert)
        List<OrdemDeCompraModel> ordensSalvas = ordemDeCompraRepository.saveAll(ordens);

        // 5️⃣ Atualiza os estoques
        for (int i = 0; i < ordensSalvas.size(); i++) {
            OrdemDeCompraModel ordemSalva = ordensSalvas.get(i);
            OrdemDeCompraCadastroDto dto = dtos.get(i);

            EstoqueModel estoque = estoqueRepository.findById(dto.getEstoqueId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));

            Integer novaQuantidade = (dto.getQuantidade() != null ? dto.getQuantidade() : 0)
                    + ordemSalva.getQuantidade();

            if (estoque.getQuantidadeMaxima() != null && novaQuantidade > estoque.getQuantidadeMaxima()) {
                throw new RequisicaoInvalidaException("A quantidade comprada ultrapassa o limite máximo de estoque permitido.");
            }

            dto.setQuantidade(novaQuantidade);
            estoque.setUltimaMovimentacao(LocalDateTime.now());
            estoqueRepository.save(estoque);
        }

        return ordensSalvas;
    }

    @Transactional
    public List<OrdemDeCompraModel> criarOrdens(List<OrdemDeCompraModel> ordens) {
        if (ordens == null || ordens.isEmpty()) {
            throw new RuntimeException("Nenhuma ordem de compra foi enviada.");
        }

        // 1. Cria o conjunto primeiro
        ConjuntoOrdemDeCompraModel conjunto = new ConjuntoOrdemDeCompraModel();
        conjunto = conjuntoRepository.save(conjunto);

        // 2. Associa cada ordem ao conjunto
        ConjuntoOrdemDeCompraModel conjuntoFinal = conjunto;
        ordens.forEach(ordem -> {
            ordem.setConjuntoOrdemDeCompra(conjuntoFinal);
        });

        // 3. Salva as ordens (já associadas ao conjunto)
        List<OrdemDeCompraModel> ordensSalvas = ordemDeCompraRepository.saveAll(ordens);

        return ordensSalvas;
    }

}

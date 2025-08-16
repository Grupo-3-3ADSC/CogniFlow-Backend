package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.Model.OrdemDeCompraModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.FornecedorRepository;
import sptech.school.CRUD.Repository.OrdemDeCompraRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;
import sptech.school.CRUD.dto.OrdemDeCompra.ListagemOrdemDeCompra;
import sptech.school.CRUD.dto.OrdemDeCompra.MudarQuantidadeAtualDto;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.dto.OrdemDeCompra.OrdemDeCompraMapper;
import sptech.school.CRUD.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.exception.RequisicaoInvalidaException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public OrdemDeCompraModel cadastroOrdemDeCompra(OrdemDeCompraCadastroDto dto) {
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

        if (ordemDeCompraRepository.existsByRastreabilidadeAndEstoqueId(dto.getRastreabilidade(), dto.getEstoqueId())) {
            throw new RequisicaoConflitanteException("Rastreabilidade já cadastrada para este estoque");
        }

        ordemDeCompra.setPendenciaAlterada(false);

        OrdemDeCompraModel ordemSalva = ordemDeCompraRepository.save(ordemDeCompra);

        // Atualiza a quantidade no estoque
        Integer novaQuantidade = (dto.getPendentes() != null ? dto.getPendentes() : 0)
                + ordemSalva.getQuantidade();
        if (estoque.getQuantidadeMaxima() != null && novaQuantidade > estoque.getQuantidadeMaxima()) {
            throw new RequisicaoInvalidaException("A quantidade comprada ultrapassa o limite máximo de estoque permitido.");
        }
        //estoque.setQuantidadeAtual(novaQuantidade);
        dto.setPendentes(novaQuantidade);
        estoque.setUltimaMovimentacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

        return ordemSalva;
    }

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

    public OrdemDeCompraModel mudarQuantidadeAtual(Integer id, MudarQuantidadeAtualDto dto) {
        // Buscar a ordem de compra existente
        OrdemDeCompraModel ordemDeCompra = ordemDeCompraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de compra não encontrada"));

        // Atualizar os campos necessários
        ordemDeCompra.setPendentes(dto.getPendentes());

        // Atualizar o estoque, se necessário
        EstoqueModel estoque = estoqueRepository.findById(ordemDeCompra.getEstoqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não encontrado"));

        if(ordemDeCompra.getPendenciaAlterada()){
            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - dto.getPendentes());
            ordemDeCompra.setQuantidade(0);
            ordemDeCompra.setPendentes(dto.getPendentes());
            ordemDeCompra.setPendenciaAlterada(false);
            estoqueRepository.save(estoque);

            return ordemDeCompraRepository.save(ordemDeCompra);
        }

        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + dto.getPendentes());
        ordemDeCompra.setQuantidade(dto.getPendentes());
        ordemDeCompra.setPendentes(0);
        ordemDeCompra.setPendenciaAlterada(true);
        estoqueRepository.save(estoque);

        return ordemDeCompraRepository.save(ordemDeCompra);
    }



}

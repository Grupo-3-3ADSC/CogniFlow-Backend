package sptech.school.CRUD.application.service.ItemOrdemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.CRUD.domain.entity.*;

import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.ConjuntoOrdemDeCompra.ConjuntoOrdemDeCompraRepository;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.ordemDeCompra.OrdemDeCompraRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra.ConjuntoOrdemDeCompraListagemDto;
import sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra.ConjuntoOrdemDeCompraMapper;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConjuntoOrdemDeCompraService {

    private final ConjuntoOrdemDeCompraRepository conjuntoRepository;
    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public List<ConjuntoOrdemDeCompraListagemDto> listarTodos() {
        return conjuntoRepository.findAll().stream()
                .map(ConjuntoOrdemDeCompraMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    public ConjuntoOrdemDeCompraModel buscarPorId(Integer id) {
        return conjuntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conjunto não encontrado com o ID: " + id));
    }

    @Transactional
    public ConjuntoOrdemDeCompraModel cadastrarMultiplasOrdensComConjunto(List<OrdemDeCompraCadastroDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new RequisicaoInvalidaException("Nenhuma ordem de compra foi enviada.");
        }

        ConjuntoOrdemDeCompraModel conjunto = new ConjuntoOrdemDeCompraModel();
        conjunto = conjuntoRepository.save(conjunto);

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

            ordemDeCompra.setConjuntoOrdemDeCompra(conjuntoFinal);


            conjuntoFinal.getOrdensDeCompra().add(ordemDeCompra);
        }


        conjunto = conjuntoRepository.save(conjunto);


        for (int i = 0; i < conjunto.getOrdensDeCompra().size(); i++) {
            OrdemDeCompraModel ordemSalva = conjunto.getOrdensDeCompra().get(i);
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

        return conjunto;
    }
}
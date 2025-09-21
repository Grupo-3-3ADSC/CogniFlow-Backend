package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.OrdemDeCompraRepository;
import sptech.school.CRUD.infrastructure.persistence.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CadastrarOrdemDeCompraService {
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
        Integer novaQuantidade = (dto.getQuantidade() != null ? dto.getQuantidade() : 0)
                + ordemSalva.getQuantidade();
        if (estoque.getQuantidadeMaxima() != null && novaQuantidade > estoque.getQuantidadeMaxima()) {
            throw new RequisicaoInvalidaException("A quantidade comprada ultrapassa o limite máximo de estoque permitido.");
        }
        //estoque.setQuantidadeAtual(novaQuantidade);
        dto.setQuantidade(novaQuantidade);
        estoque.setUltimaMovimentacao(LocalDateTime.now());
        estoqueRepository.save(estoque);

        return ordemSalva;
    }
}

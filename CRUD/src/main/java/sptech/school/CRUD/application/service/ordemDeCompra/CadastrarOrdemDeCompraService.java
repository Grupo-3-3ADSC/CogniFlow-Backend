package sptech.school.CRUD.application.service.ordemDeCompra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.*;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.*;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.OrdemDeCompraCadastroDto;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ItemOrdemCompraDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CadastrarOrdemDeCompraService {

    private final OrdemDeCompraRepository ordemDeCompraRepository;
    private final EstoqueRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public OrdemDeCompraModel cadastroOrdemDeCompra(OrdemDeCompraCadastroDto dto) {

        // Busca entidades relacionadas
        FornecedorModel fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));

        UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        // Cria a ordem
        OrdemDeCompraModel ordem = new OrdemDeCompraModel();
        ordem.setPrazoEntrega(dto.getPrazoEntrega());
        ordem.setCondPagamento(dto.getCondPagamento());
        ordem.setUsuario(usuario);
        ordem.setFornecedor(fornecedor);
        ordem.setPendenciaAlterada(false);
        ordem.setDataDeEmissao(LocalDateTime.now());

        // Itera sobre os itens do DTO
        for (ItemOrdemCompraDto itemDto : dto.getItens()) {

            EstoqueModel estoque = estoqueRepository.findById(itemDto.getEstoqueId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Estoque não encontrado para ID: " + itemDto.getEstoqueId()));

            // Checa rastreabilidade duplicada
            if (ordemDeCompraRepository.existsByRastreabilidadeAndEstoqueId(itemDto.getRastreabilidade(), estoque.getId())) {
                throw new RequisicaoConflitanteException(
                        "Rastreabilidade já cadastrada para este estoque: " + itemDto.getRastreabilidade());
            }

            // Checa quantidade máxima
            Integer novaQuantidade = (itemDto.getQuantidade() != null ? itemDto.getQuantidade() : 0)
                    + (estoque.getQuantidadeAtual() != null ? estoque.getQuantidadeAtual() : 0);
            if (estoque.getQuantidadeMaxima() != null && novaQuantidade > estoque.getQuantidadeMaxima()) {
                throw new RequisicaoInvalidaException(
                        "A quantidade do item " + itemDto.getDescricaoMaterial() +
                                " ultrapassa o limite máximo do estoque.");
            }

            // Atualiza estoque
            estoque.setQuantidadeAtual(novaQuantidade);
            estoque.setUltimaMovimentacao(LocalDateTime.now());
            estoqueRepository.save(estoque);

            // Cria item e associa à ordem
            ItemOrdemCompraModel item = new ItemOrdemCompraModel();
            item.setOrdem(ordem);
            item.setEstoque(estoque);
            item.setQuantidade(itemDto.getQuantidade());
            item.setValorUnitario(itemDto.getValorUnitario());
            item.setValorKg(itemDto.getValorKg());
            item.setValorPeca(itemDto.getValorPeca());
            item.setDescricaoMaterial(itemDto.getDescricaoMaterial());
            item.setRastreabilidade(itemDto.getRastreabilidade());
            item.setIpi(itemDto.getIpi());

            ordem.getItens().add(item);
        }

        // Salva ordem + itens
        return ordemDeCompraRepository.save(ordem);
    }
}

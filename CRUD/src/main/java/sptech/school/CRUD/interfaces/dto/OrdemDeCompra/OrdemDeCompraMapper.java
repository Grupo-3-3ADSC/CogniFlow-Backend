package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;

import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.domain.entity.ItemOrdemCompraModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemDeCompraMapper {

    // Converte OrdemDeCompraModel → ListagemOrdemDeCompra (DTO)
    public static ListagemOrdemDeCompra toListagemDto(OrdemDeCompraModel entity) {
        if (entity == null) return null;

        // Converte os itens para DTO
        List<ItemOrdemCompraDto> itensDto = entity.getItens() != null ?
                entity.getItens().stream().map(item -> ItemOrdemCompraDto.builder()
                                .estoqueId(item.getEstoque() != null ? item.getEstoque().getId() : null)
                                .quantidade(item.getQuantidade())
                                .valorUnitario(item.getValorUnitario())
                                .ipi(item.getIpi())
                                .descricaoMaterial(item.getDescricaoMaterial())
                                .valorKg(item.getValorKg())
                                .valorPeca(item.getValorPeca())
                                .rastreabilidade(item.getRastreabilidade())
                                .build())
                        .collect(Collectors.toList())
                : null;

        return ListagemOrdemDeCompra.builder()
                .id(entity.getId())
                .prazoEntrega(entity.getPrazoEntrega())
                .condPagamento(entity.getCondPagamento())
                .fornecedorId(entity.getFornecedor() != null ? entity.getFornecedor().getId() : null)
                .usuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null)
                .nomeFornecedor(entity.getFornecedor() != null ? entity.getFornecedor().getNomeFantasia() : "Fornecedor não encontrado")
                .dataDeEmissao(entity.getDataDeEmissao())
                .pendenciaAlterada(Boolean.TRUE.equals(entity.getPendenciaAlterada()))
                .itens(itensDto)
                .build();
    }

    // Converte OrdemDeCompraCadastroDto → OrdemDeCompraModel (entity)
    public static OrdemDeCompraModel toEntity(OrdemDeCompraCadastroDto dto) {
        if (dto == null) return null;

        OrdemDeCompraModel ordem = new OrdemDeCompraModel();
        ordem.setPrazoEntrega(dto.getPrazoEntrega());
        ordem.setCondPagamento(dto.getCondPagamento());
        if (dto.getFornecedorId() != null) {
            FornecedorModel fornecedor = new FornecedorModel();
            fornecedor.setId(dto.getFornecedorId());
            ordem.setFornecedor(fornecedor);
        } else {
            ordem.setFornecedor(null);
        }
        ordem.setDataDeEmissao(LocalDateTime.now());


        // Converte os itens do DTO para ItemOrdemCompraModel
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            List<ItemOrdemCompraModel> itens = dto.getItens().stream().map(itemDto -> {
                ItemOrdemCompraModel item = new ItemOrdemCompraModel();

                // Cria o EstoqueModel e seta no item
                EstoqueModel estoque = new EstoqueModel();
                estoque.setId(itemDto.getEstoqueId());
                item.setEstoque(estoque);

                item.setQuantidade(itemDto.getQuantidade());
                item.setValorUnitario(itemDto.getValorUnitario());
                item.setIpi(itemDto.getIpi());
                item.setDescricaoMaterial(itemDto.getDescricaoMaterial());
                item.setValorKg(itemDto.getValorKg());
                item.setValorPeca(itemDto.getValorPeca());
                item.setRastreabilidade(itemDto.getRastreabilidade());

                // Conecta o item à ordem
                item.setOrdem(ordem);

                return item;
            }).collect(Collectors.toList());

            ordem.setItens(itens);
        }

        return ordem;
    }
}

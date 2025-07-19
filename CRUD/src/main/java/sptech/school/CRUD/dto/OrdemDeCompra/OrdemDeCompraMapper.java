package sptech.school.CRUD.dto.OrdemDeCompra;

import sptech.school.CRUD.Model.OrdemDeCompraModel;

import java.time.LocalDateTime;

public class OrdemDeCompraMapper {

    public static ListagemOrdemDeCompra toListagemDto(OrdemDeCompraModel entity) {
        if (entity == null) {
            return null;
        }
        return ListagemOrdemDeCompra.builder()
                .id(Long.valueOf(entity.getId()))
                .prazoEntrega(entity.getPrazoEntrega())
                .ie(entity.getIe())
                .condPagamento(entity.getCondPagamento())
                .valorKg(entity.getValorKg())
                .rastreabilidade(entity.getRastreabilidade())
                .valorPeca(entity.getValorPeca())
                .descricaoMaterial(entity.getDescricaoMaterial())
                .valorUnitario(entity.getValorUnitario())
                .quantidade(entity.getQuantidade())
                .ipi(entity.getIpi())
                .fornecedorId(entity.getFornecedorId())
                .estoqueId(entity.getEstoqueId())
                .usuarioId(entity.getUsuarioId())
                .nomeFornecedor(entity.getFornecedor() != null ? entity.getFornecedor().getNomeFantasia() : "Fornecedor não encontrado")
                .descricaoMaterialCompleta(entity.getEstoque() != null ? entity.getEstoque().getTipoMaterial() : "Material não encontrado")
                .build();
    }

    public static OrdemDeCompraModel toEntity(OrdemDeCompraCadastroDto dto) {

        OrdemDeCompraModel ordem = new OrdemDeCompraModel();
        if (dto == null) {
            return null;
        }
        ordem.setPrazoEntrega(dto.getPrazoEntrega());
        ordem.setIe(dto.getIe());
        ordem.setCondPagamento(dto.getCondPagamento());
        ordem.setValorKg(dto.getValorKg());
        ordem.setRastreabilidade(dto.getRastreabilidade());
        ordem.setValorPeca(dto.getValorPeca());
        ordem.setDescricaoMaterial(dto.getDescricaoMaterial());
        ordem.setValorUnitario(dto.getValorUnitario());
        ordem.setQuantidade(dto.getQuantidade());
        ordem.setIpi(dto.getIpi());
        ordem.setDataDeEmissao(LocalDateTime.now());
        ordem.setFornecedorId(dto.getFornecedorId());
        ordem.setEstoqueId(dto.getEstoqueId());
        ordem.setUsuarioId(dto.getUsuarioId());
        return ordem;
    }


}

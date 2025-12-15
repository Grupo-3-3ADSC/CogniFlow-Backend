package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;

import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;

import java.time.LocalDateTime;

public class OrdemDeCompraMapper {

    public static ListagemOrdemDeCompra toListagemDto(OrdemDeCompraModel entity) {
        if (entity == null) {
            return null;
        }
        return ListagemOrdemDeCompra.builder()
                .id(Integer.valueOf(entity.getId()))
                .prazoEntrega(String.valueOf(entity.getPrazoEntrega()))
//                .ie(entity.getIe())
                .condPagamento(entity.getCondPagamento())
                .valorKg(entity.getValorKg())
                .tipoCompra(entity.getTipoCompra())
                .rastreabilidade(entity.getRastreabilidade())
                .valorPeca(entity.getValorPeca())
                .descricaoMaterial(entity.getDescricaoMaterial())
                .valorUnitario(entity.getValorUnitario())
                .quantidade(entity.getQuantidade())
                .fornecedorId(entity.getFornecedorId())
                .estoqueId(entity.getEstoqueId())
                .usuarioId(entity.getUsuarioId())
                .nomeFornecedor(entity.getFornecedor() != null ? entity.getFornecedor().getNomeFantasia() : "Fornecedor não encontrado")
                .descricaoMaterialCompleta(entity.getEstoque() != null ? entity.getEstoque().getTipoMaterial() : "Material não encontrado")
                .dataDeEmissao(entity.getDataDeEmissao())
                .pendenciaAlterada(Boolean.TRUE.equals(entity.getPendenciaAlterada()))
                .tipoMaterial(entity.getEstoque() != null ? entity.getEstoque().getTipoMaterial() : null)
                .build();
    }

    public static OrdemDeCompraModel toEntity(OrdemDeCompraCadastroDto dto) {

        OrdemDeCompraModel ordem = new OrdemDeCompraModel();
        if (dto == null) {
            return null;
        }
        ordem.setPrazoEntrega(dto.getPrazoEntrega());
//        ordem.setIe(dto.getIe());
        ordem.setCondPagamento(dto.getCondPagamento());
        ordem.setRastreabilidade(dto.getRastreabilidade());
        ordem.setTipoCompra(dto.getTipoCompra());
        ordem.setDescricaoMaterial(dto.getDescricaoMaterial());
        ordem.setTipoCompra(dto.getTipoCompra());

        if (dto.getTipoCompra().equalsIgnoreCase("QUILO")) {
            ordem.setValorKg(dto.getValorKg());
            ordem.setValorUnitario(null);
        } else { // UNIDADE
            ordem.setValorUnitario(dto.getValorUnitario());
            ordem.setValorKg(null);
        }

        ordem.setQuantidade(dto.getQuantidade());

        ordem.setDataDeEmissao(LocalDateTime.now());
        ordem.setFornecedorId(dto.getFornecedorId());
        ordem.setEstoqueId(dto.getEstoqueId());
        ordem.setUsuarioId(dto.getUsuarioId());

        return ordem;
    }

//    public static OrdemDeCompraModel toEntity(MudarQuantidadeAtualDto dto) {
//        if (dto == null) return null;
//
//        OrdemDeCompraModel ordem = new OrdemDeCompraModel();
//        ordem.setPendentes(dto.getPendentes());
//        // adicione outros campos conforme necessário
//        return ordem;
//    }
//    public static ListagemOrdemDeCompra toDto(OrdemDeCompraModel model) {
//        if (model == null) return null;
//
//        return ListagemOrdemDeCompra.builder()
//                .id(model.getId())
//                .quantidade(model.getQuantidade())
//                .pendentes(model.getPendentes())
//                .estoqueId(model.getEstoqueId())
//                .pendenciaAlterada(model.getPendenciaAlterada())
//                .build();
//    }

}

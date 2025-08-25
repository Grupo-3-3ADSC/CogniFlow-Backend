package sptech.school.CRUD.dto.Fornecedor;

import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Model.FornecedorModel;

import java.time.LocalDateTime;

public class FornecedorMapper {

    public static FornecedorModel toCadastroModel(FornecedorCadastroDto dto) {
        if (dto == null) {
            return null;
        }

        FornecedorModel entity = new FornecedorModel();
        entity.setCnpj(dto.getCnpj());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setRazaoSocial(dto.getRazaoSocial());
        // Nota: Os campos de endereço e contato não são setados aqui pois
        // são entidades separadas que serão criadas no service

        return entity;
    }

    // Converte FornecedorModel para FornecedorCadastroDto
    public static FornecedorCadastroDto toDto(FornecedorModel model) {
        if (model == null) {
            return null;
        }

        FornecedorCadastroDto dto = new FornecedorCadastroDto();
        dto.setCnpj(model.getCnpj());
        dto.setNomeFantasia(model.getNomeFantasia());
        dto.setRazaoSocial(model.getRazaoSocial());

        // Como o FornecedorModel não tem relacionamentos diretos,
        // os dados de endereço e contato precisam ser passados separadamente
        // ou buscados via repository no service

        return dto;
    }
    public static FornecedorCompletoDTO fornecedorCompleto(FornecedorCompletoDTO dto){
        if (dto == null){
            return null;
        }

        FornecedorCompletoDTO entity = new FornecedorCompletoDTO();
        entity.setFornecedorId(dto.getFornecedorId());
        entity.setCnpj(dto.getCnpj());
        entity.setRazaoSocial(dto.getRazaoSocial());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setEnderecoId(dto.getEnderecoId());
        entity.setCep(dto.getCep());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setContatoId(dto.getContatoId());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setResponsavel(dto.getResponsavel());
        entity.setCargo(dto.getCargo());
        return entity;
    }
//    public static FornecedorPaginadoDTO fornecedorPaginado(FornecedorPaginadoDTO dto){
//        if(dto == null){
//            return null;
//        }
//
//        FornecedorPaginadoDTO entity = new FornecedorPaginadoDTO();
//        entity.setFornecedorId(dto.getFornecedorId());
//        entity.setCnpj(dto.getCnpj());
//        entity.setRazaoSocial(dto.getRazaoSocial());
//        entity.setNomeFantasia(dto.getNomeFantasia());
//        entity.setEnderecoId(dto.getEnderecoId());
//        entity.setCep(dto.getCep());
//        entity.setNumero(dto.getNumero());
//        entity.setComplemento(dto.getComplemento());
//        entity.setContatoId(dto.getContatoId());
//        entity.setTelefone(dto.getTelefone());
//        entity.setEmail(dto.getEmail());
//        entity.setOrdemDeCompraId(dto.getOrdemDeCompraId());
//        entity.setPrazoEntrega(dto.getPrazoEntrega());
//        entity.setIe(dto.getIe());
//        entity.setCondPagamento(dto.getCondPagamento());
//        entity.setValorKg(dto.getValorKg());
//        entity.setRastreabilidade(dto.getRastreabilidade());
//        entity.setValorPeca(dto.getValorPeca());
//        entity.setDescricaoMaterial(dto.getDescricaoMaterial());
//        entity.setValorUnitario(dto.getValorUnitario());
//        entity.setQuantidade(dto.getQuantidade());
//        entity.setIpi(dto.getIpi());
//        entity.setDataDeEmissao(dto.getDataDeEmissao());
//        return entity;
//    }
}

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

        return entity;
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
        return entity;
    }
}

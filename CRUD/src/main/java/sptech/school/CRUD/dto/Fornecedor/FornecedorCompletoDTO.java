package sptech.school.CRUD.dto.Fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorCompletoDTO {

        private Integer fornecedorId;
        private String cnpj;
        private String ie;
        private String razaoSocial;
        private String nomeFantasia;
        private Integer enderecoId;
        private String cep;
        private Integer numero;
        private String complemento;
        private Integer contatoId;
        private String telefone;
        private String email;
        private String responsavel;
        private String cargo;
}

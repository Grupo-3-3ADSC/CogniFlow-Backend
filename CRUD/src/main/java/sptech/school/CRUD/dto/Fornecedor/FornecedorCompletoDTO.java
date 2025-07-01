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
        @NotBlank(message = "CNPJ não pode ser nulo ou vazio.")
        @Size(min = 14, max = 14, message = "CNPJ deve ter 14 dígitos.")
        private String cnpj;
        @NotBlank(message = "Razão Social não pode ser nulo ou vazio.")
        private String razaoSocial;
        @NotBlank(message = "Nome Fantasia não pode ser nulo ou vazio.")
        private String nomeFantasia;
        @NotBlank(message = "Endereço não pode ser nulo ou vazio.")
        private Integer enderecoId;
        @NotBlank(message = "CEP não pode ser nulo ou vazio.")
        private String cep;
        @NotBlank(message = "número não pode ser nulo ou vazio.")
        private Integer numero;

        private String complemento;

        private Integer contatoId;
        @NotBlank(message = "telefone não pode ser nulo ou vazio.")
        @Size(min = 10, max = 13, message = "Telefone deve ter entre 10 e 11 dígitos.")
        private String telefone;
        @NotBlank(message = "email não pode ser nulo ou vazio.")
        @Email(message = "O e-mail deve conter um '@' e um domínio válido.")
        private String email;
}

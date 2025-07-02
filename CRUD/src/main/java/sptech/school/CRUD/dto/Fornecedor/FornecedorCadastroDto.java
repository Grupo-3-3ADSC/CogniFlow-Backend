package sptech.school.CRUD.dto.Fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor

public class FornecedorCadastroDto {

    @NotBlank(message = "CNPJ não pode ser nulo ou vazio.")
    @Size(min = 14, max = 14, message = "CNPJ deve ter 14 dígitos.")
    private String cnpj;
    @NotBlank

    @NotBlank(message = "Razão Social não pode ser nulo ou vazio.")
    private String razaoSocial;
    @NotBlank

    @NotBlank(message = "Nome Fantasia não pode ser nulo ou vazio.")
    private String nomeFantasia;
    @NotBlank

    @NotBlank(message = "CEP não pode ser nulo ou vazio.")
    private String cep;
    @NotBlank

    @NotBlank(message = "Endereço não pode ser nulo ou vazio.")
    private String endereco;
    @NotBlank

    @NotBlank(message = "número não pode ser nulo ou vazio.")
    private String numero;
    @NotBlank

    @NotBlank(message = "telefone não pode ser nulo ou vazio.")
    @Size(min = 10, max = 13, message = "Telefone deve ter entre 10 e 13 dígitos.")
    private String telefone;
    @NotBlank

    @NotBlank(message = "email não pode ser nulo ou vazio.")
    @Email(message = "O e-mail deve conter um '@' e um domínio válido.")
    private String email;
}

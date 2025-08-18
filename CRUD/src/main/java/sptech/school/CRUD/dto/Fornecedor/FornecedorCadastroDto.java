package sptech.school.CRUD.dto.Fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @CNPJ
    private String cnpj;

    @NotBlank
    @NotNull
    private String ie;

    @NotBlank(message = "Razão Social não pode ser nulo ou vazio.")
    private String razaoSocial;

    @NotBlank(message = "Nome Fantasia não pode ser nulo ou vazio.")
    private String nomeFantasia;

    @NotBlank(message = "CEP não pode ser nulo ou vazio.")
    @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos.")
    private String cep;

    @NotBlank(message = "Endereço não pode ser nulo ou vazio.")
    private String endereco;

    @NotNull(message = "Número não pode ser nulo.")
    private Integer numero;

    @NotBlank(message = "Telefone não pode ser nulo ou vazio.")
    @Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 dígitos.")
    private String telefone;

    @NotBlank(message = "Email não pode ser nulo ou vazio.")
    @Email(message = "O e-mail deve conter um '@' e um domínio válido.")
    private String email;

    @NotBlank(message = "Responsável não pode ser nulo ou vazio")
    private String responsavel;

    @NotBlank(message = "Cargo não pode ser nulo ou vazio.")
    private String cargo;
}
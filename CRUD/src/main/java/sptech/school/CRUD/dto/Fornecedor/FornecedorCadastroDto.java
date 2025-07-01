package sptech.school.CRUD.dto.Fornecedor;

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
    @NotBlank
    @Size(min = 14, max = 14)
    @CNPJ
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    @NotBlank
    private String nomeFantasia;
    @NotBlank
    @Size(min = 8, max = 8)
    private String cep;
    @NotBlank
    private String endereco;
    @NotBlank
    private Integer numero;
    @NotBlank
    @Size(min = 11, max = 11)
    private String telefone;
    @NotBlank
    private String email;
}

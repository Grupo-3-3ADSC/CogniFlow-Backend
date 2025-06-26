package sptech.school.CRUD.dto.Fornecedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor

public class FornecedorCadastroDto {
    @NotBlank
    @Size(min = 14, max = 14)
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
    @Size(min = 14, max = 14)
    private String telefone;
    @NotBlank
    private String email;
}

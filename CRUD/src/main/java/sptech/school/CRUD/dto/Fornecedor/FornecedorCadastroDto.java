package sptech.school.CRUD.dto.Fornecedor;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor

public class FornecedorCadastroDto {


    @NotBlank
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    @NotBlank
    private String nomeFantasia;
    @NotBlank
    private String cep;
    @NotBlank
    private String endereco;
    @NotBlank
    private String numero;
    @NotBlank
    private String telefone;
    @NotBlank
    private String email;
}

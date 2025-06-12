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
}

package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueCadastroDto {

    @NotBlank
    private String tipoMaterial;

    @NotNull
    private Integer quantidadeAtual;

    @NotNull
    private Integer quantidadeMinima;

    @NotNull
    private Integer quantidadeMaxima;

    @NotBlank
    private String tipoTransferencia;

}

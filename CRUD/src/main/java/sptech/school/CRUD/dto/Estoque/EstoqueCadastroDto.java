package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueCadastroDto {

    @NotBlank
    private Integer quantidadeAtual;
    @NotBlank
    private Integer quantidadeMinima;
    @NotBlank
    private Integer quantidadeMaxima;
}

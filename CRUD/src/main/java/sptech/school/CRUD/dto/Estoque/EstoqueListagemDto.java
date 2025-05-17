package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueListagemDto {
    @NotBlank
    private Integer quantidadeAtual;
    @NotBlank
    private Integer quantidadeMinima;
    @NotBlank
    private Integer quantidadeMaxima;
}

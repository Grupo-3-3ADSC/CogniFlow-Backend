package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueListagemDto {
    @NotBlank
    private String tipoMaterial;
    @NotNull
    private Integer quantidadeAtual;
    @NotNull
    private Integer quantidadeMinima;
    @NotNull
    private Integer quantidadeMaxima;

    private Integer externo;


    private Integer interno;
}

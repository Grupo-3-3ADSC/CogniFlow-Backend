package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarEstoqueDto {
    @NotBlank
    private String tipoMaterial;
    @NotNull
    private Integer quantidadeAtual;
}

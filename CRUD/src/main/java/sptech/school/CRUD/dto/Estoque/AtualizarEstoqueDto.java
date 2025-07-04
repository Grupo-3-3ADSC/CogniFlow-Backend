package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarEstoqueDto {
    @NotBlank(message = "Tipo de Material não pode ser nulo ou vazio")
    private String tipoMaterial;
    @NotNull(message = "Quantidade Atual não pode ser nula")
    @Min(value = 1, message = "Quantidade Atual deve ser positiva")
    private Integer quantidadeAtual;
}

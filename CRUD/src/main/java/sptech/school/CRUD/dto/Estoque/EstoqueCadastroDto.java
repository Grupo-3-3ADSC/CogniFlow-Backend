package sptech.school.CRUD.dto.Estoque;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueCadastroDto {

    @NotBlank(message = "Tipo de Material não pode ser nulo ou vazio")
    private String tipoMaterial;

    @NotNull(message = "Quantidade Atual não pode ser nula")
    @Min(value = 1, message = "Quantidade Atual deve ser positiva")
    private Integer quantidadeAtual;

    @NotNull(message = "Quantidade Mínima não pode ser nula")
    @Min(value = 1, message = "Quantidade Mínima deve ser positiva")
    private Integer quantidadeMinima;

    @NotNull(message = "Quantidade Máxima não pode ser nula")
    @Min(value = 1, message = "Quantidade Máxima deve ser positiva")
    private Integer quantidadeMaxima;

    @NotNull(message = "IPI não pode ser nulo ou vazio")
    @DecimalMin(value = "0.00", message = "IPI deve ser zero ou maior")
    private Double ipi;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ultimaMovimentacao;
}

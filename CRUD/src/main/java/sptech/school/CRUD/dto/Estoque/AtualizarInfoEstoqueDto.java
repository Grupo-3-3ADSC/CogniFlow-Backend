package sptech.school.CRUD.dto.Estoque;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarInfoEstoqueDto {
    private Double ipi;
    private String tipoMaterial;
}

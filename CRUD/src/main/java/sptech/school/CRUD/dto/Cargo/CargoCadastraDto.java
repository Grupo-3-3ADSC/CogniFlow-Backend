package sptech.school.CRUD.dto.Cargo;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargoCadastraDto {

    @NotBlank
    private String nome;
}

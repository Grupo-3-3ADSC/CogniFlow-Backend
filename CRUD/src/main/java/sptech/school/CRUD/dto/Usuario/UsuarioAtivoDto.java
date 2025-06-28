package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAtivoDto {
    @NotNull
    private Boolean ativo;
}

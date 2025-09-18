package sptech.school.CRUD.interfaces.dto.Usuario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAtivoDto {
    @NotNull(message = "não pode ser nulo ou vazio.")
    private Boolean ativo;
}

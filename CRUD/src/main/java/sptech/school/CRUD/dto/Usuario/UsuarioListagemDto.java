package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioListagemDto {
    private String nome;
    private String email;
}

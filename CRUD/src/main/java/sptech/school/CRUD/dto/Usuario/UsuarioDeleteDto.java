package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDeleteDto {
    private Integer id;
    private String nome;
    private String email;
    private String password;
}

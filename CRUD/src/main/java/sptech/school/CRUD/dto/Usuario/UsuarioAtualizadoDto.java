package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizadoDto {


    private Integer id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

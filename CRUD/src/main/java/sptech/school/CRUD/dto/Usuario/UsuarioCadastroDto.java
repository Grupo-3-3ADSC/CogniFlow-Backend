package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private CargoModel cargo;
}

package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import lombok.*;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {

    @NotBlank
    @Size(min = 3, message = "Quantidade no mínimo de 3 carácter")
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotNull
    private CargoModel cargo;


}

package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {

    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    private String nome;

    @NotBlank(message = "E-mail não pode ser nulo ou vazio.")
    @Email(message = "O e-mail deve conter um '@' e um domínio válido.")
    private String email;

    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    private String password;

    private CargoModel cargo;


}

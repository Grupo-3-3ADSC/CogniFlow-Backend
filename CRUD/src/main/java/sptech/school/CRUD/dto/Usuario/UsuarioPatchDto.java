package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
public class UsuarioPatchDto {
    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    private String nome;
    @NotBlank(message = "Email não pode ser nulo ou vazio.")
    @Email
    private String email;
    @NotBlank(message = "Cargo não pode ser nulo ou vazio.")
    private CargoModel cargo;
}

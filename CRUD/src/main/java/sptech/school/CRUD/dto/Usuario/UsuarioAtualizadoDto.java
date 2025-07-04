package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizadoDto {

    @NotNull
    private Integer id;
    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    private String nome;
    @NotBlank(message = "Email não pode ser nulo ou vazio.")
    private String email;
    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    @Size(min = 6, message = "precisa ter mais de 5 caracter")
    private String password;
    @NotNull(message = "Cargo não pode ser nulo ou vazio.")
    private CargoModel cargo;


}

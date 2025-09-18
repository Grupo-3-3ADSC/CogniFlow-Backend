package sptech.school.CRUD.interfaces.dto.Usuario;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sptech.school.CRUD.domain.entity.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizadoDto {



    @NotNull(message = "ID  não pode ser nulo ou vazio.")
    private Integer id;
    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    @Size(min = 3, message = "Quantidade no mínimo de 3 carácter")
    private String nome;
    @NotBlank(message = "E-mail não pode ser nulo ou vazio.")
    @Email(message = "Insira um e-mail válido")
    private String email;
    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    @Size(min = 6)
    private String password;
    @NotNull(message = "Cargo não pode ser nulo ou vazio")
    private CargoModel cargo;


}

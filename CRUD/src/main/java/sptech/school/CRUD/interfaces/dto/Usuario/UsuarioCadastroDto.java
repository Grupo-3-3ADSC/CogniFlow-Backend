package sptech.school.CRUD.interfaces.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import lombok.*;
import sptech.school.CRUD.domain.entity.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {

    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    @Size(min = 3, message = "Quantidade no mínimo de 3 carácter")
    private String nome;
    @NotBlank(message = "E-mail não pode ser nulo ou vazio.")
    @Email(message = "O e-mail deve conter um '@' e um domínio válido.")
    private String email;
    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    @Size(min = 6)
    private String password;
    @NotNull(message = "Cargo não pode ser nulo ou vazio")
    private Integer cargoId;


}

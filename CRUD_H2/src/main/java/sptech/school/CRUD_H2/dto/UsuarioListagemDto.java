package sptech.school.CRUD_H2.dto;

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
    private String password;
}

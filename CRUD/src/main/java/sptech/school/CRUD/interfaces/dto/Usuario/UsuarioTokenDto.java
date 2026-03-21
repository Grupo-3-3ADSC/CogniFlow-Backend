package sptech.school.CRUD.interfaces.dto.Usuario;

import lombok.*;
import sptech.school.CRUD.domain.entity.CargoModel;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTokenDto {

    private Long userId;
    private String nome;
    private String email;
    private String token;
    private CargoModel cargo;
}

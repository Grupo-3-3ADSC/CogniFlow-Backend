package sptech.school.CRUD.dto.Usuario;

import lombok.Getter;
import lombok.Setter;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
public class UsuarioLoginDto {

    private String email;
    private String password;
}

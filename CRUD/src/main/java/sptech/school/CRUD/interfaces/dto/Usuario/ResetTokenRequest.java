package sptech.school.CRUD.interfaces.dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetTokenRequest {
    private String resetToken;
    private String jti;
}

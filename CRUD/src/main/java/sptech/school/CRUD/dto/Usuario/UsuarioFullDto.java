package sptech.school.CRUD.dto.Usuario;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.CRUD.Model.CargoModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFullDto {
    private Integer id;
    private String nome;
    private String email;
    private Boolean ativo;
    private byte[] foto;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private CargoModel cargo;
}

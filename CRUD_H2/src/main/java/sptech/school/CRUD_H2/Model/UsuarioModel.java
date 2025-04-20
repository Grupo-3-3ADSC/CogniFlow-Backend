package sptech.school.CRUD_H2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String password;
    private Boolean ativo = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private CargoModel cargo;

    public UsuarioModel(@NotBlank String nome, @NotBlank String email, @NotBlank String password) {
    }
}

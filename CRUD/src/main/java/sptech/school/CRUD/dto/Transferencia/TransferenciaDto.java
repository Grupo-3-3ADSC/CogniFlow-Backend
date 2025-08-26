package sptech.school.CRUD.dto.Transferencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ultimaMovimentacao;

    @NotBlank(message = "Tipo de Material não pode ser nulo ou vazio")
    private String tipoMaterial;

    @NotBlank
    private String setor;

    @NotBlank
    private Integer quantidadeTransferida;


}

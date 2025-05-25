package sptech.school.CRUD.dto.OrdemDeCompra;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeCompraCadastroDto {

    @NotBlank
    private String prazoEntrega;
    @NotBlank
    private String ie;
    @NotBlank
    private String condPagamento;
    @NotBlank
    private Double valorKg;
    @NotBlank
    private String rastreabilidade;
    @NotBlank
    private Double valorPeca;
    @NotBlank
    private String descricaoMaterial;
    @NotBlank
    private Double valorUnitario;
    @NotBlank
    private Integer quantidade;
    @NotBlank
    private Double ipi;


}

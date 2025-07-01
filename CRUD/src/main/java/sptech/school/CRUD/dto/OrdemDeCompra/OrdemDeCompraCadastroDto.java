package sptech.school.CRUD.dto.OrdemDeCompra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Double valorKg;
    @NotBlank
    private String rastreabilidade;
    @NotNull
    private Double valorPeca;
    @NotBlank
    private String descricaoMaterial;
    @NotNull
    private Double valorUnitario;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Double ipi;
    @NotNull
    private Integer fornecedorId;
    @NotNull
    private Integer estoqueId;
    @NotNull
    private Integer usuarioId;

}

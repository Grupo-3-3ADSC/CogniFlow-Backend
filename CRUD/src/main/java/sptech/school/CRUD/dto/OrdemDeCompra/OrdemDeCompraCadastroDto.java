package sptech.school.CRUD.dto.OrdemDeCompra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 12, max = 12)
    private String ie;
    @NotBlank
    private String condPagamento;
    @NotNull
    private Double valorKg;
    @NotBlank
    @Size(min = 16,max = 16)
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

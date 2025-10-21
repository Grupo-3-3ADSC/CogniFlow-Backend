package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrdemCompraDto {

    @NotNull(message = "Estoque não pode ser nulo")
    private Integer estoqueId;

    @NotNull(message = "Quantidade não pode ser nula")
    @Min(value = 1, message = "Quantidade mínima é 1")
    private Integer quantidade;

    @NotNull(message = "Valor unitário não pode ser nulo")
    @DecimalMin(value = "0.01", message = "Valor unitário deve ser maior que zero")
    private Double valorUnitario;

    @DecimalMin(value = "0.00", message = "IPI deve ser zero ou maior")
    private Double ipi;

    @NotBlank(message = "Descrição do material não pode ser vazia")
    private String descricaoMaterial;

    @NotNull(message = "Valor por quilo não pode ser nulo")
    @DecimalMin(value = "0.01", message = "Valor por quilo deve ser maior que zero")
    private Double valorKg;

    @NotNull(message = "Valor por peça não pode ser nulo")
    @DecimalMin(value = "0.01", message = "Valor por peça deve ser maior que zero")
    private Double valorPeca;

    @NotBlank(message = "Rastreabilidade não pode ser vazia")
    @Size(min = 16, message = "Rastreabilidade deve ter no mínimo 16 caracteres")
    private String rastreabilidade;
}

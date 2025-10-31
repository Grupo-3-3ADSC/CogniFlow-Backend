package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListagemOrdemDeCompra {
    private Integer id;
    private String prazoEntrega;
    private String ie;
    private String condPagamento;
    private Double valorKg;
    private String rastreabilidade;
    private Double valorPeca;
    private String descricaoMaterial;
    private Double valorUnitario;
    private Integer quantidade;
    private Integer fornecedorId;
    private Integer estoqueId;
    private Integer usuarioId;
    private String nomeFornecedor;
    private String descricaoMaterialCompleta;
    private LocalDateTime dataDeEmissao;
    private String tipoMaterial;
    private Boolean pendenciaAlterada;
}

package sptech.school.CRUD.interfaces.dto.Transferencia;

import lombok.*;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginacaoHistoricoTransferencia {
    private List<TransferenciaListagemDto> data;
    private int paginaAtual;
    private int paginasTotais;
    private long totalItems;
    private boolean hasNext;
    private boolean hasPrevious;
    private int pageSize;
}

package sptech.school.CRUD.dto.OrdemDeCompra;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginacaoHistoricoOrdemDeCompraDTO {
    private List<ListagemOrdemDeCompra> data;
    private int paginaAtual;
    private int paginasTotais;
    private long totalItems;
    private boolean hasNext;
    private boolean hasPrevious;
    private int pageSize;
}

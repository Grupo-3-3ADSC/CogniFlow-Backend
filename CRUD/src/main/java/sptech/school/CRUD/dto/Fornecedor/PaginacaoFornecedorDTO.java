package sptech.school.CRUD.dto.Fornecedor;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginacaoFornecedorDTO {
    private List<FornecedorCompletoDTO> data;
    private int paginaAtual;
    private int paginasTotais;
    private long totalItems;
    private boolean hasNext;
    private boolean hasPrevious;
    private int pageSize;
}

package sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sptech.school.CRUD.interfaces.dto.OrdemDeCompra.ListagemOrdemDeCompra;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConjuntoOrdemDeCompraListagemDto {
    private Integer id;
    private List<ListagemOrdemDeCompra> ordensDeCompra;
}

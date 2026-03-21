package sptech.school.CRUD.interfaces.dto.ItemOrdemDeCompra;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.school.CRUD.domain.entity.ConjuntoOrdemDeCompraModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConjuntoOrdemDeCompraDTO {

    private Integer id;
    private List<Integer> ordensIds;


}

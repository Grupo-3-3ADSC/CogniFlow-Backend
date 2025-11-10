package sptech.school.CRUD.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class EspessuraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String espessura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private OrdemDeCompraModel ordemDeCompraModel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private EstoqueModel estoqueModel;
}

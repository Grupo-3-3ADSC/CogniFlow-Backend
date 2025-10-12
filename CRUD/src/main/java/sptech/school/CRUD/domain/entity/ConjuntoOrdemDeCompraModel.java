package sptech.school.CRUD.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conjunto_ordem_compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConjuntoOrdemDeCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "conjuntoOrdemDeCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<OrdemDeCompraModel> ordensDeCompra = new ArrayList<>();

    // ✅ Método helper para adicionar ordens de forma segura
    public void adicionarOrdem(OrdemDeCompraModel ordem) {
        ordensDeCompra.add(ordem);
        ordem.setConjuntoOrdemDeCompra(this);
    }

    // ✅ Método helper para remover ordens de forma segura
    public void removerOrdem(OrdemDeCompraModel ordem) {
        ordensDeCompra.remove(ordem);
        ordem.setConjuntoOrdemDeCompra(null);
    }
}
